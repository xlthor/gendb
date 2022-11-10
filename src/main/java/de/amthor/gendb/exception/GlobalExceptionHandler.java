package de.amthor.gendb.exception;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.ConstraintViolationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import de.amthor.gendb.payload.ErrorDetails;

@ControllerAdvice
public class GlobalExceptionHandler { //extends ResponseEntityExceptionHandler {

	public static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);
	
    // handle specific exceptions
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorDetails> handleResourceNotFoundException(ResourceNotFoundException exception,
                                                                        WebRequest webRequest){
        ErrorDetails errorDetails = new ErrorDetails(1001, new Date(), exception.getMessage(),
                webRequest.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(GendbAPIException.class)
    public ResponseEntity<ErrorDetails> handleGendAPIException(GendbAPIException exception,
                                                                        WebRequest webRequest){
        ErrorDetails errorDetails = new ErrorDetails(1002, new Date(), exception.getMessage(),
                webRequest.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }
    
    @ExceptionHandler(AlreadyExistsException.class)
    public ResponseEntity<ErrorDetails> handleAlreadyExistsException(AlreadyExistsException exception,
                                                                        WebRequest webRequest){
        ErrorDetails errorDetails = new ErrorDetails(1003, new Date(), exception.getMessage(),
                webRequest.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }
    
    @ExceptionHandler(ChildRecordExists.class)
    public ResponseEntity<ErrorDetails> handleChildRecordExistsException(ChildRecordExists exception, WebRequest webRequest){
		ErrorDetails errorDetails = new ErrorDetails(1004, new Date(), exception.getMessage(), webRequest.getDescription(false));
		return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
	}
    
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorDetails> handleAuthenticationException(AccessDeniedException exception, WebRequest webRequest) {
    	LOGGER.error(exception.getMessage(), exception);
    	ErrorDetails errorDetails = new ErrorDetails(1005, new Date(), exception.getMessage(), webRequest.getDescription(false));
		return new ResponseEntity<>(errorDetails, HttpStatus.UNAUTHORIZED);
    }
    
    @ExceptionHandler(ReferenceException.class)
    public ResponseEntity<ErrorDetails> handleReferenceException(ReferenceException exception, WebRequest webRequest) {
    	ErrorDetails errorDetails = new ErrorDetails(1006, new Date(), exception.getMessage(), webRequest.getDescription(false));
		return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ErrorDetails> handleUnauthorizedException(AuthenticationException exception, WebRequest webRequest) {
    	ErrorDetails errorDetails = new ErrorDetails(1007, new Date(), exception.getMessage(), webRequest.getDescription(false));
		return new ResponseEntity<>(errorDetails, HttpStatus.UNAUTHORIZED);
    }
    
    @ExceptionHandler(CustomUnauthorizedException.class)
    public ResponseEntity<ErrorDetails> handleUnauthorizedException(CustomUnauthorizedException exception, WebRequest webRequest) {
    	ErrorDetails errorDetails = new ErrorDetails(1007, new Date(), exception.getMessage(), webRequest.getDescription(false));
		return new ResponseEntity<>(errorDetails, HttpStatus.UNAUTHORIZED);
    }
    
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ErrorDetails> handleNotSupportedMethodException(HttpRequestMethodNotSupportedException exception, WebRequest webRequest) {
    	ErrorDetails errorDetails = new ErrorDetails(1009, new Date(), exception.getMessage(), webRequest.getDescription(false));
		return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }
    
    /**
     * Mainly catches the @Pattern... etc annotation validation exceptions
     * @param ex
     * @return
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Object> handleConstraintViolationException(ConstraintViolationException ex)  
	{
		List<ErrorDetails> errors = new ArrayList<>();
		ex.getConstraintViolations().forEach((error) -> {
			String message = error.getMessage();
			String fieldName =  error.getPropertyPath().toString();
			ErrorDetails errorDetails = new ErrorDetails(1010, new Date(), message, fieldName);
			errors.add(errorDetails);
		});

		return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }
    
    @ExceptionHandler(GeneratorException.class)
    public ResponseEntity<ErrorDetails> handleGeneratorException(GeneratorException exception, WebRequest webRequest) {
    	ErrorDetails errorDetails = new ErrorDetails(1011, new Date(), exception.getMessage(), webRequest.getDescription(false));
		return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }
    
    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status,
                                                                  WebRequest request) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) ->{
            String fieldName = ((FieldError)error).getField();
            String message = error.getDefaultMessage();
            errors.put(fieldName, message);
        });

        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }
    
    // global exceptions
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetails> handleGlobalException(Exception exception,
                                                               WebRequest webRequest){
        ErrorDetails errorDetails = new ErrorDetails(1000, new Date(), exception.getMessage(), webRequest.getDescription(false));
        
        LOGGER.error(exception.getMessage(), exception);
        
        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
