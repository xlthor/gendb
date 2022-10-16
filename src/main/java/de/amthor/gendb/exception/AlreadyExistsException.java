package de.amthor.gendb.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class AlreadyExistsException extends RuntimeException {

	private static final long serialVersionUID = -3220100183487268060L;
	private String resourceName;
    private String fieldName;
    private String fieldValue;

    public AlreadyExistsException(String resourceName, String fieldName, String fieldValue) {
        super(String.format("%s with %s = '%s' already exists", resourceName, fieldName, fieldValue)); 
        this.resourceName = resourceName;
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }

    public String getResourceName() {
        return resourceName;
    }

    public String getFieldName() {
        return fieldName;
    }

    public String getFieldValue() {
        return fieldValue;
    }
}
