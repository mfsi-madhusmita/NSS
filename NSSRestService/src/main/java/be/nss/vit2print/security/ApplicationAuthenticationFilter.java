package be.nss.vit2print.security;

import static be.nss.vit2print.exception.ExceptionMessages.INVALID_LOGIN_JSON;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.validation.BindException;
import org.springframework.validation.Validator;

import be.nss.vit2print.dto.LoginRequest;
import be.nss.vit2print.exception.APIException;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Custom AuthenticationFilter in order to get login credentials input in JSON
 * format and validation for the same
 */
public class ApplicationAuthenticationFilter extends
		AbstractAuthenticationProcessingFilter {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	// private static final String loginContentType = "application/json";
	private static final String loginContentType = "application/json;charset=UTF-8";
	private static final String loginHttpMethod = "POST";

	@Autowired
	private Validator validator;

	public ApplicationAuthenticationFilter() {
		super(new AntPathRequestMatcher("/login", loginHttpMethod));
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request,
			HttpServletResponse response) throws AuthenticationException {
		
		/*
		 * HTTP Method for login should be POST
		 */
		if (!loginHttpMethod.equals(request.getMethod())) {
			throw new AuthenticationServiceException(
					"Authentication method not supported: "
							+ request.getMethod());
		}

		/*
		 * Content type for login should be JSON
		 */
		String contentType = request.getContentType();
		if (contentType != null) {
			contentType = contentType.replaceAll("\\s+", "");
		}
		if (!loginContentType.equals(contentType)) {
			StringBuilder message = new StringBuilder("Content Type ").append(
					request.getContentType()).append(" Not Supported");
			throw new AuthenticationServiceException(message.toString());
		}

		LoginRequest loginRequest = getLoginRequest(request);

		// validate LoginRequest
		validateLoginRequest(loginRequest);

		UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(
				loginRequest.getUsername(), loginRequest.getPassword());

		authRequest.setDetails(authenticationDetailsSource
				.buildDetails(request));

		return this.getAuthenticationManager().authenticate(authRequest);
	}

	/**
	 * Helper method for converting LoginRequest into JSON format
	 */
	private LoginRequest getLoginRequest(HttpServletRequest request) {
		LoginRequest loginRequest = null;

		try {
			loginRequest = new ObjectMapper().readValue(request.getReader(),
					LoginRequest.class);
		} catch (Exception e) {
			logger.warn(e.getMessage());
			throw new APIException(INVALID_LOGIN_JSON, HttpStatus.BAD_REQUEST);
		}
		return loginRequest;
	}

	/**
	 * Helper method to validate LoginRequest
	 */
	private void validateLoginRequest(LoginRequest loginRequest) {
		BindException bindException = new BindException(loginRequest,
				"Login Request");
		validator.validate(loginRequest, bindException);
		if (bindException != null && bindException.hasErrors()) {
			throw new APIException(bindException.getFieldError()
					.getDefaultMessage(), HttpStatus.BAD_REQUEST);
		}
	}
}
