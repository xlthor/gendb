package de.amthor.gendb.controller;

import java.security.Principal;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import de.amthor.gendb.entity.User;
import de.amthor.gendb.exception.ResourceNotFoundException;
import de.amthor.gendb.repository.UserRepository;

public abstract class ControllerBase {

	@Autowired
    private UserRepository userRepository;
	
	protected ModelMapper mapper;
	
    public ControllerBase(ModelMapper mapper) {
        this.mapper = mapper;
    }
    
    /**
     * Determine the logged in user. If the user could not be found (huh ...?) throws an Exception
     * If the user is found, no need for an Optional then.
     * 
     * @param principal
     * @return Logged in User
     */
    public User getLoggedInUser(Principal principal) {
		Optional<User> user = userRepository.findByLoginnameOrEmail(principal.getName(),principal.getName());
    	if ( user.isEmpty() )
    		throw new ResourceNotFoundException("User", "name", principal.getName());
		return user.get();
	}
    
    /**
     * Generic simple object mapper
     * 
     * @param <To> Target class
     * @param <From> Source class 
     * @param source Source
     * @param type Target type
     * @return DTO
     */
    public <To, From> To genericSimpleMapper(From source, Class<To>type) {
    	To target = source != null ? mapper.map(source, type) : null;
        return target;
    }

}
