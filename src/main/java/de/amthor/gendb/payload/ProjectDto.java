package de.amthor.gendb.payload;

import java.util.Date;
import java.util.Set;

import javax.validation.constraints.NotEmpty;

import de.amthor.gendb.entity.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@Api(value = "Project model information")
public class ProjectDto {
	
	@ApiModelProperty(value = "Project name")
    @NotEmpty(message = "Name should not be null or empty")
	private String name;
	
	@ApiModelProperty(value = "Project Description name")
    @NotEmpty(message = "Description should not be null or empty")
	private String description;
	
	@ApiModelProperty(value = "Project id")
	private long id;
	
	@ApiModelProperty(value = "Project creation date")
	private Date created;
	
	@ApiModelProperty(value = "Project last change date")
	private Date updated;
	
	@ApiModelProperty(value = "List of project owners")
	private Set<User> users;

}
