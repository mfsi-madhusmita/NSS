package be.nss.vit2print.model;

import java.util.List;

public class LevelData {

	private List<Level> levels;
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
