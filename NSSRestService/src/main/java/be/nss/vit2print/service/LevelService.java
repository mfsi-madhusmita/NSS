package be.nss.vit2print.service;

import java.io.IOException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import be.nss.vit2print.dto.GetLevelsInputDTO;
import be.nss.vit2print.repository.LevelRepository;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class LevelService {

	@Autowired
	private LevelRepository levelRepository;

	/**
	 * Service method for getLevels API
	 */
	public String getLevels(GetLevelsInputDTO getLevelsInputDTO)
			throws JsonParseException, JsonMappingException, IOException {
		return levelRepository.getLevels(
				getLevelsInputDTO.getAuthenticationString(),
				getLevelsInputDTO.getLibraryId(),
				convertOptionsToJson(getLevelsInputDTO.getOptions()));
	}

	/**
	 * helper method to convert options as JSON data
	 */
	@SuppressWarnings("unchecked")
	private Map<String, Object> convertOptionsToJson(String options)
			throws JsonParseException, JsonMappingException, IOException {
		return new ObjectMapper().readValue(options, Map.class);
	}
}
