package be.nss.vit2print.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

/**
 * To reply for unauthenticated access
 */
@Component
public class ApplicationAuthenticationEntryPoint implements
		AuthenticationEntryPoint {

	@Autowired
	private SecurityExceptionHandler exceptionHandler;

	@Override
	public void commence(HttpServletRequest request,
			HttpServletResponse response,
			AuthenticationException authenticationException)
			throws IOException, ServletException {
		exceptionHandler.handleExceptions(request, response,
				authenticationException);
	}

}
