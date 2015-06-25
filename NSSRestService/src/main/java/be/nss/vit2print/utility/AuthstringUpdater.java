package be.nss.vit2print.utility;

import static be.nss.vit2print.exception.ExceptionMessages.INVALID_AUTHSTRING;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import be.nss.vit2print.exception.APIException;
import be.nss.vit2print.service.AuthstringUpdaterService;

/**
 * Custom interceptor to update authstring timestamp in the database
 */
@Component
public class AuthstringUpdater extends HandlerInterceptorAdapter {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private AuthstringUpdaterService authstringUpdaterService;

	@Autowired
	private StringParser parser;

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws APIException {
		String authstring = request.getHeader("authstring");
		authstringValidator(authstring);
		authstringUpdaterService.updateAuthstringTimestamp(authstring);
		return true;
	}

	private void authstringValidator(String authstring) {
		if (parser.isBlank(authstring)) {
			logger.error(INVALID_AUTHSTRING);
			throw new APIException(INVALID_AUTHSTRING, HttpStatus.BAD_REQUEST);
		}
	}
}
