package be.nss.vit2print.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import be.nss.vit2print.dto.ErrorDTO;

/**
 * Application Global Exception Handler
 */
@ControllerAdvice
public class ApiExceptionHandler {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * Exception handler for any Exception
	 */
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorDTO> handleException(Exception e) {
		logger.error(e.getMessage(), e);
		return new ResponseEntity<ErrorDTO>(new ErrorDTO(e.getMessage()),
				HttpStatus.INTERNAL_SERVER_ERROR);
	}

	/**
	 * Exception handler for Data validation exception
	 */
	@ExceptionHandler({ MethodArgumentNotValidException.class,
			BindException.class })
	public ResponseEntity<ErrorDTO> handleDataValidationException(Exception e) {
		String errorMessage = null;

		if (e instanceof BindException) {
			BindException be = (BindException) e;
			errorMessage = be.getBindingResult().getFieldError()
					.getDefaultMessage();
		} else if (e instanceof MethodArgumentNotValidException) {
			MethodArgumentNotValidException me = (MethodArgumentNotValidException) e;
			errorMessage = me.getBindingResult().getFieldError()
					.getDefaultMessage();
		}

		logger.error(errorMessage);

		return new ResponseEntity<ErrorDTO>(new ErrorDTO(errorMessage),
				HttpStatus.BAD_REQUEST);
	}
}
