/**
 * 
 */
package de.amthor.gendb.service;

import java.util.Optional;

import javax.validation.Valid;

import de.amthor.gendb.entity.GdbTable;
import de.amthor.gendb.entity.Tableformat;
import de.amthor.gendb.payload.CollationDto;
import de.amthor.gendb.payload.TableDto;
import de.amthor.gendb.payload.TableResponse;

/**
 * @author axel
 *
 */
public interface TableService {

	TableDto createTable(@Valid GdbTable table);

	CollationDto getCollation(long collationid);

	Tableformat getTableFormat(long formatid);

	Optional<TableDto> getTable(@Valid TableDto tableDto);

	TableDto updateTable(GdbTable table);

	Optional<TableDto> getTable(Long tableId);

	void deleteTable(Long tableId);

	TableResponse getAllTablesOfDatabase(Long dbid, int pageNo, int pageSize, String sortBy, String sortDir);

}
