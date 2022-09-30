package de.amthor.gendb.exception;

import org.springframework.http.HttpStatus;

public class GendbAPIException extends RuntimeException {

    private static final long serialVersionUID = 4602210369919764766L;
	private HttpStatus status;
    private String message;

    public GendbAPIException(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }

    public GendbAPIException(String message, HttpStatus status, String message1) {
        super(message);
        this.status = status;
        this.message = message1;
    }

    public HttpStatus getStatus() {
        return status;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
