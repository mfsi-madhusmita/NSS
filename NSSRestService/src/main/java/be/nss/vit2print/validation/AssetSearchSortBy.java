package be.nss.vit2print.validation;

import java.util.ArrayList;
import java.util.List;

public enum AssetSearchSortBy {

	FILENAME, ACCESSED, IMPORTTIME;

	public static List<String> sortByTypes() {
		List<String> enumValues = new ArrayList<String>();
		for (AssetSearchSortBy e : values()) {
			enumValues.add(e.name());
		}

		return enumValues;
	}
}
