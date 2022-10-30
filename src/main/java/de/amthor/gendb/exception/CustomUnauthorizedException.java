package de.amthor.gendb.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class CustomUnauthorizedException extends RuntimeException {


	private static final long serialVersionUID = -4682808425418616813L;

	public CustomUnauthorizedException(String message) {
        super(message); 
    }

}
