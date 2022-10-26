package de.amthor.gendb.controller;

import java.security.Principal;
import java.util.Collections;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;

import de.amthor.gendb.entity.Collation;
import de.amthor.gendb.entity.DbType;
import de.amthor.gendb.entity.Release;
import de.amthor.gendb.entity.User;
import de.amthor.gendb.exception.ReferenceException;
import de.amthor.gendb.exception.ResourceNotFoundException;
import de.amthor.gendb.operations.DatabaseOperations;
import de.amthor.gendb.payload.CollationDto;
import de.amthor.gendb.payload.CollationResponse;
import de.amthor.gendb.payload.DatabaseDto;
import de.amthor.gendb.payload.DatabaseResponse;
import de.amthor.gendb.payload.DbTypeDto;
import de.amthor.gendb.payload.DbTypeResponse;
import de.amthor.gendb.payload.ProjectDto;
import de.amthor.gendb.payload.Views;
import de.amthor.gendb.service.DatabaseService;
import de.amthor.gendb.service.ProjectService;
import de.amthor.gendb.service.ReleaseService;
import de.amthor.gendb.service.TableService;

@RestController
public class DatabaseController extends ControllerBase implements DatabaseOperations {

private static final Logger LOGGER = LoggerFactory.getLogger(ProjectController.class);
	
	private DatabaseService  databaseService;
	
	@Autowired
	ReleaseService releaseService;
	
	@Autowired
	ProjectService projectService;
	
	@Autowired
	TableService tableService;
	
	public DatabaseController(DatabaseService databaseService, ModelMapper mapper) {
		super(mapper);
		this.databaseService = databaseService;
	}

	@Override
	public ResponseEntity<DatabaseDto> createDatabase(@Valid DatabaseDto databaseDto, Principal principal) {

		checkProjectAccess(databaseDto, principal);
		
		// check the collation
		Collation collation = databaseService.getCollation(databaseDto.getCollation().getCollationid());
		DbType dbType = databaseService.getDBType(databaseDto.getDbType().getTypeid());
		
		if ( dbType == null || collation == null || !collation.getTypename().equals(dbType.getTypename()) ) {
			throw new ReferenceException("Type of collation and database mismatched or missing");
		}
    	
    	// ok, all good, let's create a database:
    	DatabaseDto database = databaseService.createDatabase(databaseDto);
    	
    	return new ResponseEntity<>(database, HttpStatus.CREATED);
	}

	/**
	 * If the project of this database belongs to this user, it will be returned. Otherwise an exception is thrown.
	 * 
	 * @param databaseDto
	 * @param principal
	 */
	private ProjectDto checkProjectAccess(DatabaseDto databaseDto, Principal principal) {
		ProjectDto project;
		// find the current user
    	User user = getLoggedInUser(principal);
    	LOGGER.debug(user.toString());
    	
    	LOGGER.info("====================> " + databaseDto);
    	
    	// cascade the hierarchy and check the users rights
    	long releaseId = databaseDto.getReleaseId();
    	Release release = releaseService.getReleaseById(releaseId).orElseThrow(() -> new ResourceNotFoundException("Release", "id", releaseId));
    	project = projectService.getProjectByIdAndUser(release.getProjectId(), Collections.singleton(user));
    	if ( project == null )
    		throw new ResourceNotFoundException("Project", "id", release.getProjectId());
    	
    	return project;
	}

	@Override
	public ResponseEntity<DatabaseDto> getDatabaseById(long databaseId, Principal principal) {
		
		DatabaseDto databaseDto = databaseService.getDatabaseById(databaseId)
															.orElseThrow(() -> new ResourceNotFoundException("Database", "id", databaseId));
		 
		checkProjectAccess(databaseDto, principal);
    	
    	// ok, all good, let's return the database:
    	return new ResponseEntity<>(databaseDto, HttpStatus.CREATED);
	}

	@Override
	@JsonView({Views.DatabaseResponse.class})
	public DatabaseResponse getAllDatabases(long releaseId, int pageNo, int pageSize, String sortBy, String sortDir, Principal principal) {
		
		User user = getLoggedInUser(principal);
		
		// check access
		projectService.getProjectByReleaseAndUser(releaseId, user)
																.orElseThrow(() -> new ResourceNotFoundException("Project by Release", "releaseid", releaseId));
		return databaseService.getAllDatabases(releaseId, pageNo, pageSize, sortBy, sortDir);
	}

	@Override
	public ResponseEntity<DatabaseDto> updateDatabase(@Valid DatabaseDto databaseDto, Principal principal) {
		checkProjectAccess(databaseDto, principal);
		
		// check the collation
		CollationDto collation = tableService.getCollation(databaseDto.getCollation().getCollationid());
		DbType dbType = databaseService.getDBType(databaseDto.getDbType().getTypeid());
		
		if ( dbType == null || collation == null || !collation.getTypename().equals(dbType.getTypename()) ) {
			throw new ReferenceException("Type of collation and database mismatched or missing");
		}
				
		DatabaseDto updated = databaseService.updateDatabase(databaseDto);
		
		return new ResponseEntity<>(updated, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<String> deleteDatabase(Long databaseId, Principal principal) {
		DatabaseDto databaseDto = databaseService.getDatabaseById(databaseId).orElseThrow(() -> new ResourceNotFoundException("Database", "id", databaseId));
		checkProjectAccess(databaseDto, principal);
		databaseService.deleteDatabase(databaseDto);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@Override
	public DbTypeResponse getDatabaseTypes(int pageNo, int pageSize, String sortBy, String sortDir) {
		return databaseService.getDatabaseTypes(pageNo, pageSize, sortBy, sortDir);
	}

	@Override
	public CollationResponse getDbTypeCollations(Long dbtypeid, int pageNo, int pageSize, String sortBy, String sortDir) {
		return databaseService.getCollationsForType(dbtypeid, pageNo, pageSize, sortBy, sortDir);
	}

	@Override
	public ResponseEntity<DbTypeDto> getDbType(Long dbtypeid, Principal principal) {

		DbTypeDto dbTypeDto = databaseService.getDbTypeById(dbtypeid)
															.orElseThrow(() -> new ResourceNotFoundException("Database Type", "id", dbtypeid));
		
		return new ResponseEntity<>(dbTypeDto, HttpStatus.OK);
	}


}
