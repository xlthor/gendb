package de.amthor.gendb.payload;

import java.util.Date;

import javax.persistence.Id;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@Api(value = "Database model information")
public class DbTypeDto {

	@ApiModelProperty(value = "Database Type Id")
	long typeid;
	
	/** database type */
	@ApiModelProperty(value = "Type name")
	@Id String typename;
	
	/** DB Version */
	@ApiModelProperty(value = "Database Version")
	String version;
	
	/** a description */
	@ApiModelProperty(value = "Database Description")
	String description;
	
	private Date created;
	private Date updated;
}
