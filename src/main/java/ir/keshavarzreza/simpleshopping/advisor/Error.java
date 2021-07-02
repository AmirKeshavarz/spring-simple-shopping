package ir.keshavarzreza.simpleshopping.advisor;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.HashMap;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Error {
	private Long timeStamp;
	private final int status;
	private final String error;
	private final String message;
	private HashMap details = new HashMap();
	private String path;

	Error(int status, String message) {
		this.status = status;
		this.message = message;
		this.error = "Bad Request";
	}

	public Long getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(Long timeStamp) {
		this.timeStamp = timeStamp;
	}

	public int getStatus() {
		return status;
	}

	public String getMessage() {
		return message;
	}

	public String getError() {
		return error;
	}

	public void addFieldError(String path, String message) {
		details.put(path, message);
	}

	public HashMap getDetails() {
		return details;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
}