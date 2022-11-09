package de.amthor.gendb.operations;

import java.security.Principal;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.fasterxml.jackson.annotation.JsonView;

import de.amthor.gendb.ddlgenerators.GeneratorBase.Scope;
import de.amthor.gendb.payload.Views;
import de.amthor.gendb.utils.AppConstants;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value = "Rest APIs for SQL DDL Code Generation")
@RequestMapping(AppConstants.API_BASE)
public interface DdlOperations {

	
	@ApiOperation(value = "Generate DB DDL.", 
			notes = "")
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping( produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.TEXT_PLAIN_VALUE, "application/sql"}, value=AppConstants.CODE + "/{scope}/{id}")
	@JsonView(Views.Response.class)
	ResponseEntity<String> getElementSql(
			@PathVariable(value = "id") 
			long elementId, 
			@PathVariable(value = "scope")
			Scope scope,
			@RequestParam(name = "comments", defaultValue = "true")
			Boolean comments,
			@RequestParam(name = "transaction", defaultValue = "true")
			Boolean transaction,
			Principal principal);

	
}