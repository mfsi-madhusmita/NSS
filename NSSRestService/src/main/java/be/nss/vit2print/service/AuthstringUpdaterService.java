package be.nss.vit2print.service;

import static be.nss.vit2print.exception.ExceptionMessages.INVALID_AUTHSTRING;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import be.nss.vit2print.exception.APIException;
import be.nss.vit2print.repository.AuthstringUpdaterRepository;

/**
 * Service class for authstring timestamp operation
 */
@Service
public class AuthstringUpdaterService {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private AuthstringUpdaterRepository authstringUpdaterRepository;

	public void updateAuthstringTimestamp(String authstring) {
		int updateCount = authstringUpdaterRepository
				.updateAuthstringTimestamp(authstring,
						String.valueOf(System.currentTimeMillis() / 1000));
		if (!(updateCount > 0)) {
			logger.error(INVALID_AUTHSTRING);
			throw new APIException(INVALID_AUTHSTRING, HttpStatus.BAD_REQUEST);
		}
	}
}
