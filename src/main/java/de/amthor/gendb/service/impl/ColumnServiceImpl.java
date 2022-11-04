package de.amthor.gendb.service.impl;

import java.util.Optional;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.amthor.gendb.entity.Collation;
import de.amthor.gendb.entity.Coltype;
import de.amthor.gendb.entity.Columns;
import de.amthor.gendb.exception.AlreadyExistsException;
import de.amthor.gendb.exception.ResourceNotFoundException;
import de.amthor.gendb.payload.ColumnResponse;
import de.amthor.gendb.payload.ColumnsDto;
import de.amthor.gendb.repository.CollationRepository;
import de.amthor.gendb.repository.ColtypesRepository;
import de.amthor.gendb.repository.ColumnsRepository;
import de.amthor.gendb.service.ColumnService;
import de.amthor.gendb.utils.PageableResponse;

@Service
public class ColumnServiceImpl extends ServiceBase implements ColumnService {

	private ColumnsRepository columnsRepository;

	@Autowired
	CollationRepository collationRepository;

	@Autowired
	ColtypesRepository coltypesRepository;

	
	public ColumnServiceImpl(ColumnsRepository columnsRepository, ModelMapper mapper) {
		super(mapper);
		this.columnsRepository = columnsRepository;
	}

//	@SuppressWarnings("unused")
	private static final Logger LOGGER = LoggerFactory.getLogger(ColumnService.class);

	@Override
	public ColumnsDto saveColumn(@Valid ColumnsDto columnDto) {
		
		// do we have a new column (id = 0)?
		if ( columnDto.getColumnid() <= 0L ) {
			Optional<Columns> column = columnsRepository.findByColnameAndTableid(columnDto.getColname(), columnDto.getTableid() );
			if ( column.isPresent() )
				throw new AlreadyExistsException("Column", "colname", columnDto.getColname() + "/" + columnDto.getColumnid());
		}
		
		Columns newColumn = genericSimpleMapper(columnDto, Columns.class);
		
		// link to collation
		Collation collation = getCollation(columnDto.getCollation().getCollationid());
		
		// link to column type
		Coltype coltype = getColtype(columnDto.getColtype().getColtypeid());
		
		newColumn.setCollation(collation);
		newColumn.setColtype(coltype);
		
		newColumn = columnsRepository.save(newColumn);
		return genericSimpleMapper(newColumn, ColumnsDto.class);
	}

	/**
	 * @param columnDto
	 * @return
	 */
	private Coltype getColtype(long coltypeid) {
		return coltypesRepository.findById(coltypeid).orElseThrow(() 
				                   	-> new ResourceNotFoundException("Column type", "coltype Id", coltypeid));
	}
	
	@Override
	public Collation getCollation(long collationid) {
		Collation collation = collationRepository.findById(collationid).orElseThrow(() 
				 					-> new ResourceNotFoundException("Collation", "collation Id", collationid));
		return collation;
	}

	@Override
	public Optional<ColumnsDto> findById(long columnId) {
		Optional<Columns> col = columnsRepository.findById(columnId);
		LOGGER.debug("==================> " + col.toString());
		if ( col.isPresent() )
			return Optional.of(genericSimpleMapper(col.get(), ColumnsDto.class));
		else
			return Optional.empty();
	}

	@Override
	public ColumnResponse getAllColumnsOfTable(Long tableid, int pageNo, int pageSize, String sortBy, String sortDir) {
		
		ColumnResponse colResponse = new PageableResponse.Builder()
				.mapper(mapper)
				.pageNo(pageNo)
				.pageSize(pageSize)
				.sortBy(sortBy)
				.sortDir(sortDir)
				.responseDto(new ColumnResponse()) 
				.setPage( (pageable) -> columnsRepository.findAllByTableid(pageable, tableid) )
				.setElementType(ColumnsDto.class)
				.build();
		
		return colResponse;
	}

	@Override
	public void deleteById(Long columnId) {
		
		columnsRepository.deleteById(columnId);
		
	}

	@Override
	public Optional<ColumnsDto> findByName(String colname) {
		Optional<Columns> col = columnsRepository.findByColname(colname);
		LOGGER.debug("==================> " + col.toString());
		if ( col.isPresent() )
			return Optional.of(genericSimpleMapper(col.get(), ColumnsDto.class));
		else
			return Optional.empty();
	}
}
