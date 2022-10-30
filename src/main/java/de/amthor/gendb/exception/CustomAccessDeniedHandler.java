package de.amthor.gendb.exception;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;


@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

	private static final Logger LOGGER = LoggerFactory.getLogger(CustomAccessDeniedHandler.class);
	
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException arg2)
            throws IOException, ServletException {
    	
    	LOGGER.info("\n======================================> In CustomAccessDeniedHandler!\n");
    	
    	response.getWriter().print("{\"error\":1008, \"message\":\"Access denied\"}");
    	response.setContentType(MediaType.APPLICATION_JSON_VALUE);
    	response.setStatus(HttpServletResponse.SC_FORBIDDEN);
    	response.flushBuffer();
    }

}
