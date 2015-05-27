package be.nss.vit2print.exception;

public interface ExceptionMessages {

	String INVALID_AUTHENTICATION_STRING = "Invalid username";
	String INVALID_LIBRARY_ID = "Invalid library id, libraryId";
	String INVALID_OPTIONS = "Invalid options";
	String INVALID_USERNAME = "Username is not valid";
	String INVALID_PASSWORD = "Password is not valid";
	String INTERNAL_SERVER_ERROR = "Internal server error";
	String INVALID_LIBRARY_ID_PATTERN = "Invalid library id. Expected format: number or number_number";
	String INVALID_ASSET_SEARCH_SORT_BY = "Invalid sortBy parameter, expected FILENAME or ACCESSED or IMPORTTIME ";
	String INVALID_SEARCH_LEVELS = "Invalid search levels";
	String INVALID_PAGE = "Invalid Page";
	String INVALID_ASSET_PER_PAGE = "Invalid asset per page";
	String INVALID_SEARCH_VALUE = "Invalid search value";
	String INVALID_SORT_ORDER = "Invalid sort order";
	String INVALID_RECURSE = "Invalid recurse";
}
