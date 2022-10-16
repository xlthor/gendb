package de.amthor.gendb.operations;

import java.security.Principal;

import javax.validation.Valid;

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

import de.amthor.gendb.payload.ProjectDto;
import de.amthor.gendb.payload.ProjectResponse;
import de.amthor.gendb.utils.AppConstants;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value = "CRUD Rest APIs for project operations")
@RequestMapping(AppConstants.API_BASE)
public interface ProjectOperations {

	/**
	 * Create a new project for this user
	 * 
	 * @param projectDto
	 * @param principal
	 * @return
	 */
	@ApiOperation(value = "Create new Project for the current user")
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(AppConstants.PROJECTS)
	ResponseEntity<ProjectDto> createProject(@Valid @RequestBody ProjectDto projectDto, Principal principal);

	/**
	 * Get project of current user by ID.
	 * 
	 * @param projectId
	 * @param principal
	 * @return
	 */
	@ApiOperation(value = "Get project of current user by ID.")
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(AppConstants.PROJECTS + "/{id}")
	ResponseEntity<ProjectDto> getProjectById(
    		@PathVariable(value = "id")long projectId, Principal principal);

	/**
	 * Get all projects of the current user
	 * @param pageNo
	 * @param pageSize
	 * @param sortBy
	 * @param sortDir
	 * @param principal
	 * @return
	 */
	@ApiOperation(value = "Get All Projects of the current user")
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(AppConstants.PROJECTS)
	ProjectResponse getAllProjects(
	    @RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
	    @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
	    @RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
	    @RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir,
    Principal principal);

	/**
	 * Update Project of current user by ID
	 * 
	 * @param projectId
	 * @param projectDto
	 * @param principal
	 * @return
	 */
	@ApiOperation(value = "Update Project of current user by ID")
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping(AppConstants.PROJECTS + "/{id}")
	ResponseEntity<ProjectDto> updateProject(@PathVariable(value = "id") Long projectId,
            @Valid @RequestBody ProjectDto projectDto,
            Principal principal);

	
	/**
	 * Delete the prject with ID if it belongs the current user
	 * 
	 * @param projectId
	 * @param principal
	 * @return
	 */
	@ApiOperation(value = "Delete Project of current user by ID")
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping(AppConstants.PROJECTS + "/{id}")
	ResponseEntity<String> deleteProject(@PathVariable(value = "id") Long projectId,
			Principal principal);

}