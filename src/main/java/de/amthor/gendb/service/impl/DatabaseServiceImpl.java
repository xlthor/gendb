package de.amthor.gendb.service.impl;

import java.util.Optional;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.amthor.gendb.entity.Database;
import de.amthor.gendb.entity.DbType;
import de.amthor.gendb.exception.AlreadyExistsException;
import de.amthor.gendb.exception.ResourceNotFoundException;
import de.amthor.gendb.payload.DatabaseDto;
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

}
