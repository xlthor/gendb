package de.amthor.gendb.config;

import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.servers.Server;
import springfox.documentation.oas.web.OpenApiTransformationContext;
import springfox.documentation.oas.web.WebMvcOpenApiTransformationFilter;
import springfox.documentation.spi.DocumentationType;

@Component
public class SpringfoxSwaggerHostResolver implements WebMvcOpenApiTransformationFilter {

	@Value("${app.uri}")
    private String uri;
	
  @Override
  public OpenAPI transform(OpenApiTransformationContext<HttpServletRequest> context) {
    OpenAPI swagger = context.getSpecification();
    
    Server server1 = new Server();
    server1.setUrl("http://localhost:8080");
    
    Server server2 = new Server();
    server2.setUrl(uri);
    
    swagger.setServers(Arrays.asList(server1, server2));
    
    return swagger;
  }

  @Override
  public boolean supports(DocumentationType docType) {
    return docType.equals(DocumentationType.OAS_30);
  }
}
