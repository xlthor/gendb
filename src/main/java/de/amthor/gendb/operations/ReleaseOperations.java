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

import de.amthor.gendb.payload.ReleaseDto;
import de.amthor.gendb.payload.ReleaseResponse;
import de.amthor.gendb.utils.AppConstants;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value = "CRUD Rest APIs for Release operations")
@RequestMapping(AppConstants.API_BASE)
public interface ReleaseOperations {

	/**
	 * Create a new release in the given project
	 * 
	 * @param releaseDto
	 * @param principal
	 * @return
	 */
	@ApiOperation(value = "Create a new release in the given project")
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(AppConstants.RELEASES)
	ResponseEntity<ReleaseDto> createRelease(@Valid @RequestBody ReleaseDto releaseDto, Principal principal);

	/**
	 *Get release with id (if the according project belongs to user).
	 * 
	 * @param releaseId
	 * @param principal
	 * @return
	 */
	@ApiOperation(value = "Get release with id (if the according project belongs to user).")
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(AppConstants.RELEASES + "/{id}")
	ResponseEntity<ReleaseDto> getReleaseById(
    		@PathVariable(value = "id")long releaseId, Principal principal);

	/**
	 * Get all projects of the current user
	 * @param pageNo
	 * @param pageSize
	 * @param sortBy
	 * @param sortDir
	 * @param principal
	 * @return
	 */
	@ApiOperation(value = "Get All Releases of the current user for particular project")
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(AppConstants.RELEASES + "/project/{projectid}")
	ReleaseResponse getAllReleases(
			@PathVariable(value = "projectid") long projectid,
		    @RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
		    @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
		    @RequestParam(value = "sortBy", defaultValue = "releaseid", required = false) String sortBy,
		    @RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir,
		    Principal principal);

	/**
	 * Update Release of current user by ID
	 * 
	 * @param releaseDto
	 * @param principal
	 * @return
	 */
	@ApiOperation(value = "Update Release of current user by ID")
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping(AppConstants.RELEASES)
	ResponseEntity<ReleaseDto> updateRelease(
			@Valid @RequestBody ReleaseDto releaseDto,
            Principal principal);

	
	/**
	 * Delete the release with ID if it belongs the current user
	 * 
	 * @param releaseId
	 * @param principal
	 * @return
	 */
	@ApiOperation(value = "Delete Release of current user by ID")
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping(AppConstants.RELEASES + "/{id}")
	ResponseEntity<String> deleteRelease(@PathVariable(value = "id") long releaseId, Principal principal);

}