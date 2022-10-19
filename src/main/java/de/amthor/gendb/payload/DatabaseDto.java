package de.amthor.gendb.payload;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonView;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@Api(value = "Database model information")
public class DatabaseDto {
	
	@JsonView({Views.Response.class, Views.ReleaseUpdate.class, Views.DatabaseUpdate.class})
	@ApiModelProperty(value = "Database Id")
	private long dbid;
	
	@JsonView({Views.Response.class, Views.Create.class, Views.DatabaseCreate.class})
	@ApiModelProperty(value = "Database Name")
	private String dbname;
	
	@JsonView({Views.Response.class, Views.Create.class, Views.DatabaseCreate.class})
	@ApiModelProperty(value = "Database Description")
	private String description;
	
	@JsonView({Views.Response.class, Views.Create.class, Views.DatabaseCreate.class})
	@ApiModelProperty(value = "Release id")
	private long releaseId;
	
	/** DB Type */
	@JsonView({Views.Response.class, Views.DatabaseCreate.class, Views.DatabaseUpdate.class})
	@ApiModelProperty(value = "Database Type")
	private DbTypeDto dbType;
	
	@JsonView({Views.Response.class})
	@ApiModelProperty(value = "Release creation date")
	private Date created;
	
	@JsonView({Views.Response.class})
	@ApiModelProperty(value = "Release last change date")
	private Date updated;

}
