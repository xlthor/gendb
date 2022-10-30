package de.amthor.gendb.controller;

import java.security.Principal;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import de.amthor.gendb.operations.ColumnOperations;
import de.amthor.gendb.payload.ColumnResponse;
import de.amthor.gendb.payload.ColumnsDto;

@RestController
public class ColumnController extends ControllerBase implements ColumnOperations {

	public ColumnController(ModelMapper mapper) {
		super(mapper);
		
	}

	@Override
	public ResponseEntity<ColumnsDto> createColumn(@Valid ColumnsDto columnDto, Principal principal) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<ColumnsDto> getColumnById(long columnId, Principal principal) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ColumnResponse getAllColumns(Long tableid, int pageNo, int pageSize, String sortBy, String sortDir, Principal principal) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<ColumnsDto> updateColumn(Long columnId, @Valid ColumnsDto columnDto, Principal principal) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<String> deleteColumn(Long columnId, Principal principal) {
		// TODO Auto-generated method stub
		return null;
	}

}
