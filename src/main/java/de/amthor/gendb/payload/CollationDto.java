package de.amthor.gendb.payload;

import com.fasterxml.jackson.annotation.JsonView;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@Api(value = "Collation Model")
public class CollationDto {

	@ApiModelProperty(value = "Collation Id")
	@JsonView({Views.Response.class, Views.TableUpdate.class, Views.TableCreate.class})
	long collationid;
	
	@ApiModelProperty(value = "Name of database type")
	@JsonView({Views.Response.class})
	private String typename;
	
	@ApiModelProperty(value = "Name of collation")
	@JsonView({Views.Response.class})
	private String collation;
	
	@ApiModelProperty(value = "Enclosed character set")
	@JsonView({Views.Response.class})
	private String characterset;
	
}
