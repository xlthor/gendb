package de.amthor.gendb.config;

import java.security.Principal;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.Contact;
import springfox.documentation.service.HttpAuthenticationScheme;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SwaggerConfig {

    private static final String AUTH_KEYNAME = "JWT Token";
	public static final String AUTHORIZATION_HEADER = "Authorization";

    private ApiInfo apiInfo(){
        return new ApiInfo(
                "gendb - Database Generation and Documentation APIs",
                "gendb - Database Generation and Documentation - API Documentation",
                "0.1",
                "Terms of service",
                new Contact("Axel Amthor", "https://github.com/xlthor/gendb", "axel@amthor.de"),
                "Attribution-NonCommercial 4.0 International (CC BY-NC 4.0) ",
                "https://github.com/xlthor/gendb/blob/main/LICENSE.md",
                Collections.emptyList()
        );
    }

    @Bean
    public Docket api(){
    	
    	HttpAuthenticationScheme authenticationScheme = HttpAuthenticationScheme
                .JWT_BEARER_BUILDER
                .name(AUTH_KEYNAME)
                .build();
    	
        return new Docket(DocumentationType.OAS_30)
        		
        		.protocols(new HashSet<>(Arrays.asList("http")))
        		
        		// if this is missing, all the REST Endpoints get a query param "name" in the swagger documentation
        		.ignoredParameterTypes(Principal.class)
        		
                .apiInfo(apiInfo())
                .securityContexts(Arrays.asList(securityContext()))
                .securitySchemes(Collections.singletonList(authenticationScheme))
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build();
    }
    
    private SecurityContext securityContext() {
        return SecurityContext.builder().securityReferences(defaultAuth()).build();
    }

    private List<SecurityReference> defaultAuth(){
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
    	// see https://stackoverflow.com/a/59540298/2374302
        return Arrays.asList(new SecurityReference(AUTH_KEYNAME, authorizationScopes));
    }
}
