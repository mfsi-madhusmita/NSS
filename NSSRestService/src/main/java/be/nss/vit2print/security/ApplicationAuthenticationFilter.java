package be.nss.vit2print.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import be.nss.vit2print.dto.LoginRequest;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Custom AuthenticationFilter to get login credentials input in JSON format
 */
public class ApplicationAuthenticationFilter extends
		AbstractAuthenticationProcessingFilter {

	private static final String loginContentType = "application/json";
	private static final String loginHttpMethod = "POST";

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
		if (!loginContentType.equals(request.getContentType())) {
			throw new AuthenticationServiceException("Content Type "
					+ request.getContentType() + " Not Supported");
		}

		LoginRequest loginRequest = getLoginRequest(request);
		UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(
				loginRequest.getUsername(), loginRequest.getPassword());

		authRequest.setDetails(authenticationDetailsSource
				.buildDetails(request));

		return this.getAuthenticationManager().authenticate(authRequest);
	}

	private LoginRequest getLoginRequest(HttpServletRequest request) {
		LoginRequest loginRequest = null;

		try {
			loginRequest = new ObjectMapper().readValue(request.getReader(),
					LoginRequest.class);
		} catch (Exception e) {
			System.out.println("Exception at--------------> " + e.getMessage());
		}
		return loginRequest;
	}
}
