package be.nss.vit2print.validation;

import java.util.ArrayList;
import java.util.List;

public enum AssetSortOrder {
	ASC, DESC;

	public static List<String> sortByTypes() {
		List<String> enumValues = new ArrayList<String>();
		for (AssetSortOrder e : values()) {
			enumValues.add(e.name());
		}

		return enumValues;
	}
}
