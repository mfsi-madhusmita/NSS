package be.nss.vit2print.exception;

public interface ExceptionMessages {

	String INVALID_AUTHENTICATION_STRING = "Invalid username";
	String INVALID_LIBRARY_ID = "Invalid library id, libraryId can not be null or blank";
	String INVALID_OPTIONS = "Invalid options";
	String INVALID_USERNAME = "Username is not valid";
	String INVALID_PASSWORD = "Password is not valid";
	String INTERNAL_SERVER_ERROR = "Internal server error";
	String INVALID_LIBRARY_ID_PATTERN = "Invalid library id. Expected format: number or number_number";
}
