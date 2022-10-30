package de.amthor.gendb.service.impl;

import java.util.Optional;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.amthor.gendb.entity.Collation;
import de.amthor.gendb.entity.Database;
import de.amthor.gendb.entity.DbType;
import de.amthor.gendb.entity.Release;
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
import de.amthor.gendb.repository.ReleaseRepository;
import de.amthor.gendb.service.DatabaseService;
import de.amthor.gendb.utils.PageableResponse;

@Service
public class DatabaseServiceImpl extends ServiceBase implements DatabaseService {

	@SuppressWarnings("unused")
	private static final Logger LOGGER = LoggerFactory.getLogger(DatabaseService.class);
	
	private DatabaseRepository databaseRepository;

	@Autowired
	DBTypeRepository dbTypeRepository;
	
	@Autowired
	CollationRepository collationRepository;

	@Autowired
	ReleaseRepository releaseRepository;
	
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
		DbType dbType = getDBType(databaseDto.getDbType().getTypeid());	
		
		// link to collation
		Collation collation = getCollation(databaseDto.getCollation().getCollationid());
		
		database.setDbType(dbType);	
		database.setCollation(collation);
		
		Database newDatabase = databaseRepository.save(database);
		return genericSimpleMapper(newDatabase, DatabaseDto.class);
		
	}
	
	@Override
	public Collation getCollation(long collationid) {
		Collation collation = collationRepository.findById(collationid).orElseThrow(() -> new ResourceNotFoundException("Collation", "collation Id", collationid));
		return collation;
	}

	/**
	 * @param typeId
	 * @return
	 */
	@Override
	public DbType getDBType(long typeId) {
		DbType dbType = dbTypeRepository.findById(typeId).orElseThrow(() -> new ResourceNotFoundException("Database Type", "type Id", typeId));
		return dbType;
	}

	@Override
	public Optional<DatabaseDto> getDatabaseById(long databaseId) {

		Database database = databaseRepository.findById(databaseId).orElseThrow(() -> new ResourceNotFoundException("Database", "DB Id", databaseId));
		
		return Optional.of(genericSimpleMapper(database,DatabaseDto.class));
	}

	@Override
	public DatabaseResponse getAllDatabases(long releaseId, int pageNo, int pageSize, String sortBy, String sortDir) {
		
		Release release = releaseRepository.findById(releaseId).orElseThrow(() -> new ResourceNotFoundException("Release", "Release Id", releaseId));

		
		DatabaseResponse dbs = new PageableResponse.Builder()
					.mapper(mapper)
					.pageNo(pageNo)
					.pageSize(pageSize)
					.sortBy(sortBy)
					.sortDir(sortDir)
					.responseDto(new DatabaseResponse())
					.setPage( (pageable) -> { return databaseRepository.findAllByReleaseId(pageable, release.getReleaseid()); })
					.setElementType(DatabaseDto.class)
					.build();
		
        return dbs;
        
	}

	@Override
	public DatabaseDto updateDatabase(@Valid DatabaseDto databaseDto) {
		
		Database database = genericSimpleMapper(databaseDto, Database.class);
		
		Optional<Database> db = databaseRepository.findById(database.getDbid());
		if ( !db.isPresent() )
			throw new ResourceNotFoundException("Database", "DB ID", database.getDbid());
	
		// link to valid DBType
		DbType dbType = getDBType(databaseDto.getDbType().getTypeid());	
		
		// link to collation
		Collation collation = getCollation(databaseDto.getCollation().getCollationid());
		
		database.setDbType(dbType);	
		database.setCollation(collation);
		database.setCreated(db.get().getCreated());
		
		Database newDatabase = databaseRepository.save(database);
		return genericSimpleMapper(newDatabase, DatabaseDto.class);
	}

	@Override
	public void deleteDatabase(DatabaseDto databaseDto) {
		
		Database db = databaseRepository.findById(databaseDto.getDbid()).orElseThrow(() -> new ResourceNotFoundException("Database", "DB Id", databaseDto.getDbid()));

		databaseRepository.deleteById(db.getDbid());
		
	}

	@Override
	public Optional<DbTypeDto> getDbTypeById(Long dbtypeid) {
	
		DbType dbType = dbTypeRepository.findById(dbtypeid).orElseThrow(() -> new ResourceNotFoundException("Database Type", "DB Type ID", dbtypeid));
		
		DbTypeDto dbTypeDto = genericSimpleMapper(dbType,DbTypeDto.class);
		
		return Optional.of(dbTypeDto);
	}

	@Override
	public DbTypeResponse getDatabaseTypes(int pageNo, int pageSize, String sortBy, String sortDir) {

        DbTypeResponse dbTypes = new PageableResponse.Builder()
				.mapper(mapper)
				.pageNo(pageNo)
				.pageSize(pageSize)
				.sortBy(sortBy)
				.sortDir(sortDir)
				.responseDto(new DbTypeResponse())
				.setPage( (pageable) -> dbTypeRepository.findAll(pageable) )
				.setElementType(DbTypeDto.class)
				.build();

        return dbTypes;
	}

	@Override
	public CollationResponse getCollationsForType(Long dbtypeid, int pageNo, int pageSize, String sortBy, String sortDir) {

		DbType dbType = dbTypeRepository.findById(dbtypeid).orElseThrow(() -> new ResourceNotFoundException("Database Type", "DB Type ID", dbtypeid));
		
		CollationResponse collations = new PageableResponse.Builder()
				.mapper(mapper)
				.pageNo(pageNo)
				.pageSize(pageSize)
				.sortBy(sortBy)
				.sortDir(sortDir)
				.responseDto(new CollationResponse())
				.setPage( (pageable) ->  collationRepository.findAllByTypename(pageable, dbType.getTypename()) )
				.setElementType(CollationDto.class)
				.build();
		
        return collations;
	}

}
