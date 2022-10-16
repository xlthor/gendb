package de.amthor.gendb.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class ChildRecordExists extends RuntimeException {

	private static final long serialVersionUID = -3220100183487268060L;
	private String resourceName;

    public ChildRecordExists(String resourceName) {
        super(String.format("At least one child of type '%s' exists", resourceName)); 
        this.resourceName = resourceName;
    }

    public String getResourceName() {
        return resourceName;
    }

}
