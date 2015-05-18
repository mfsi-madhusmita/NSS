package be.nss.vit2print.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "levels", "specifiedLevel" })
public class LevelData {

	@JsonProperty("levels")
	private List<Level> levels;

	@JsonProperty("specifiedLevel")
	private SpecifiedLevel specifiedLevel;

	public LevelData() {
		// Default Constructor
	}

	public List<Level> getLevels() {
		return levels;
	}

	public void setLevels(List<Level> levels) {
		this.levels = levels;
	}

	public SpecifiedLevel getSpecifiedLevel() {
		return specifiedLevel;
	}

	public void setSpecifiedLevel(SpecifiedLevel specifiedLevel) {
		this.specifiedLevel = specifiedLevel;
	}
}
