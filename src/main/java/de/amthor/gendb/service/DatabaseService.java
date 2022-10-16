package de.amthor.gendb.service;

import javax.validation.Valid;

import de.amthor.gendb.payload.DatabaseDto;

public interface DatabaseService {

	public DatabaseDto createDatabase(@Valid DatabaseDto databaseDto) ;

}
