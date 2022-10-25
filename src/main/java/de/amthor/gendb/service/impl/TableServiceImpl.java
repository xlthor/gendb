package de.amthor.gendb.service.impl;

import java.util.Optional;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.amthor.gendb.entity.Collation;
import de.amthor.gendb.entity.GdbTable;
import de.amthor.gendb.entity.Tableformat;
import de.amthor.gendb.payload.CollationDto;
import de.amthor.gendb.payload.TableDto;
import de.amthor.gendb.repository.CollationRepository;
import de.amthor.gendb.repository.FormatRepository;
import de.amthor.gendb.repository.TableRepository;
import de.amthor.gendb.service.TableService;

@Service
public class TableServiceImpl extends ServiceBase implements TableService {

	@Autowired
	TableRepository tableRepository;
	
	@Autowired
	CollationRepository collationRepository;
	
	@Autowired
	FormatRepository tableformatRepository;
	
	public TableServiceImpl(ModelMapper mapper) {
		super(mapper);
	}

	@Override
	public TableDto createTable(@Valid GdbTable table) {
		
			table = tableRepository.save(table);
			
		return genericSimpleMapper(table, TableDto.class);
	}

	@Override
	public CollationDto getCollation(long collationid) {
		Optional<Collation> collation = collationRepository.findById(collationid);
		return collation.isPresent()  ? genericSimpleMapper(collation.get(), CollationDto.class) : null;
	}

	@Override
	public Tableformat getTableFormat(long formatid) {
		Optional<Tableformat> tf = tableformatRepository.findById(formatid);
		return tf.isPresent()  ? tf.get() : null;
	}

	@Override
	public Optional<TableDto> getTable(@Valid TableDto tableDto) {
		
		Optional<GdbTable> table = tableRepository.findByTablenameAndDatabaseId(tableDto.getTablename(), tableDto.getDatabaseId());
				
		return table.isPresent() ? Optional.of(genericSimpleMapper(table.get(), TableDto.class)) : Optional.empty();
	}

	@Override
	public TableDto updateTable(GdbTable table) {

		table = tableRepository.save(table);
		
		return genericSimpleMapper(table, TableDto.class);
		
	}

	@Override
	public Optional<TableDto> getTable(Long tableId) {
		
		Optional<GdbTable> table = tableRepository.findById(tableId);
		
		if ( table.isPresent() )
			return Optional.of(genericSimpleMapper(table.get(), TableDto.class));
			
		return Optional.empty();
	}

	@Override
	public void deleteTable(Long tableId) {
		
		tableRepository.deleteById(tableId);
		
	}

}
