package be.nss.vit2print.security;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import be.nss.vit2print.dto.ErrorDTO;
import be.nss.vit2print.exception.APIException;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Exception handler for security related exceptions
 */
@Component
public class SecurityExceptionHandler {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	public void handleExceptions(HttpServletRequest request,
			HttpServletResponse response, Exception e) throws IOException {
		logger.error(e.getMessage());

		response.setContentType("application/json");
		if (e instanceof APIException) {
			APIException apiE = (APIException) e;
			response.setStatus(apiE.getHttpStatus().value());
		} else if (e instanceof AuthenticationException) {
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		} else {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			logger.error(e.getMessage(),e);
		}

		new ObjectMapper().writeValue(response.getOutputStream(), new ErrorDTO(
				e.getMessage()));
	}
}
