package de.amthor.gendb.payload;

import java.util.Date;

import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonView;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@Api(value = "Database model information")
public class DbTypeDto {

	@ApiModelProperty(value = "Database Type Id")
	@JsonView({Views.Response.class, Views.DatabaseCreate.class})
	long typeid;
	
	/** database type */
	@ApiModelProperty(value = "Type name")
	@JsonView({Views.Response.class})
	@Id String typename;
	
	/** DB Version */
	@ApiModelProperty(value = "Database Version")
	@JsonView({Views.Response.class})
	String version;
	
	/** a description */
	@ApiModelProperty(value = "Database Description")
	@JsonView({Views.Response.class})
	String description;
	
	private Date created;
	private Date updated;
}
