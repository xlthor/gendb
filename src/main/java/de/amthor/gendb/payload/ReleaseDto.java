package de.amthor.gendb.payload;

import java.util.Date;

import javax.validation.constraints.NotEmpty;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@Api(value = "Release model information")
public class ReleaseDto {

	@ApiModelProperty(value = "Release Id")
	private long releaseid;
	
	@ApiModelProperty(value = "Release version number")
    @NotEmpty(message = "Version must not be null or empty")
	private String version;
	
	@ApiModelProperty(value = "Release Description")
	private String description;
	
	@ApiModelProperty(value = "Name for the release")
	private String name;
	
	@ApiModelProperty(value = "Date of Release")
	private Date since;
	
	@ApiModelProperty(value = "Release creation date")
	private Date created;
	
	@ApiModelProperty(value = "Release last change date")
	private Date updated;
	
	@ApiModelProperty(value = "Parent Project")
	long projectId;
	
	
	
}
