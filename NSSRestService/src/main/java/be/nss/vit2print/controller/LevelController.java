package be.nss.vit2print.controller;

import java.io.IOException;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import be.nss.vit2print.dto.ErrorDTO;
import be.nss.vit2print.dto.GetLevelsInputDTO;
import be.nss.vit2print.service.LevelService;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

@RestController
public class LevelController {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private LevelService levelService;

	/**
	 * HTTP GET call to retrieve levels sample url is
	 * http://localhost:8080/NSS/getLevels
	 * ?authenticationString=abcdef&libraryId=3_5&options={"countLevelAssets":
	 * true,"enablePrivateLibrary" : false}
	 */
	@RequestMapping(value = "/admin/getLevels", method = RequestMethod.GET)
	public String getLevels(@Valid GetLevelsInputDTO getLevelsInputDTO)
			throws JsonParseException, JsonMappingException, IOException {
		return levelService.getLevels(getLevelsInputDTO);
	}

	/**
	 * Local exception handler for JSON Conversion Exceptions
	 */
	@ExceptionHandler({ JsonParseException.class, JsonMappingException.class,
			IOException.class })
	public ResponseEntity<ErrorDTO> handleJsonConversionException(Exception e) {
		logger.error(e.getMessage());
		return new ResponseEntity<ErrorDTO>(new ErrorDTO(e.getMessage()),
				HttpStatus.BAD_REQUEST);
	}
}
