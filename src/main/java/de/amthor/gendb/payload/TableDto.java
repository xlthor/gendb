package de.amthor.gendb.payload;

import java.util.Date;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonView;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@Api(value = "Table model information")
public class TableDto {

	@JsonView({Views.Response.class, Views.DatabaseResponse.class})
	@ApiModelProperty(value = "Table Id")
	private long tableid;
	
	/** unique table name */
	@JsonView({Views.Response.class, Views.DatabaseResponse.class, Views.TableCreate.class, Views.TableUpdate.class})
	@ApiModelProperty(value = "Table Name")
	String tablename;
	
	/** documentation / comment of this table */
	@JsonView({Views.Response.class, Views.TableCreate.class, Views.TableUpdate.class})
	@ApiModelProperty(value = "Table description or comment")
	private String description;
	
	@JsonView({Views.Response.class})
	@ApiModelProperty(value = "Column definitions")
	Set<ColumnsDto> columns;
	
	/** enclosing database */
	@JsonView({Views.Response.class, Views.TableCreate.class, Views.TableUpdate.class})
	@ApiModelProperty(value = "Database/Scheme of this table")
	private long databaseId;
	
	/** collation and charset */
	@JsonView({Views.Response.class, Views.TableCreate.class, Views.TableUpdate.class})
	@ApiModelProperty(value = "Collation and charset of this table")
	private CollationDto collation;
	
	/**format or storage engine of a table */
	@JsonView({Views.Response.class, Views.TableCreate.class, Views.TableUpdate.class})
	@ApiModelProperty(value = "Tableformat (storage engine) of this table")
	private TableformatDto tableformat;
	
	@JsonView({Views.Response.class})
	@ApiModelProperty(value = "Table creation date")
	private Date created;
	
	@JsonView({Views.Response.class})
	@ApiModelProperty(value = "Table last change date")
	private Date updated;
	
}
