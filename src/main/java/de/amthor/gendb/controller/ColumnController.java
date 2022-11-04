package de.amthor.gendb.controller;

import java.security.Principal;
import java.util.Collections;
import java.util.Optional;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.RestController;

import de.amthor.gendb.entity.Collation;
import de.amthor.gendb.entity.DbType;
import de.amthor.gendb.entity.Release;
import de.amthor.gendb.entity.User;
import de.amthor.gendb.exception.AlreadyExistsException;
import de.amthor.gendb.exception.ReferenceException;
import de.amthor.gendb.exception.ResourceNotFoundException;
import de.amthor.gendb.operations.ColumnOperations;
import de.amthor.gendb.payload.ColumnResponse;
import de.amthor.gendb.payload.ColumnsDto;
import de.amthor.gendb.payload.DatabaseDto;
import de.amthor.gendb.payload.ProjectDto;
import de.amthor.gendb.payload.TableDto;
import de.amthor.gendb.service.ColumnService;
import de.amthor.gendb.service.DatabaseService;
import de.amthor.gendb.service.ProjectService;
import de.amthor.gendb.service.ReleaseService;
import de.amthor.gendb.service.TableService;

@RestController
public class ColumnController extends ControllerBase implements ColumnOperations {

	private static final Logger LOGGER = LoggerFactory.getLogger(ColumnController.class);
	
	private ColumnService  columnService;
	
	@Autowired
	private DatabaseService  databaseService;
	
	@Autowired
	TableService tableService;
	
	@Autowired
	ReleaseService releaseService;
	
	@Autowired
	ProjectService projectService;
	
	public ColumnController(ColumnService  columnService, ModelMapper mapper) {
		super(mapper);
		this.columnService = columnService;
	}

	@Override
	public ResponseEntity<ColumnsDto> createColumn(@Valid ColumnsDto columnDto, Principal principal) {

		// cascade the hierarchy and check the users rights
    	TableDto tableDto = tableService.getTable(columnDto.getTableid()).orElseThrow(() -> new ResourceNotFoundException("Table", "id",  columnDto.getTableid()));
    	DatabaseDto databaseDto = databaseService.getDatabaseById(tableDto.getDatabaseId()).orElseThrow(() -> new ResourceNotFoundException("Database", "id", tableDto.getDatabaseId()));
    	DbType dbType = databaseService.getDBType(databaseDto.getDbType().getTypeid());
    	
		checkProjectAccess(databaseDto, principal);

		// check the collation
		Collation collation = databaseService.getCollation(columnDto.getCollation().getCollationid());
				
		if ( collation == null || dbType == null || !collation.getTypename().equals(dbType.getTypename()) ) {
			throw new ReferenceException("Type of collation and database mismatched or missing");
		}
		
		// all good now, create the column entry
		ColumnsDto col = columnService.saveColumn(columnDto);
		
		return new ResponseEntity<>(col, HttpStatus.CREATED);
	}
	
	/**
	 * If the project of this column/table/database/release/project path is owned by this user, it will be returned. Otherwise an exception is thrown.
	 * 
	 * @param databaseDto
	 * @param principal
	 */
	private ProjectDto checkProjectAccess(DatabaseDto databaseDto, Principal principal) {
		ProjectDto project;
		
		// find the current user
    	User user = getLoggedInUser(principal);
    	
    	long releaseId = databaseDto.getReleaseId();
    	Release release = releaseService.getReleaseById(releaseId).orElseThrow(() -> new ResourceNotFoundException("Release", "id", releaseId));
    	project = projectService.getProjectByIdAndUser(release.getProjectId(), Collections.singleton(user));
    	if ( project == null )
    		throw new AccessDeniedException("Not owner of the project");
    	
    	return project;
	}

	@Override
	public ResponseEntity<ColumnsDto> getColumnById(long columnId, Principal principal) {
		
		ColumnsDto columnDto = columnService.findById(columnId).orElseThrow(() -> new ResourceNotFoundException("Column", "id", columnId));
		
		// cascade the hierarchy and check the users rights
    	TableDto tableDto = tableService.getTable(columnDto.getTableid()).orElseThrow(() -> new ResourceNotFoundException("Table", "id",  columnDto.getTableid()));
    	DatabaseDto databaseDto = databaseService.getDatabaseById(tableDto.getDatabaseId()).orElseThrow(() -> new ResourceNotFoundException("Database", "id", tableDto.getDatabaseId()));
    	
		checkProjectAccess(databaseDto, principal);
		
		return new ResponseEntity<>(columnDto, HttpStatus.OK);
	}

	@Override
	public ColumnResponse getAllColumns(Long tableid, int pageNo, int pageSize, String sortBy, String sortDir, Principal principal) {

		ColumnResponse colresponse = null;
		
		// cascade the hierarchy and check the users rights
    	TableDto tableDto = tableService.getTable(tableid).orElseThrow(() -> new ResourceNotFoundException("Table", "id",  tableid));
    	DatabaseDto databaseDto = databaseService.getDatabaseById(tableDto.getDatabaseId()).orElseThrow(() -> new ResourceNotFoundException("Database", "id", tableDto.getDatabaseId()));
    	
		checkProjectAccess(databaseDto, principal);
		
		colresponse = columnService.getAllColumnsOfTable(tableid,pageNo, pageSize, sortBy, sortDir);
		
		return colresponse;
	}

	@Override
	public ResponseEntity<ColumnsDto> updateColumn(Long columnId, @Valid ColumnsDto columnDto, Principal principal) {
		
		// cascade the hierarchy and check the users rights
    	TableDto tableDto = tableService.getTable(columnDto.getTableid()).orElseThrow(() -> new ResourceNotFoundException("Table", "id",  columnDto.getTableid()));
    	DatabaseDto databaseDto = databaseService.getDatabaseById(tableDto.getDatabaseId()).orElseThrow(() -> new ResourceNotFoundException("Database", "id", tableDto.getDatabaseId()));
    	DbType dbType = databaseService.getDBType(databaseDto.getDbType().getTypeid());
    	
		checkProjectAccess(databaseDto, principal);

		Optional<ColumnsDto> existingColDto = columnService.findById(columnId);
		if ( existingColDto.isEmpty() )
			throw new ResourceNotFoundException("Column", "id", columnId);
		
		/*
		 * If there is a column with that name but it has a different ID: duplicate name
		 */
		existingColDto = columnService.findByName(columnDto.getColname());
		if ( existingColDto.isPresent() && existingColDto.get().getColumnid() != columnId )
			throw new AlreadyExistsException("Column", "name", existingColDto.get().getColname());
		
		// check the collation
		Collation collation = databaseService.getCollation(columnDto.getCollation().getCollationid());
				
		if ( collation == null || dbType == null || !collation.getTypename().equals(dbType.getTypename()) ) {
			throw new ReferenceException("Type of collation and database mismatched or missing");
		}
		
		// all good now, save the column entry
		columnDto.setColumnid(columnId);
		ColumnsDto col = columnService.saveColumn(columnDto);
		
		return new ResponseEntity<>(col, HttpStatus.CREATED);
	}

	@Override
	public ResponseEntity<String> deleteColumn(Long columnId, Principal principal) {
		
		ColumnsDto columnDto = columnService.findById(columnId).orElseThrow(() -> new ResourceNotFoundException("Column", "id", columnId));
		
		// cascade the hierarchy and check the users rights
    	TableDto tableDto = tableService.getTable(columnDto.getTableid()).orElseThrow(() -> new ResourceNotFoundException("Table", "id",  columnDto.getTableid()));
    	DatabaseDto databaseDto = databaseService.getDatabaseById(tableDto.getDatabaseId()).orElseThrow(() -> new ResourceNotFoundException("Database", "id", tableDto.getDatabaseId()));
    	
		checkProjectAccess(databaseDto, principal);
		
		columnService.deleteById(columnId);
		
		return new ResponseEntity<>("deleted", HttpStatus.OK);
	}

}
