package be.nss.vit2print.dto;

import static be.nss.vit2print.exception.ExceptionMessages.INVALID_AUTHENTICATION_STRING;
import static be.nss.vit2print.exception.ExceptionMessages.INVALID_LIBRARY_ID;
import static be.nss.vit2print.exception.ExceptionMessages.INVALID_OPTIONS;

import org.hibernate.validator.constraints.NotBlank;

public class GetLevelsInputDTO {

	@NotBlank(message = INVALID_AUTHENTICATION_STRING)
	private String username;

	@NotBlank(message = INVALID_LIBRARY_ID)
	private String libraryId;

	@NotBlank(message = INVALID_OPTIONS)
	private String options;

	public GetLevelsInputDTO() {
		// Default constructor
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getLibraryId() {
		return libraryId;
	}

	public void setLibraryId(String libraryId) {
		this.libraryId = libraryId;
	}

	public String getOptions() {
		return options;
	}

	public void setOptions(String options) {
		this.options = options;
	}
}
