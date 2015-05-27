package be.nss.vit2print.exception;

import static be.nss.vit2print.exception.ExceptionMessages.INTERNAL_SERVER_ERROR;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
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
	 * Exception handler for any Exception or NullPointerException
	 */
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorDTO> handleException(Exception e) {
		String errorMessage = e.getMessage();
		if (e instanceof NullPointerException) {
			errorMessage = errorMessage != null ? errorMessage
					: INTERNAL_SERVER_ERROR;
		}
		logger.error(errorMessage, e);
		return new ResponseEntity<ErrorDTO>(new ErrorDTO(errorMessage),
				HttpStatus.INTERNAL_SERVER_ERROR);
	}

	/**
	 * Exception handler for Data validation exception
	 */
	@ExceptionHandler({ MethodArgumentNotValidException.class,
			BindException.class, HttpMessageNotReadableException.class })
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
		} else if (e instanceof HttpMessageNotReadableException) {
			HttpMessageNotReadableException je = (HttpMessageNotReadableException) e;
			errorMessage = je.getMostSpecificCause().getMessage();
		}

		logger.error(errorMessage, e);

		return new ResponseEntity<ErrorDTO>(new ErrorDTO(errorMessage),
				HttpStatus.BAD_REQUEST);
	}
}
