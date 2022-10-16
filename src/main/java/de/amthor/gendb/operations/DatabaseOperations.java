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

import de.amthor.gendb.payload.CollationResponse;
import de.amthor.gendb.payload.DatabaseDto;
import de.amthor.gendb.payload.DatabaseResponse;
import de.amthor.gendb.payload.DbTypeDto;
import de.amthor.gendb.payload.DbTypeResponse;
import de.amthor.gendb.utils.AppConstants;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value = "CRUD Rest APIs for Datbase operations")
@RequestMapping(AppConstants.API_BASE)
public interface DatabaseOperations {

	/**
	 * Create a new database in the given project
	 * 
	 * @param databaseDto
	 * @param principal
	 * @return
	 */
	@ApiOperation(value = "Create a new database in the given project")
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(AppConstants.DATABASES)
	ResponseEntity<DatabaseDto> createDatabase(@Valid @RequestBody DatabaseDto databaseDto, Principal principal);

	/**
	 *Get database with id (if the according project belongs to user).
	 * 
	 * @param databaseId
	 * @param principal
	 * @return
	 */
	@ApiOperation(value = "Get database with id (if the according project belongs to user).")
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(AppConstants.DATABASES + "/{id}")
	ResponseEntity<DatabaseDto> getDatabaseById(
    		@PathVariable(value = "id")long databaseId, Principal principal);

	/**
	 * Get all projects of the current user
	 * @param pageNo
	 * @param pageSize
	 * @param sortBy
	 * @param sortDir
	 * @param principal
	 * @return
	 */
	@ApiOperation(value = "Get All Databases of the current user for particular release")
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(AppConstants.DATABASES + "/release/{releaseid}")
	DatabaseResponse getAllDatabases(
			@PathVariable(value = "releaseid") long releaseid,
		    @RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
		    @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
		    @RequestParam(value = "sortBy", defaultValue = "databaseid", required = false) String sortBy,
		    @RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir,
		    Principal principal);

	/**
	 * Update Database of current user by ID
	 * 
	 * @param databaseDto
	 * @param principal
	 * @return
	 */
	@ApiOperation(value = "Update Database of current user by ID")
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping(AppConstants.DATABASES)
	ResponseEntity<DatabaseDto> updateDatabase(
			@Valid @RequestBody DatabaseDto databaseDto,
            Principal principal);

	
	/**
	 * Delete the database with ID if it belongs the current user
	 * 
	 * @param databaseId
	 * @param principal
	 * @return
	 */
	@ApiOperation(value = "Delete Database of current user by ID")
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping(AppConstants.DATABASES + "/{id}")
	ResponseEntity<String> deleteDatabase(@PathVariable(value = "id") Long databaseId,
			Principal principal);
	
	
	/**
	 * Returns a list of the supported database types
	 * 
	 * @param principal
	 * @return
	 */
	@ApiOperation(value = "Get all database types")
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(AppConstants.DATABASES + "/types")
	DbTypeResponse getDatabaseTypes(Principal principal);
	
	
	/**
	 * Get a list of all collations and charsets for the given DB type
	 * @param dbtypeid
	 * @param principal
	 * @return
	 */
	@ApiOperation(value = "Get all collations of a particular database type.")
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(AppConstants.DATABASES + "/types/{dbtypeid}/collations")
	CollationResponse getDbTypeCollations(@PathVariable(value = "dbtypeid") Long dbtypeid, Principal principal);
	
	
	/**
	 * Retrieve a particular db type by its id
	 * 
	 * @param dbtypeid
	 * @param principal
	 * @return
	 */
	@ApiOperation(value = "Get a particular database by its id.")
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(AppConstants.DATABASES + "/types/{dbtypeid}")
	ResponseEntity<DbTypeDto> getDbType(@PathVariable(value = "dbtypeid") Long dbtypeid, Principal principal);
}