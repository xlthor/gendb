package de.amthor.gendb.controller;

import java.security.Principal;
import java.util.Collections;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;

import de.amthor.gendb.entity.User;
import de.amthor.gendb.operations.ProjectOperations;
import de.amthor.gendb.payload.ProjectDto;
import de.amthor.gendb.payload.ProjectResponse;
import de.amthor.gendb.payload.UserDto;
import de.amthor.gendb.payload.Views;
import de.amthor.gendb.service.ProjectService;

@RestController
public class ProjectController extends ControllerBase implements ProjectOperations {

	private static final Logger LOGGER = LoggerFactory.getLogger(ProjectController.class);
	
	private ProjectService  projectService;
	
    public ProjectController(ProjectService projectService, ModelMapper mapper) {
    	super(mapper);
        this.projectService = projectService;
    }
    
	
    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ProjectDto> createProject(ProjectDto projectDto, Principal principal){
    	
    	LOGGER.debug(principal.toString());
    	
    	// find the current user
    	User user = getLoggedInUser(principal);
    	LOGGER.debug(user.toString());
        
    	projectDto.setUsers(Collections.singleton(genericSimpleMapper(user, UserDto.class)));
    	
        return new ResponseEntity<>(projectService.createProject(projectDto), HttpStatus.CREATED);
    }
    
    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ProjectDto>getProjectById(long projectId, Principal principal)
    {
    	User user = getLoggedInUser(principal);
    	
    	/*
    	 * we always filter by current user!
    	 */
    	ProjectDto projectDto = projectService.getProjectByIdAndUser(projectId, Collections.singleton(user));
    	
    	if ( projectDto != null )
    		return new ResponseEntity<>(projectDto, HttpStatus.OK);
    	else
    		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    
    @Override
    @PreAuthorize("hasRole('ADMIN')")
    @JsonView({Views.Response.class})
    public ProjectResponse getAllProjects(
            int pageNo,
            int pageSize,
            String sortBy,
            String sortDir,
            Principal principal
    ){
    	
    	// find the current user
    	User user = getLoggedInUser(principal);
    	
        return projectService.getAllProjects(pageNo, pageSize, sortBy, sortDir, Collections.singleton(user));
    }
    
    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ProjectDto> updateProject( Long projectId,
                                                    ProjectDto projectDto,
                                                    Principal principal)
    {
    	// check if the given principal owns the project
    	User user = getLoggedInUser(principal);
    	ProjectDto existingProjectDto = projectService.getProjectByIdAndUser(projectId, Collections.singleton(user));
    	
    	if ( existingProjectDto != null ) {
    		LOGGER.info(existingProjectDto.toString());
	        ProjectDto updatedProject = projectService.updateProject(projectDto, projectId);
	        return new ResponseEntity<>(updatedProject, HttpStatus.OK);
    	}
    	else {
    		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    	}
    }
    
    
    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deleteProject(Long projectId,
    											Principal principal)
    {
    	// check if the given principal owns the project
    	User user = getLoggedInUser(principal);
    	ProjectDto existingProjectDto = projectService.getProjectByIdAndUser(projectId, Collections.singleton(user));
    	
    	if ( existingProjectDto != null ) {
	    	LOGGER.info(existingProjectDto.toString());
	    	projectService.deleteProjectById(projectId);
	    	return new ResponseEntity<>("Project deleted successfully", HttpStatus.OK);
    	}
    	else {
    		return new ResponseEntity<>("Project not found", HttpStatus.NOT_FOUND);
    	}
    }
    
    
    
}
