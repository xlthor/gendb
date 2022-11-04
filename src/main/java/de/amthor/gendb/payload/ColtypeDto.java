package de.amthor.gendb.payload;

import com.fasterxml.jackson.annotation.JsonView;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@Api(value = "Column Type Model")
public class ColtypeDto {

	@ApiModelProperty(value = "Column Type Id")
	@JsonView({Views.Response.class, 
		Views.ColumnCreate.class, 
		Views.ColumnUpdate.class})
	private long coltypeid;
	
	@ApiModelProperty(value = "Column Type Name")
	@JsonView({Views.Response.class})
	private String type;
	
	@ApiModelProperty(value = "Column Type Group")
	@JsonView({Views.Response.class})
	private String tgroup; // "group" is reserved SQL!
	
	@ApiModelProperty(value = "Column Type Description")
	@JsonView({Views.Response.class})
	private String description;
	
	@ApiModelProperty(value = "Database Type")
	@JsonView({Views.Response.class})
	private String dbtype;
	
}
