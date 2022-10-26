/**
 * 
 */
package de.amthor.gendb.controller;

import java.security.Principal;
import java.util.Collections;
import java.util.Optional;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import de.amthor.gendb.entity.Collation;
import de.amthor.gendb.entity.GdbTable;
import de.amthor.gendb.entity.Release;
import de.amthor.gendb.entity.Tableformat;
import de.amthor.gendb.entity.User;
import de.amthor.gendb.exception.AlreadyExistsException;
import de.amthor.gendb.exception.ReferenceException;
import de.amthor.gendb.exception.ResourceNotFoundException;
import de.amthor.gendb.operations.TableOperations;
import de.amthor.gendb.payload.CollationDto;
import de.amthor.gendb.payload.DatabaseDto;
import de.amthor.gendb.payload.ProjectDto;
import de.amthor.gendb.payload.TableDto;
import de.amthor.gendb.payload.TableResponse;
import de.amthor.gendb.service.DatabaseService;
import de.amthor.gendb.service.ProjectService;
import de.amthor.gendb.service.ReleaseService;
import de.amthor.gendb.service.TableService;

/**
 * @author axel
 *
 */
@RestController
public class TableController extends ControllerBase implements TableOperations {

	TableService tableService;
	
	@Autowired
	ReleaseService releaseService;
	
	@Autowired
	ProjectService projectService;
	
	@Autowired
	DatabaseService databaseService;
	
	public TableController(TableService tableService, ModelMapper mapper) {
		super(mapper);
		this.tableService = tableService;
	}

	@Override
	public ResponseEntity<TableDto> createTable(@Valid TableDto tableDto, Principal principal) {
		
		DatabaseDto databaseDto = databaseService.getDatabaseById(tableDto.getDatabaseId())
											.orElseThrow(() -> new ResourceNotFoundException("Database", "id", tableDto.getDatabaseId()));
		
		@SuppressWarnings("unused")
		ProjectDto project = checkProjectAccess(databaseDto, principal);
		
		// check uniqueness: table name for this database
		Optional<TableDto> tmpTable = tableService.getTable(tableDto);
		if ( tmpTable.isPresent() )
			throw new AlreadyExistsException("Table", "table name", tableDto.getTablename());
			
		// check the collation
		CollationDto collation = tableService.getCollation(tableDto.getCollation().getCollationid());
		
		// check the table format
		Tableformat tableFormat = tableService.getTableFormat(tableDto.getTableformat().getFormatid());
		
		// check whether the given format and collation are ones of the current database
		if ( tableFormat == null ||  !tableFormat.getTypename().equals(databaseDto.getDbType().getTypename()) ) {
			throw new ReferenceException("Type of table format and database mismatched or missing");
		}
		
		if ( collation == null || !collation.getTypename().equals(databaseDto.getDbType().getTypename()) ) {
			throw new ReferenceException("Type of collation and database mismatched or missing");
		}
		
		GdbTable table = genericSimpleMapper(tableDto, GdbTable.class);
		
		table.setCollation(genericSimpleMapper(collation, Collation.class));
    	table.setTableformat(tableFormat);
    	
    	TableDto newTable = tableService.createTable(table);
    	
    	return new ResponseEntity<>(newTable, HttpStatus.CREATED);
	}

	/**
	 * @param tableDto
	 * @param databaseDto 
	 * @param principal
	 */
	private ProjectDto checkProjectAccess(DatabaseDto databaseDto, Principal principal) {
		
		ProjectDto project;
		// find the current user
    	User user = getLoggedInUser(principal);
    	
    	Release release = releaseService.getReleaseById(databaseDto.getReleaseId()).orElseThrow(() -> new ResourceNotFoundException("Release", "id", databaseDto.getReleaseId()));
    	project = projectService.getProjectByIdAndUser(release.getProjectId(), Collections.singleton(user));
    	if ( project == null )
    		throw new ResourceNotFoundException("Project", "id", release.getProjectId());
    	
    	return project;
	}

	@Override
	public ResponseEntity<TableDto> getTableById(long tableId, Principal principal) {
		
		ProjectDto project;
		// find the current user
    	User user = getLoggedInUser(principal);
    	
    	TableDto tableDto = tableService.getTable(tableId).orElseThrow(() -> new ResourceNotFoundException("Table", "tableid", tableId));
    	
    	DatabaseDto databaseDto = databaseService.getDatabaseById(tableDto.getDatabaseId())
				.orElseThrow(() -> new ResourceNotFoundException("Database", "id", tableDto.getDatabaseId()));
    	
    	Release release = releaseService.getReleaseById(databaseDto.getReleaseId()).orElseThrow(() -> new ResourceNotFoundException("Release", "id", databaseDto.getReleaseId()));
    	project = projectService.getProjectByIdAndUser(release.getProjectId(), Collections.singleton(user));
    	if ( project == null )
    		throw new ResourceNotFoundException("Project", "id", release.getProjectId());
		
		return new ResponseEntity<>(tableDto, HttpStatus.OK);
	}

	@Override
	public TableResponse getAllTables(Long dbid, int pageNo, int pageSize, String sortBy, String sortDir, Principal principal) {
		
		TableResponse tableResponse = null;
		
    	DatabaseDto databaseDto = databaseService.getDatabaseById(dbid)
				.orElseThrow(() -> new ResourceNotFoundException("Database", "id", dbid));
    	@SuppressWarnings("unused")
		ProjectDto project = checkProjectAccess(databaseDto, principal);
    	
    	tableResponse  = tableService.getAllTablesOfDatabase(dbid, pageNo, pageSize, sortBy, sortDir);
    	
    	return tableResponse;
	}

	@Override
	public ResponseEntity<TableDto> updateTable(Long tableId, @Valid TableDto tableDto, Principal principal) {

		DatabaseDto databaseDto = databaseService.getDatabaseById(tableDto.getDatabaseId())
										.orElseThrow(() -> new ResourceNotFoundException("Database", "id", tableDto.getDatabaseId()));

		@SuppressWarnings("unused")
		ProjectDto project = checkProjectAccess(databaseDto, principal);
		
		// check uniqueness: table name for this database
		Optional<TableDto> tmpTable = tableService.getTable(tableDto);
		if (tmpTable.isPresent() && tmpTable.get().getTableid() != tableId )
			throw new AlreadyExistsException("Table", "table name", tableDto.getTablename());

		// check the collation
		CollationDto collation = tableService.getCollation(tableDto.getCollation().getCollationid());

		// check the table format
		Tableformat tableFormat = tableService.getTableFormat(tableDto.getTableformat().getFormatid());

		// check whether the given format and collation are ones of the current database
		if (tableFormat == null || !tableFormat.getTypename().equals(databaseDto.getDbType().getTypename())) {
			throw new ReferenceException("Type of table format and database mismatched or missing");
		}

		if (collation == null || !collation.getTypename().equals(databaseDto.getDbType().getTypename())) {
			throw new ReferenceException("Type of collation and database mismatched or missing");
		}

		GdbTable table = genericSimpleMapper(tableDto, GdbTable.class);

		table.setCollation(genericSimpleMapper(collation, Collation.class));
		table.setTableformat(tableFormat);
		table.setTableid(tableId);

		TableDto newTable = tableService.updateTable(table);

		return new ResponseEntity<>(newTable, HttpStatus.OK);

	}

	@Override
	public ResponseEntity<String> deleteTable(Long tableId, Principal principal) {

		TableDto tableDto = tableService.getTable(tableId).orElseThrow(() -> new ResourceNotFoundException("Table", "tableid", tableId));
		
		DatabaseDto databaseDto = databaseService.getDatabaseById(tableDto.getDatabaseId())
				.orElseThrow(() -> new ResourceNotFoundException("Database", "id", tableDto.getDatabaseId()));

		@SuppressWarnings("unused")
		ProjectDto project = checkProjectAccess(databaseDto, principal);
		
		// FIXME: check for children: columns, seed data etc.

		tableService.deleteTable(tableId);
		
		return new ResponseEntity<>(HttpStatus.OK);
	}

}
