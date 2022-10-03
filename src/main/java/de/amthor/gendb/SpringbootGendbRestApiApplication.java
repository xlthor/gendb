package de.amthor.gendb;

import java.util.Collections;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import de.amthor.gendb.entity.Role;
import de.amthor.gendb.entity.User;
import de.amthor.gendb.repository.RoleRepository;
import de.amthor.gendb.repository.UserRepository;

@SpringBootApplication
public class SpringbootGendbRestApiApplication implements CommandLineRunner {

	@Bean
	public ModelMapper modelMapper(){
		return new ModelMapper();
	}

	public static void main(String[] args) {
		SpringApplication.run(SpringbootGendbRestApiApplication.class, args);
	}

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private UserRepository userRepository;
	
    @Autowired
    private PasswordEncoder passwordEncoder;
    
	@Override
	@Transactional
	public void run(String... args) throws Exception {

		Role userRole = new Role();
		userRole.setName("ROLE_USER");
		if ( !roleRepository.findByName(userRole.getName()).isPresent() )
			roleRepository.save(userRole);
		
		Role adminRole = new Role();
		adminRole.setName("ROLE_ADMIN");
		if ( !roleRepository.findByName(adminRole.getName()).isPresent() )
			roleRepository.save(adminRole);
		
		User admin = new User();
		admin.setId(1L);
		admin.setEmail("axel@amthor.de");
		admin.setLastname("Amthor");
		admin.setSurname("Axel");
		admin.setPassword(passwordEncoder.encode("123"));
		admin.setLoginname("xlthor");
		
		Role roles = roleRepository.findByName("ROLE_ADMIN").get();
		admin.setRoles(Collections.singleton(roles));
		userRepository.save(admin);

	}
}
