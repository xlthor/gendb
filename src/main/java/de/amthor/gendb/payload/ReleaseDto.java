package de.amthor.gendb.payload;

import java.util.Date;
import java.util.Set;

import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonView;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@Api(value = "Release model information")
public class ReleaseDto {

	@JsonView({Views.Response.class, Views.ParentUpdate.class, Views.ReleaseUpdate.class})
	@ApiModelProperty(value = "Release Id")
	private long releaseid;
	
	@JsonView({Views.Response.class, Views.Create.class, Views.ReleaseUpdate.class})
	@ApiModelProperty(value = "Release version number")
    @NotEmpty(message = "Version must not be null or empty")
	private String version;
	
	@JsonView({Views.Response.class, Views.Create.class, Views.ReleaseUpdate.class})
	@ApiModelProperty(value = "Release Description")
	private String description;
	
	@JsonView({Views.Response.class, Views.Create.class, Views.ReleaseUpdate.class})
	@ApiModelProperty(value = "Name for the release")
	private String name;
	
	@JsonView({Views.Response.class, Views.Create.class, Views.ReleaseUpdate.class})
	@ApiModelProperty(value = "Date of Release")
	private Date since;
	
	@JsonView({Views.Response.class})
	@ApiModelProperty(value = "Release creation date")
	private Date created;
	
	@JsonView({Views.Response.class})
	@ApiModelProperty(value = "Release last change date")
	private Date updated;
	
	@JsonView({Views.Response.class, Views.Create.class})
	@ApiModelProperty(value = "Parent Project")
	long projectId;
	
	@JsonView({Views.Response.class, Views.ReleaseUpdate.class})
	@ApiModelProperty(value = "List of Databases in this release")
	private Set<DatabaseDto> databases;
	
	
	
}
