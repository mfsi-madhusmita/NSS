package be.nss.vit2print.exception;

public interface ExceptionMessages {

	String INVALID_AUTHENTICATION_STRING = "Invalid username";
	String INVALID_LIBRARY_ID = "Invalid library id";
	String INVALID_OPTIONS = "Invalid options";
	String INVALID_USERNAME = "Username is not valid";
	String INVALID_PASSWORD = "Password is not valid";
	String INVALID_LOGIN_JSON = "Invalid JSON format of credentials, Expected format \n{\n\t\"username\":\"username\",\n\t\"password\":\"password\"\n} ";
}
