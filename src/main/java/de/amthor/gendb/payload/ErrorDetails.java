package de.amthor.gendb.payload;

import java.util.Date;

public class ErrorDetails {

	private Date timestamp;
    private String message;
    private String details;
    private long errorCode = 1000L;

    public ErrorDetails(Date timestamp, String message, String details) {
        this.timestamp = timestamp;
        this.message = message;
        this.details = details;
    }
    
    public ErrorDetails(long errorCode, Date timestamp, String message, String details) {
        this.timestamp = timestamp;
        this.message = message;
        this.details = details;
        this.errorCode = errorCode;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public String getMessage() {
        return message;
    }

    public String getDetails() {
        return details;
    }
    
   	public long getErrorCode() {
   		return errorCode;
   	}
 
   	public void setErrorCode(long errorCode) {
   		this.errorCode = errorCode;
   	}
}
