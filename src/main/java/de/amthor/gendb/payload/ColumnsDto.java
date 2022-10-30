package de.amthor.gendb.payload;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonView;

import de.amthor.gendb.entity.Collation;
import de.amthor.gendb.entity.Coltype;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@Api(value = "Column model information")
public class ColumnsDto {

	@JsonView({Views.Response.class, Views.ColumnResponse.class})
	@ApiModelProperty(value = "Column Id")
	long columnid;
	
	@JsonView({Views.Response.class, Views.ColumnResponse.class, Views.ColumnCreate.class, Views.ColumnUpdate.class})
	@ApiModelProperty(value = "Unique Table Name")
	private String colname;
	
	@JsonView({Views.Response.class, Views.ColumnResponse.class, Views.ColumnCreate.class, Views.ColumnUpdate.class})
	@ApiModelProperty(value = "Description / Comment")
	private String description;
	
	@JsonView({Views.Response.class, Views.ColumnResponse.class, Views.ColumnCreate.class, Views.ColumnUpdate.class})
	@ApiModelProperty(value = "Field length")
	private long length;
	
	@JsonView({Views.Response.class, Views.ColumnResponse.class, Views.ColumnCreate.class, Views.ColumnUpdate.class})
	@ApiModelProperty(value = "Default Value if default type is set accordingly")
	private String defaultValues;
	
	@JsonView({Views.Response.class, Views.ColumnResponse.class, Views.ColumnCreate.class, Views.ColumnUpdate.class})
	@ApiModelProperty(value = "Type of Default constraint")
	private String defaultType;
	
	@JsonView({Views.Response.class, Views.ColumnResponse.class, Views.ColumnCreate.class, Views.ColumnUpdate.class})
	@ApiModelProperty(value = "Attributes")
	private String attributes;
	
	@JsonView({Views.Response.class, Views.ColumnResponse.class, Views.ColumnCreate.class, Views.ColumnUpdate.class})
	@ApiModelProperty(value = "Is Column nullable")
	private Boolean nullable;
	
	@JsonView({Views.Response.class, Views.ColumnResponse.class, Views.ColumnCreate.class, Views.ColumnUpdate.class})
	@ApiModelProperty(value = "Type of index")
	private String indexType;
	
	@JsonView({Views.Response.class, Views.ColumnResponse.class, Views.ColumnCreate.class, Views.ColumnUpdate.class})
	@ApiModelProperty(value = "Auto increment column")
	private Boolean autoincrement;
	
	@JsonView({Views.Response.class, Views.ColumnResponse.class, Views.ColumnCreate.class, Views.ColumnUpdate.class})
	@ApiModelProperty(value = "Serial reference")
	private String serial;
	
	@JsonView({Views.Response.class, Views.ColumnResponse.class, Views.ColumnCreate.class, Views.ColumnUpdate.class})
	@ApiModelProperty(value = "Virtuality")
	private String virtuality;
	
	@JsonView({Views.Response.class, Views.ColumnResponse.class, Views.ColumnCreate.class, Views.ColumnUpdate.class})
	@ApiModelProperty(value = "Type of column")
	private Coltype coltype;
	
	@JsonView({Views.Response.class, Views.ColumnResponse.class, Views.ColumnCreate.class, Views.ColumnUpdate.class})
	@ApiModelProperty(value = "Collation")
	Collation collation;
	
	@JsonView({Views.Response.class, Views.ColumnResponse.class, Views.ColumnCreate.class})
	@ApiModelProperty(value = "Enclosing table")
	long tableid;
	
	@JsonView({Views.Response.class, Views.ColumnResponse.class})
	private Date created;
	
	@JsonView({Views.Response.class, Views.ColumnResponse.class})
	private Date updated;
}
