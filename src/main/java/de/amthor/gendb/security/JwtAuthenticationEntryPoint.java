package de.amthor.gendb.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint 
{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(JwtAuthenticationEntryPoint.class);
	
    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {
        
    	LOGGER.debug("\n======================================> In JwtAuthenticationEntryPoint!\n");
    	
    	response.getWriter().print("{\"error\":1007, \"message\":\"Not logged in\"}");
    	response.setContentType(MediaType.APPLICATION_JSON_VALUE);
    	response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    	response.flushBuffer();

    }
}
