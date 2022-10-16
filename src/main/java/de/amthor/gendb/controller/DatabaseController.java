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

import de.amthor.gendb.entity.Release;
import de.amthor.gendb.entity.User;
import de.amthor.gendb.exception.ResourceNotFoundException;
import de.amthor.gendb.operations.DatabaseOperations;
import de.amthor.gendb.payload.CollationResponse;
import de.amthor.gendb.payload.DatabaseDto;
import de.amthor.gendb.payload.DatabaseResponse;
import de.amthor.gendb.payload.DbTypeDto;
import de.amthor.gendb.payload.DbTypeResponse;
import de.amthor.gendb.payload.ProjectDto;
import de.amthor.gendb.service.DatabaseService;
import de.amthor.gendb.service.ProjectService;
import de.amthor.gendb.service.ReleaseService;

@RestController
public class DatabaseController extends ControllerBase implements DatabaseOperations {

private static final Logger LOGGER = LoggerFactory.getLogger(ProjectController.class);
	
	private DatabaseService  databaseService;
	
	@Autowired
	ReleaseService releaseService;
	
	@Autowired
	ProjectService projectService;
	
	public DatabaseController(DatabaseService databaseService, ModelMapper mapper) {
		super(mapper);
		this.databaseService = databaseService;
	}

	@Override
	public ResponseEntity<DatabaseDto> createDatabase(@Valid DatabaseDto databaseDto, Principal principal) {

		// find the current user
    	User user = getLoggedInUser(principal);
    	LOGGER.debug(user.toString());
    	
    	LOGGER.info("====================> " + databaseDto);
    	
    	// cascade the hierarchy and check the users rights
    	long releaseId = databaseDto.getReleaseId();
    	Release release = releaseService.getReleaseById(releaseId).orElseThrow(() -> new ResourceNotFoundException("Release", "id", releaseId));
    	ProjectDto project = projectService.getProjectByIdAndUser(release.getProjectId(), Collections.singleton(user));
    	if ( project == null )
    		throw new ResourceNotFoundException("Project", "id", release.getProjectId());
    	
    	// ok, all good, let's create a database:
    	DatabaseDto database = databaseService.createDatabase(databaseDto);
    	
    	return new ResponseEntity<>(database, HttpStatus.CREATED);
	}

	@Override
	public ResponseEntity<DatabaseDto> getDatabaseById(long databaseId, Principal principal) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DatabaseResponse getAllDatabases(long releaseid, int pageNo, int pageSize, String sortBy, String sortDir,
			Principal principal) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<DatabaseDto> updateDatabase(@Valid DatabaseDto databaseDto, Principal principal) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<String> deleteDatabase(Long databaseId, Principal principal) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DbTypeResponse getDatabaseTypes(Principal principal) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CollationResponse getDbTypeCollations(Long dbtypeid, Principal principal) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<DbTypeDto> getDbType(Long dbtypeid, Principal principal) {
		// TODO Auto-generated method stub
		return null;
	}

}
