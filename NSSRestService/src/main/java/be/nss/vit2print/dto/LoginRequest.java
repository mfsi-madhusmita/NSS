package be.nss.vit2print.dto;

import static be.nss.vit2print.exception.ExceptionMessages.INVALID_PASSWORD;
import static be.nss.vit2print.exception.ExceptionMessages.INVALID_USERNAME;

import org.hibernate.validator.constraints.NotBlank;

public class LoginRequest {

	@NotBlank(message = INVALID_USERNAME)
	private String username;

	@NotBlank(message = INVALID_PASSWORD)
	private String password;

	public LoginRequest() {
		// TODO Auto-generated constructor stub
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
