package de.amthor.gendb.service;

import java.util.Optional;

import javax.validation.Valid;

import de.amthor.gendb.entity.Collation;
import de.amthor.gendb.entity.DbType;
import de.amthor.gendb.payload.CollationResponse;
import de.amthor.gendb.payload.DatabaseDto;
import de.amthor.gendb.payload.DatabaseResponse;
import de.amthor.gendb.payload.DbTypeDto;
import de.amthor.gendb.payload.DbTypeResponse;

/**
 * Interface for the database services to create, update delete and retrieve databases of given release within a users project.
 * 
 * @author axel
 */
public interface DatabaseService {

	/**
	 * @param databaseDto
	 * @return
	 */
	public DatabaseDto createDatabase(@Valid DatabaseDto databaseDto) ;

	/**
	 * @param databaseId
	 * @return
	 */
	public Optional<DatabaseDto> getDatabaseById(long databaseId);

	/**
	 * @param releaseId
	 * @param pageNo
	 * @param pageSize
	 * @param sortBy
	 * @param sortDir
	 * @return
	 */
	public DatabaseResponse getAllDatabases(long releaseId, int pageNo, int pageSize, String sortBy, String sortDir);

	/**
	 * @param databaseDto
	 * @return
	 */
	public DatabaseDto updateDatabase(@Valid DatabaseDto databaseDto);

	/**
	 * @param databaseDto
	 */
	public void deleteDatabase(DatabaseDto databaseDto);

	/**
	 * @param dbtypeid
	 * @return
	 */
	public Optional<DbTypeDto> getDbTypeById(Long dbtypeid);

	/**
	 * @param pageNo
	 * @param pageSize
	 * @param sortBy
	 * @param sortDir
	 * @return
	 */
	public DbTypeResponse getDatabaseTypes(int pageNo, int pageSize, String sortBy, String sortDir);

	/**
	 * @param dbtypeid
	 * @param pageNo
	 * @param pageSize
	 * @param sortBy
	 * @param sortDir
	 * @return
	 */
	public CollationResponse getCollationsForType(Long dbtypeid, int pageNo, int pageSize, String sortBy,
			String sortDir);

	/**
	 * @param typeId
	 * @return
	 */
	public DbType getDBType(long typeId);

	public Collation getCollation(long collationid);

}
