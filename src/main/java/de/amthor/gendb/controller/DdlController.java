package de.amthor.gendb.controller;

import java.security.Principal;
import java.util.Set;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import de.amthor.gendb.ddlgenerators.DdlGeneratorServiceInterface;
import de.amthor.gendb.ddlgenerators.GeneratorBase.Scope;
import de.amthor.gendb.ddlgenerators.services.BeanFactoryDynamicAutowireService;
import de.amthor.gendb.entity.Database;
import de.amthor.gendb.entity.Release;
import de.amthor.gendb.exception.ResourceNotFoundException;
import de.amthor.gendb.operations.DdlOperations;
import de.amthor.gendb.payload.DatabaseDto;
import de.amthor.gendb.payload.TableDto;
import de.amthor.gendb.service.DatabaseService;
import de.amthor.gendb.service.ProjectService;
import de.amthor.gendb.service.ReleaseService;
import de.amthor.gendb.service.TableService;
import de.amthor.gendb.utils.CodeBuffer;

@RestController
public class DdlController extends ControllerBase implements DdlOperations {

	private static final Logger LOGGER = LoggerFactory.getLogger(DdlController.class);
	
	@Autowired
	DatabaseService  databaseService;
	
	@Autowired
	ReleaseService releaseService;
	
	@Autowired
	ProjectService projectService;
	
	@Autowired
	TableService tableService;
	
	@Autowired
	BeanFactoryDynamicAutowireService dynamicAutowireService;
	
	public DdlController(ModelMapper mapper) {
		super(mapper);
		LOGGER.info("=========================> New SqlController");
	}

	@Override
	public ResponseEntity<String> getElementSql(long elementId, Scope scope, Boolean comments, Boolean transaction, Principal principal) {
		
		switch ( scope ) {
			case RELEASE:
				return runReleaseGenerator(elementId, scope, comments, transaction, principal);
			
			case DATABASE:
				return runDatabaseGenerator(elementId, scope, comments, transaction, principal);
				
			case TABLE:
				return runTableGenerator(elementId, scope, comments, transaction, principal);
			
			default:
				break;
		}
		return null;
		
	}
	
	
	/**
	 * Generate code for all databases and their tables within a release
	 * 
	 * @param elementId Id of the element denoted in scope
	 * @param scope scope of the generator, Release in this case
	 * @param comments Generate Comments
	 * @param transaction Encapsulate into a transaction
	 * @param principal current user
	 * @return
	 */
	private ResponseEntity<String> runReleaseGenerator(long elementId, Scope scope, Boolean comments, Boolean transaction, Principal principal) {
		
		CodeBuffer codeBuffer = new CodeBuffer();
		
		generateReleaseDdl(elementId, scope, comments, transaction, codeBuffer);
		
		return ResponseEntity.ok()
				.contentType(new MediaType("application", "sql"))
				.header("Content-disposition", "inline; filename=\"generated.sql\"")
				.body(codeBuffer.toString());
	}

	/**
	 * @param elementId
	 * @param scope
	 * @param comments
	 * @param transaction
	 * @param codeBuffer
	 */
	private void generateReleaseDdl(long elementId, Scope scope, Boolean comments, Boolean transaction, CodeBuffer codeBuffer) {
		
		Release release = releaseService.getReleaseById(elementId).orElseThrow(() -> new ResourceNotFoundException("Release", "id", elementId));
		
		Set<Database> databases = release.getDatabases();
		
		databases.stream().forEach( (database) -> {
			generateDatabaseDdl(elementId, scope, comments, transaction, codeBuffer);
		});
	}

	/**
	 * Generate code for a particular table
	 * 
	 * @param elementId Id of the element denoted in scope
	 * @param scope scope of the generator, table in this case
	 * @param comments Generate Comments
	 * @param transaction Encapsulate into a transaction
	 * @param principal current user
	 * @return
	 */
	private ResponseEntity<String> runTableGenerator(long elementId, Scope scope, Boolean comments, Boolean transaction, Principal principal) {
		
		CodeBuffer codeBuffer = new CodeBuffer();
		
		generateTableDdl(elementId, scope, comments, transaction, codeBuffer);
		
		return ResponseEntity.ok()
				.contentType(new MediaType("application", "sql"))
				.header("Content-disposition", "inline; filename=\"generated.sql\"")
				.body(codeBuffer.toString());
	}

	/**
	 * @param elementId
	 * @param scope
	 * @param comments
	 * @param transaction
	 * @param codeBuffer
	 */
	private void generateTableDdl(long elementId, Scope scope, Boolean comments, Boolean transaction, CodeBuffer codeBuffer) {
		
		TableDto tableDto = tableService.getTable(elementId).orElseThrow(() -> new ResourceNotFoundException("Table", "id", elementId));
		
		DatabaseDto databaseDto = databaseService.getDatabaseById(tableDto.getDatabaseId()).orElseThrow(() -> new ResourceNotFoundException("Database", "id", tableDto.getDatabaseId()));
		
		DdlGeneratorServiceInterface service = dynamicAutowireService.getGenerator(databaseDto.getDbType().getTypename()); 
		if ( service == null )
			throw new ResourceNotFoundException("Generator", "Bean", databaseDto.getDbType().getTypename());
		
		service.setScope(scope);
		
		service.writeTableDdl(codeBuffer, tableDto, comments, transaction);
	}

	/**
	 * Generate code for a particular databases and its tables
	 * 
	 * @param elementId Id of the element denoted in scope
	 * @param scope scope of the generator, database in this case
	 * @param comments Generate Comments
	 * @param transaction Encapsulate into a transaction
	 * @param principal current user
	 * @return
	 */
	private ResponseEntity<String> runDatabaseGenerator(long elementId, Scope scope, Boolean comments, Boolean transaction, Principal principal) {
		
		CodeBuffer codeBuffer = new CodeBuffer();
		
		generateDatabaseDdl(elementId, scope, comments, transaction, codeBuffer);
		
		return ResponseEntity.ok()
				.contentType(new MediaType("application", "sql"))
				.header("Content-disposition", "inline; filename=\"generated.sql\"")
				.body(codeBuffer.toString());
		
	}

	/**
	 * @param elementId
	 * @param scope
	 * @param codeBuffer
	 */
	private void generateDatabaseDdl(long elementId, Scope scope, Boolean comments, Boolean transaction, CodeBuffer codeBuffer) {
		DatabaseDto databaseDto = databaseService.getDatabaseById(elementId).orElseThrow(() -> new ResourceNotFoundException("Database", "id", elementId));
		
		DdlGeneratorServiceInterface service = dynamicAutowireService.getGenerator(databaseDto.getDbType().getTypename()); 
		if ( service == null )
			throw new ResourceNotFoundException("Generator", "Bean", databaseDto.getDbType().getTypename());
		
		service.setScope(scope);
		service.writeDatabaseDdl(codeBuffer, databaseDto, comments, transaction);
		
		databaseDto.getTables().stream().forEach((tableDto) -> {
			service.writeTableDdl(codeBuffer, tableDto, comments, transaction);
		});
	}
	

}
