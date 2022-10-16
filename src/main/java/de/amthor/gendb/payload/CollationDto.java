package de.amthor.gendb.payload;

import io.swagger.annotations.Api;
import lombok.Data;

@Data
@Api(value = "Collation Model")
public class CollationDto {

	long collationid;
	
	private String typename;
	
	private String collation;
	private String characterset;
	
}
