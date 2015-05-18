package be.nss.vit2print.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import be.nss.vit2print.dto.GetLevelsInputDTO;
import be.nss.vit2print.model.Level;
import be.nss.vit2print.model.LevelData;
import be.nss.vit2print.model.SearchKeyword;
import be.nss.vit2print.model.SpecifiedLevel;
import be.nss.vit2print.repository.LevelRepository;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class LevelService {

	private static final String DEFAULT_CATEGORY_ID = "0";

	@Autowired
	private LevelRepository levelRepository;

	/**
	 * Service method for getLevels API
	 */
	@Transactional(readOnly = true)
	public LevelData getLevelData(GetLevelsInputDTO getLevelsInputDTO)
			throws JsonParseException, JsonMappingException, IOException {

		String[] parsedLibraryId = parseLibraryId(getLevelsInputDTO
				.getLibraryId());
		String libraryId = parsedLibraryId != null ? parsedLibraryId[0]
				: getLevelsInputDTO.getLibraryId();
		String categoryId = parsedLibraryId != null ? parsedLibraryId[1]
				: DEFAULT_CATEGORY_ID;

		List<Level> levels = levelRepository.findLevels(
				getLevelsInputDTO.getAuthenticationString(), libraryId,
				categoryId);

		List<SearchKeyword> searchKeywords = levelRepository
				.findSearchKeywords(
						getLevelsInputDTO.getAuthenticationString(), libraryId,
						categoryId);

		SpecifiedLevel specifiedLevel = levelRepository.findSpecifiedLevel(
				getLevelsInputDTO.getAuthenticationString(), libraryId,
				categoryId);
		specifiedLevel.setSearchKeywords(searchKeywords);

		LevelData levelData = new LevelData();
		levelData.setLevels(levels);
		levelData.setSpecifiedLevel(specifiedLevel);

		return levelData;
	}

	/**
	 * helper method to convert options as JSON data
	 */
	@SuppressWarnings({ "unchecked", "unused" })
	private Map<String, Object> convertOptionsToJson(String options)
			throws JsonParseException, JsonMappingException, IOException {
		return new ObjectMapper().readValue(options, Map.class);
	}

	/**
	 * helper method to parse libraryId
	 */
	private String[] parseLibraryId(String libraryId) {
		String[] parsedLibraryId = null;
		String token = "_";
		if (libraryId.contains(token)) {
			parsedLibraryId = libraryId.split(token);
		}
		return parsedLibraryId;
	}
}
