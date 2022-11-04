package de.amthor.gendb.service;

import java.util.Optional;

import javax.validation.Valid;

import de.amthor.gendb.entity.Collation;
import de.amthor.gendb.payload.ColumnResponse;
import de.amthor.gendb.payload.ColumnsDto;

public interface ColumnService  {

	ColumnsDto saveColumn(@Valid ColumnsDto columnDto);

	Collation getCollation(long collationid);

	Optional<ColumnsDto> findById(long columnId);

	ColumnResponse getAllColumnsOfTable(Long tableid, int pageNo, int pageSize, String sortBy, String sortDir);

	void deleteById(Long columnId);

	Optional<ColumnsDto> findByName(String colname);

}
