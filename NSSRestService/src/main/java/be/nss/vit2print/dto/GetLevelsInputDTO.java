package be.nss.vit2print.dto;

import static be.nss.vit2print.exception.ExceptionMessages.INVALID_LIBRARY_ID;
import static be.nss.vit2print.exception.ExceptionMessages.INVALID_USERNAME;
import static be.nss.vit2print.exception.ExceptionMessages.INVALID_LIBRARY_ID_PATTERN;

import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;

public class GetLevelsInputDTO {

	private static final String LIBRARY_ID_PATTERN = "[0-9]*[_]*[0-9]+$";

	@NotBlank(message = INVALID_USERNAME)
	private String username;

	@NotBlank(message = INVALID_LIBRARY_ID)
	@Pattern(regexp = LIBRARY_ID_PATTERN, message = INVALID_LIBRARY_ID_PATTERN)
	private String libraryId;

	/*
	 * @NotBlank(message = INVALID_OPTIONS) private String options;
	 */

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

	/*
	 * public String getOptions() { return options; }
	 * 
	 * public void setOptions(String options) { this.options = options; }
	 */
}
