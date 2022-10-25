package de.amthor.gendb.payload;


import com.fasterxml.jackson.annotation.JsonView;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@Api(value = "Table or storage engine model information")
public class TableformatDto {

	@JsonView({Views.Response.class, Views.TableCreate.class, Views.TableUpdate.class})
	@ApiModelProperty(value = "Format Id")
	private long formatid;

	@JsonView({Views.Response.class})
	@ApiModelProperty(value = "DB Type name")
	private String typename;

	@JsonView({Views.Response.class})
	@ApiModelProperty(value = "Format name")
	private String formatname;

	@JsonView({Views.Response.class})
	@ApiModelProperty(value = "Description")
	private String description;
	
}
