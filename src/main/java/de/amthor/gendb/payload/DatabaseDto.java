package de.amthor.gendb.payload;

import java.util.Date;

import de.amthor.gendb.entity.DbType;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@Api(value = "Database model information")
public class DatabaseDto {
	
	@ApiModelProperty(value = "Database Id")
	private long dbid;
	
	@ApiModelProperty(value = "Database Name")
	private String dbname;
	
	@ApiModelProperty(value = "Database Description")
	private String description;
	
	@ApiModelProperty(value = "Release id")
	private long releaseId;
	
	/** DB Type */
	@ApiModelProperty(value = "Database Type")
	private DbType dbType;
	
	@ApiModelProperty(value = "Release creation date")
	private Date created;
	
	@ApiModelProperty(value = "Release last change date")
	private Date updated;

}
