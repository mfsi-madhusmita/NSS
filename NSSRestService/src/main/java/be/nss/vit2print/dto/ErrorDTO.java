package be.nss.vit2print.dto;

public class ErrorDTO {

	private String message;

	public ErrorDTO() {
		// default constructor
	}

	public ErrorDTO(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
