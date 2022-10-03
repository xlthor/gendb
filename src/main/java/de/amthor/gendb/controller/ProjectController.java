package de.amthor.gendb.controller;




import java.security.Principal;
import java.util.Collections;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import de.amthor.gendb.entity.User;
import de.amthor.gendb.payload.ProjectDto;
import de.amthor.gendb.payload.ProjectResponse;
import de.amthor.gendb.repository.UserRepository;
import de.amthor.gendb.service.ProjectService;
import de.amthor.gendb.utils.AppConstants;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value = "CRUD Rest APIs for project resources")
@RestController
@RequestMapping(AppConstants.API_BASE)
public class ProjectController {

	private static final Logger LOGGER = LoggerFactory.getLogger(ProjectController.class);
	
	private ProjectService  projectService;
	
	@Autowired
    private UserRepository userRepository;
	
    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }
    
	
    /**
     * Create a new project for this user
     * 
     * @param projectDto
     * @param principal
     * @return
     */
    @ApiOperation(value = "Create new Project REST API")
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(AppConstants.PROJECTS)
    public ResponseEntity<ProjectDto> createProject(@Valid @RequestBody ProjectDto projectDto, Principal principal){
    	
    	LOGGER.info(principal.toString());
    	
    	// find the current user
    	Optional<User> user = userRepository.findByLoginnameOrEmail(principal.getName(),principal.getName());
    	LOGGER.info(user.toString());
        
    	projectDto.setUsers(Collections.singleton(user.get()));
    	
        return new ResponseEntity<>(projectService.createProject(projectDto), HttpStatus.CREATED);
    }
    
    /**
     * Get all projects of the current user
     * @param pageNo
     * @param pageSize
     * @param sortBy
     * @param sortDir
     * @param principal
     * @return
     */
    @ApiOperation(value = "Get All Projects of the current user REST API")
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(AppConstants.PROJECTS)
    public ProjectResponse getAllProjects(
            @RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir,
            Principal principal
    ){
    	
    	// find the current user
    	Optional<User> user = userRepository.findByLoginnameOrEmail(principal.getName(),principal.getName());
    	LOGGER.info(user.toString());
    	
        return projectService.getAllProjects(pageNo, pageSize, sortBy, sortDir, Collections.singleton(user.get()));
    }
    
    @ApiOperation(value = "Update Project By ID REST API")
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping(AppConstants.PROJECTS + "/{id}")
    public ResponseEntity<ProjectDto> updateProject(@PathVariable(value = "id") Long projectId,
                                                    @Valid @RequestBody ProjectDto projectDto,
                                                    Principal principal)
    {
    	// check if the given principal owns the project
    	Optional<User> user = userRepository.findByLoginnameOrEmail(principal.getName(), principal.getName());
    	ProjectDto existingProjectDto = projectService.getProjectByIdAndUser(projectId, Collections.singleton(user.get()));
    	
    	LOGGER.info(existingProjectDto.toString());
    	
        ProjectDto updatedProject = projectService.updateProject(projectDto, projectId);
        return new ResponseEntity<>(updatedProject, HttpStatus.OK);
    }
    
    
    @ApiOperation(value = "Delete Project By ID REST API")
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping(AppConstants.PROJECTS + "/{id}")
    public ResponseEntity<String> deleteProject(@PathVariable(value = "id") Long projectId,
    											Principal principal)
    {
    	// check if the given principal owns the project
    	Optional<User> user = userRepository.findByLoginnameOrEmail(principal.getName(), principal.getName());
    	ProjectDto existingProjectDto = projectService.getProjectByIdAndUser(projectId, Collections.singleton(user.get()));
    	
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
