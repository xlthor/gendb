package de.amthor.gendb.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class ReferenceException extends RuntimeException {

	private static final long serialVersionUID = -2220100183482568060L;

    public ReferenceException(String message) {
        super(message); 
    }

}
