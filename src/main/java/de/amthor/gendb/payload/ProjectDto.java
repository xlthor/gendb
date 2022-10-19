package de.amthor.gendb.payload;

import java.util.Date;
import java.util.Set;

import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonView;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@Api(value = "Project model information")
public class ProjectDto {
	
	@JsonView({Views.Response.class, Views.ParentUpdate.class, Views.Create.class})
	@ApiModelProperty(value = "Project name")
    @NotEmpty(message = "Name should not be null or empty")
	private String name;
	
	@JsonView({Views.Response.class, Views.ParentUpdate.class, Views.Create.class})
	@ApiModelProperty(value = "Project Description name")
    @NotEmpty(message = "Description should not be null or empty")
	private String description;
	
	@JsonView({Views.Response.class, Views.ParentUpdate.class})
	@ApiModelProperty(value = "Project id")
	private long id;
	
	@JsonView({Views.Response.class})
	@ApiModelProperty(value = "Project creation date")
	private Date created;
	
	@JsonView({Views.Response.class})
	@ApiModelProperty(value = "Project last change date")
	private Date updated;
	
	@JsonView({Views.Response.class, Views.ParentUpdate.class})
	@ApiModelProperty(value = "List of project owners")
	private Set<UserDto> users;
	
	@JsonView({Views.Response.class, Views.ParentUpdate.class})
	@ApiModelProperty(value = "List of releases in this project")
	private Set<ReleaseDto> releases;

}
