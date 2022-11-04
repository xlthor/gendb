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

import com.fasterxml.jackson.annotation.JsonView;

import de.amthor.gendb.payload.ColumnResponse;
import de.amthor.gendb.payload.ColumnsDto;
import de.amthor.gendb.payload.Views;
import de.amthor.gendb.utils.AppConstants;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(value = "CRUD Rest APIs for column operations")
@RequestMapping(AppConstants.API_BASE)
public interface ColumnOperations {

	/**
	 * Create a new column for this user
	 * 
	 * @param columnDto
	 * @param principal
	 * @return
	 */
	@JsonView({Views.Response.class})
	@ApiOperation(value = "Create a new column within a table")
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(AppConstants.COLUMNS)
	ResponseEntity<ColumnsDto> createColumn(
			@Valid 
			@RequestBody
			@JsonView({Views.ColumnCreate.class})
			@ApiParam(name = "Column", value = "Column Definition") ColumnsDto columnDto, 
			Principal principal);

	/**
	 * Get column by ID.
	 * 
	 * @param columnId
	 * @param principal
	 * @return
	 */
	@JsonView({Views.Response.class})
	@ApiOperation(value = "Get column by ID.")
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(AppConstants.COLUMNS + "/{id}")
	ResponseEntity<ColumnsDto> getColumnById(
    		@PathVariable(value = "id")long columnId, Principal principal);

	/**
	 * Get all columns within a given table
	 * 
	 * @param pageNo
	 * @param pageSize
	 * @param sortBy
	 * @param sortDir
	 * @param principal
	 * @return
	 */
	@ApiOperation(value = "Get All columns within a table")
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(AppConstants.COLUMNS + "/table/{tableid}")
	@JsonView({Views.Response.class})
	ColumnResponse getAllColumns(
		@PathVariable(value = "tableid") Long tableid,
	    @RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
	    @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
	    @RequestParam(value = "sortBy", defaultValue = "colname", required = false) String sortBy,
	    @RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir,
	    Principal principal);

	/**
	 * Update column by ID
	 * 
	 * @param columnId
	 * @param columnDto
	 * @param principal
	 * @return
	 */
	@JsonView({Views.Response.class})
	@ApiOperation(value = "Update column by ID")
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping(AppConstants.COLUMNS + "/{id}")
	ResponseEntity<ColumnsDto> updateColumn(@PathVariable(value = "id") Long columnId,
            @Valid 
            @RequestBody 
            @JsonView({Views.ColumnUpdate.class})
			@ApiParam(name = "Column", value = "Column Definition") ColumnsDto columnDto, 
            Principal principal);

	
	/**
	 * Delete the column with ID if it belongs the current user
	 * 
	 * @param columnId
	 * @param principal
	 * @return
	 */
	@ApiOperation(value = "Delete column by ID")
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping(AppConstants.COLUMNS + "/{id}")
	ResponseEntity<String> deleteColumn(@PathVariable(value = "id") Long columnId,
			Principal principal);
	
}
