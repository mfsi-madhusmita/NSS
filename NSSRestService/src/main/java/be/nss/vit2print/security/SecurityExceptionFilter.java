package be.nss.vit2print.security;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Custom Servlet Filter to catch security related exceptions in the filter
 * chain
 */

@Component
public class SecurityExceptionFilter implements Filter {

	@Autowired
	private SecurityExceptionHandler exceptionHandler;

	@Override
	public void destroy() {
		// Default implementation
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		try {
			chain.doFilter(request, response);
		} catch (Exception e) {
			exceptionHandler.handleExceptions((HttpServletRequest) request,
					(HttpServletResponse) response, e);
		}
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// Default implementation
	}

}
