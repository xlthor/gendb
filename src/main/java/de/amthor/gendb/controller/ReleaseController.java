/**
 * 
 */
package de.amthor.gendb.controller;

import java.security.Principal;
import java.util.Collections;
import java.util.Optional;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import de.amthor.gendb.entity.Release;
import de.amthor.gendb.entity.User;
import de.amthor.gendb.exception.ResourceNotFoundException;
import de.amthor.gendb.operations.ReleaseOperations;
import de.amthor.gendb.payload.ProjectDto;
import de.amthor.gendb.payload.ReleaseDto;
import de.amthor.gendb.payload.ReleaseResponse;
import de.amthor.gendb.service.ProjectService;
import de.amthor.gendb.service.ReleaseService;

/**
 * @author axel
 *
 */
@RestController
public class ReleaseController extends ControllerBase implements ReleaseOperations  {

	private static final Logger LOGGER = LoggerFactory.getLogger(ReleaseController.class);
	
	private ReleaseService  releaseService;
	
	@Autowired
	private ProjectService  projectService;
	
	
    public ReleaseController(ReleaseService releaseService, ModelMapper mapper) {
    	super(mapper);
        this.releaseService = releaseService;
    }
    
	@Override
	public ResponseEntity<ReleaseDto> createRelease(@Valid ReleaseDto releaseDto, Principal principal) {
		
		// find the current user
		User user = getLoggedInUser(principal);
    	LOGGER.info(user.toString());
    	
    	ProjectDto projectDto = projectService.getProjectByIdAndUser(releaseDto.getProjectId(), Collections.singleton(user));
    	
    	if ( projectDto != null )
    		return new ResponseEntity<>(releaseService.createRelease(releaseDto), HttpStatus.CREATED);
    	else
    		throw new ResourceNotFoundException("Project", "id", releaseDto.getProjectId());
	}

	@Override
	public ResponseEntity<ReleaseDto> getReleaseById(long releaseId, Principal principal) {
		User user = getLoggedInUser(principal);
    	
    	Optional<Release> release = releaseService.getReleaseById(releaseId);
    	if ( release.isEmpty() )
    		throw new ResourceNotFoundException("Release", "id", releaseId);
    	
		ProjectDto projectDto = projectService.getProjectByIdAndUser(release.get().getProjectId(), Collections.singleton(user));
	
		if ( projectDto != null ) {
			// we have a release and the according project belongs to user ...
			return new ResponseEntity<>( genericSimpleMapper(release.get(), ReleaseDto.class), HttpStatus.OK);
		}
    	else
    		throw new ResourceNotFoundException("Release", "id", releaseId);
	}

	@Override
	public ReleaseResponse getAllReleases(long projectId, int pageNo, int pageSize, String sortBy, String sortDir, Principal principal) {
		// find the current user
    	User user = getLoggedInUser(principal);
    	
    	ProjectDto projectDto = projectService.getProjectByIdAndUser(projectId, Collections.singleton(user));
    	
    	if ( projectDto != null ) {
			
    		// we have a release and the users project
    		ReleaseResponse releases = releaseService.getAllReleases(pageNo, pageSize, sortBy, sortDir, projectId);
    		
			return releases;
		}
    	else
    		throw new ResourceNotFoundException("Project", "id", projectId);
	}

	@Override
	public ResponseEntity<ReleaseDto> updateRelease(@Valid ReleaseDto releaseDto, Principal principal) {
		// find the current user
    	User user = getLoggedInUser(principal);
    	long projectId = releaseDto.getProjectId();
    	
    	// get the project for this user only
    	ProjectDto projectDto = projectService.getProjectByIdAndUser(projectId, Collections.singleton(user));
    	if ( projectDto != null ) {
    		// update release by id
    		ReleaseDto updatedReleaseDto = releaseService.updateRelease(releaseDto);
    		return new ResponseEntity<>( updatedReleaseDto, HttpStatus.OK);
    	}
    	else
    		throw new ResourceNotFoundException("Project", "id", projectId);
    		
	}

	@Override
	public ResponseEntity<String> deleteRelease(long releaseId, Principal principal) {
		User user = getLoggedInUser(principal);
		
		Optional<Release> release = releaseService.getReleaseById(releaseId);
    	if ( release.isEmpty() )
    		throw new ResourceNotFoundException("Release", "id", releaseId);
    	
    	LOGGER.info("=====================> " + release.toString());
    	
    	
		ProjectDto projectDto = projectService.getProjectByIdAndUser(release.get().getProjectId(), Collections.singleton(user));
		
		if ( projectDto != null ) {
			releaseService.deleteRelease(release.get().getReleaseid());
			return new ResponseEntity<>( HttpStatus.NO_CONTENT );
		}
		else
			throw new ResourceNotFoundException("Project", "id", release.get().getProjectId());
	}
	
}
