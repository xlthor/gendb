package de.amthor.gendb.payload;

import java.util.Date;
import java.util.Set;

import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonView;

import de.amthor.gendb.entity.Role;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@Api(value = "GenDB User model information")
public class UserDto {

	@ApiModelProperty(value = "Unique User ID")
    @JsonView({Views.Response.class, Views.ParentUpdate.class})
    private long id;
    
	@ApiModelProperty(value = "User surname")
    @JsonView({Views.Response.class, Views.Update.class, Views.Create.class})
    @NotEmpty(message = "Name should not be null or empty")
    private String surname;
	
	@ApiModelProperty(value = "User lastname")
	@JsonView({Views.Response.class, Views.Update.class, Views.Create.class})
    @NotEmpty(message = "Name should not be null or empty")
    private String lastname;
    
    /*
     * for security reasons we hide the login name, the password and the roles from the responses!
     */
	@JsonView({Views.Update.class, Views.Create.class})
    @NotEmpty(message = "Login should not be null or empty")
    private String loginname;
    
	@JsonView({Views.Response.class, Views.Update.class, Views.Create.class})
    @NotEmpty(message = "Email should not be null or empty")
    private String email;
    
	@JsonView({Views.Update.class, Views.Create.class})
    @NotEmpty(message = "Password should not be null or empty")
    private String password;

    @JsonView({Views.Response.class})
    private Date created;
    @JsonView({Views.Response.class})
    private Date updated;

    private Set<Role> roles;
}
