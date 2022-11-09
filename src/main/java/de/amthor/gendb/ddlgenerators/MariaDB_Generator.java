package de.amthor.gendb.ddlgenerators;

import org.springframework.stereotype.Service;

@Service("MariaDB_GeneratorService")
public class MariaDB_Generator extends MySQL_Generator implements DdlGeneratorServiceInterface {

	MariaDB_Generator() {
		this.setDbType("MariaDB");
	}

}
