package de.amthor.gendb.service;

import java.util.Optional;

import javax.validation.Valid;

import de.amthor.gendb.payload.CollationResponse;
import de.amthor.gendb.payload.DatabaseDto;
import de.amthor.gendb.payload.DatabaseResponse;
import de.amthor.gendb.payload.DbTypeDto;
import de.amthor.gendb.payload.DbTypeResponse;

public interface DatabaseService {

	public DatabaseDto createDatabase(@Valid DatabaseDto databaseDto) ;

	public Optional<DatabaseDto> getDatabaseById(long databaseId);

	public DatabaseResponse getAllDatabases(long releaseId, int pageNo, int pageSize, String sortBy, String sortDir);

	public DatabaseDto updateDatabase(@Valid DatabaseDto databaseDto);

	public void deleteDatabase(DatabaseDto databaseDto);

	public Optional<DbTypeDto> getDbTypeById(Long dbtypeid);

	public DbTypeResponse getDatabaseTypes(int pageNo, int pageSize, String sortBy, String sortDir);

	public CollationResponse getCollationsForType(Long dbtypeid, int pageNo, int pageSize, String sortBy,
			String sortDir);

}
