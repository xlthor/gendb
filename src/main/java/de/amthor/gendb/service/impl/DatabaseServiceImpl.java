package de.amthor.gendb.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import de.amthor.gendb.entity.Collation;
import de.amthor.gendb.entity.Database;
import de.amthor.gendb.entity.DbType;
import de.amthor.gendb.exception.AlreadyExistsException;
import de.amthor.gendb.exception.ResourceNotFoundException;
import de.amthor.gendb.payload.CollationDto;
import de.amthor.gendb.payload.CollationResponse;
import de.amthor.gendb.payload.DatabaseDto;
import de.amthor.gendb.payload.DatabaseResponse;
import de.amthor.gendb.payload.DbTypeDto;
import de.amthor.gendb.payload.DbTypeResponse;
import de.amthor.gendb.repository.CollationRepository;
import de.amthor.gendb.repository.DBTypeRepository;
import de.amthor.gendb.repository.DatabaseRepository;
import de.amthor.gendb.service.DatabaseService;

@Service
public class DatabaseServiceImpl extends ServiceBase implements DatabaseService {

	@SuppressWarnings("unused")
	private static final Logger LOGGER = LoggerFactory.getLogger(DatabaseService.class);
	
	private DatabaseRepository databaseRepository;

	@Autowired
	DBTypeRepository dbTypeRepository;
	
	@Autowired
	CollationRepository collationRepository;
	
	public DatabaseServiceImpl(DatabaseRepository databaseRepository, ModelMapper mapper) {
		super(mapper);
        this.databaseRepository = databaseRepository;
	}
	
	@Override
	public DatabaseDto createDatabase(@Valid DatabaseDto databaseDto) {

		Database database = genericSimpleMapper(databaseDto, Database.class);
		
		Optional<Database> db = databaseRepository.findByDbname(database.getDbname());
		if ( db.isPresent() )
			throw new AlreadyExistsException("Database", "dbname", database.getDbname());
	
		// link to valid DBType
		String dbTypename = databaseDto.getDbType().getTypename();
		String version = databaseDto.getDbType().getVersion();
		DbType dbType = dbTypeRepository.findByTypenameAndVersion(dbTypename, version).orElseThrow(() -> new ResourceNotFoundException("Database Type", "dbTypeName", dbTypename));	
		
		database.setDbType(dbType);		
		
		Database newDatabase = databaseRepository.save(database);
		return genericSimpleMapper(newDatabase, DatabaseDto.class);
		
	}

	@Override
	public Optional<DatabaseDto> getDatabaseById(long databaseId) {

		Database database = databaseRepository.findById(databaseId).orElseThrow(() -> new ResourceNotFoundException("Database", "DB ID", databaseId));
		
		return Optional.of(genericSimpleMapper(database,DatabaseDto.class));
	}

	@Override
	public DatabaseResponse getAllDatabases(long releaseId, int pageNo, int pageSize, String sortBy, String sortDir) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DatabaseDto updateDatabase(@Valid DatabaseDto databaseDto) {
		
		Database database = genericSimpleMapper(databaseDto, Database.class);
		
		Optional<Database> db = databaseRepository.findById(database.getDbid());
		if ( !db.isPresent() )
			throw new ResourceNotFoundException("Database", "DB ID", database.getDbid());
	
		// link to valid DBType
		String dbTypename = databaseDto.getDbType().getTypename();
		String version = databaseDto.getDbType().getVersion();
		DbType dbType = dbTypeRepository.findByTypenameAndVersion(dbTypename, version).orElseThrow(() -> new ResourceNotFoundException("Database Type", "dbTypeName", dbTypename));	
		
		database.setDbType(dbType);		
		
		Database newDatabase = databaseRepository.save(database);
		return genericSimpleMapper(newDatabase, DatabaseDto.class);
	}

	@Override
	public void deleteDatabase(DatabaseDto databaseDto) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Optional<DbTypeDto> getDbTypeById(Long dbtypeid) {
	
		DbType dbType = dbTypeRepository.findById(dbtypeid).orElseThrow(() -> new ResourceNotFoundException("Database Type", "DB Type ID", dbtypeid));
		
		DbTypeDto dbTypeDto = genericSimpleMapper(dbType,DbTypeDto.class);
		
		return Optional.of(dbTypeDto);
	}

	@Override
	public DbTypeResponse getDatabaseTypes(int pageNo, int pageSize, String sortBy, String sortDir) {

		Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        // create Pageable instance
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        
        Page<DbType> typelist = dbTypeRepository.findAll(pageable);
        
     // get content for page object
        List<DbType> listOfDbTypes = typelist.getContent();

        List<DbTypeDto> content = listOfDbTypes.stream().map(dbtype -> genericSimpleMapper(dbtype, DbTypeDto.class)).collect(Collectors.toList());

        DbTypeResponse dbTypes = new DbTypeResponse();
        dbTypes.setDbtypes(content);
        dbTypes.setPageNo(typelist.getNumber());
        dbTypes.setPageSize(typelist.getSize());
        dbTypes.setTotalElements(typelist.getTotalElements());
        dbTypes.setTotalPages(typelist.getTotalPages());
        dbTypes.setLast(typelist.isLast());

        return dbTypes;
	}

	@Override
	public CollationResponse getCollationsForType(Long dbtypeid, int pageNo, int pageSize, String sortBy, String sortDir) {

		Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        // create Pageable instance
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        
        DbType dbType = dbTypeRepository.findById(dbtypeid).orElseThrow(() -> new ResourceNotFoundException("Database Type", "DB Type ID", dbtypeid));
        
        Page<Collation> typelist = collationRepository.findAllByTypename(pageable, dbType.getTypename());
        
     // get content for page object
        List<Collation> listOfCollations = typelist.getContent();

        List<CollationDto> content = listOfCollations.stream().map(collation -> genericSimpleMapper(collation, CollationDto.class)).collect(Collectors.toList());

        CollationResponse collations = new CollationResponse();
        collations.setCollations(content);
        collations.setPageNo(typelist.getNumber());
        collations.setPageSize(typelist.getSize());
        collations.setTotalElements(typelist.getTotalElements());
        collations.setTotalPages(typelist.getTotalPages());
        collations.setLast(typelist.isLast());

        return collations;
	}

}
