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

import de.amthor.gendb.payload.TableDto;
import de.amthor.gendb.payload.TableResponse;
import de.amthor.gendb.payload.Views;
import de.amthor.gendb.utils.AppConstants;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(value = "CRUD Rest APIs for table operations")
@RequestMapping(AppConstants.API_BASE)
public interface TableOperations {

	/**
	 * Create a new table for this user
	 * 
	 * @param tableDto
	 * @param principal
	 * @return
	 */
	@JsonView({Views.Response.class})
	@ApiOperation(value = "Create new table within a database")
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(AppConstants.TABLES)
	ResponseEntity<TableDto> createTable(
			@Valid 
			@RequestBody
			@JsonView({Views.TableCreate.class})
			@ApiParam(name = "Table", value = "Table Definition") TableDto tableDto, 
			Principal principal);

	/**
	 * Get table by ID.
	 * 
	 * @param tableId
	 * @param principal
	 * @return
	 */
	@JsonView({Views.Response.class})
	@ApiOperation(value = "Get table by ID.")
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(AppConstants.TABLES + "/{id}")
	ResponseEntity<TableDto> getTableById(
    		@PathVariable(value = "id")long tableId, Principal principal);

	/**
	 * Get all tables of the current user
	 * @param pageNo
	 * @param pageSize
	 * @param sortBy
	 * @param sortDir
	 * @param principal
	 * @return
	 */
	@ApiOperation(value = "Get All tables within a database")
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(AppConstants.TABLES + "/database/{dbid}")
	@JsonView({Views.Response.class})
	TableResponse getAllTables(
		@PathVariable(value = "dbid") Long dbid,
	    @RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
	    @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
	    @RequestParam(value = "sortBy", defaultValue = "tablename", required = false) String sortBy,
	    @RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir,
	    Principal principal);

	/**
	 * Update table by ID
	 * 
	 * @param tableId
	 * @param tableDto
	 * @param principal
	 * @return
	 */
	@JsonView({Views.Response.class})
	@ApiOperation(value = "Update table by ID")
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping(AppConstants.TABLES + "/{id}")
	ResponseEntity<TableDto> updateTable(@PathVariable(value = "id") Long tableId,
            @Valid 
            @RequestBody 
            @JsonView({Views.TableUpdate.class})
			@ApiParam(name = "Table", value = "Table Definition") TableDto tableDto, 
            Principal principal);

	
	/**
	 * Delete the table with ID if it belongs the current user
	 * 
	 * @param tableId
	 * @param principal
	 * @return
	 */
	@ApiOperation(value = "Delete table by ID")
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping(AppConstants.TABLES + "/{id}")
	ResponseEntity<String> deleteTable(@PathVariable(value = "id") Long tableId,
			Principal principal);
	
}
