package de.amthor.gendb.payload;

import java.util.Date;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonView;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@Api(value = "Database model information")
public class DatabaseDto {
	
	@JsonView({Views.Response.class, Views.DatabaseResponse.class, Views.ReleaseUpdate.class, Views.DatabaseUpdate.class})
	@ApiModelProperty(value = "Database Id")
	private long dbid;
	
	@JsonView({Views.Response.class, Views.DatabaseResponse.class, Views.Create.class, Views.DatabaseCreate.class})
	@ApiModelProperty(value = "Database Name")
	private String dbname;
	
	@JsonView({Views.DatabaseResponse.class, Views.Create.class, Views.DatabaseCreate.class})
	@ApiModelProperty(value = "Database Description")
	private String description;
	
	@JsonView({Views.DatabaseResponse.class, Views.Create.class, Views.DatabaseCreate.class})
	@ApiModelProperty(value = "Release id")
	private long releaseId;
	
	/** DB Type */
	@JsonView({Views.DatabaseResponse.class, Views.DatabaseCreate.class, Views.DatabaseUpdate.class})
	@ApiModelProperty(value = "Database Type")
	private DbTypeDto dbType;
	
	@JsonView({Views.DatabaseResponse.class})
	@ApiModelProperty(value = "Database tables")
	private Set<TableDto> tables;
	
	@JsonView({Views.DatabaseResponse.class})
	@ApiModelProperty(value = "Release creation date")
	private Date created;
	
	@JsonView({Views.DatabaseResponse.class})
	@ApiModelProperty(value = "Release last change date") 
	private Date updated;

}
