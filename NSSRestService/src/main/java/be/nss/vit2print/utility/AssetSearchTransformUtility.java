package be.nss.vit2print.utility;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import be.nss.vit2print.model.AssetLevel;
import be.nss.vit2print.model.AssetParent;
import be.nss.vit2print.model.Keyword;
import be.nss.vit2print.model.KeywordGroup;

@Component
public class AssetSearchTransformUtility {

	public void transformAssetLevel(AssetLevel assetLevel) {
		if (assetLevel != null) {
			String parentsAsString = assetLevel.getParentsAsString();
			// sample string 3+My images|3_5+Test|3_8+25HO52 FullFamily_Brochure
			if (parentsAsString != null) {
				List<AssetParent> assetParents = getAssetParents(StringUtils
						.split(parentsAsString, "|"));
				assetLevel.setAssetParents(assetParents);
			}
		}
	}

	public List<KeywordGroup> transformKeywordGroups(
			List<KeywordGroup> keywordGroups) {
		List<KeywordGroup> transformedKeywordGroups = null;

		if (keywordGroups != null) {
			transformedKeywordGroups = new ArrayList<KeywordGroup>();
			Map<String, List<Keyword>> keywordGroupsMap = getKeywordGroupsMap(keywordGroups);
			if (keywordGroupsMap.size() > 0) {
				for (Map.Entry<String, List<Keyword>> me : keywordGroupsMap
						.entrySet()) {
					KeywordGroup keywordGroup = getKeywordGroup(me.getKey());
					if (keywordGroup != null) {
						keywordGroup.setKeywords(me.getValue());
					}
					transformedKeywordGroups.add(keywordGroup);
				}
			}
		}

		return transformedKeywordGroups;
	}

	private List<AssetParent> getAssetParents(String[] parentsAsStringArray) {
		List<AssetParent> assetParents = null;

		// sample parentsAsStringArray={3+My images,3_5+Test}
		if (parentsAsStringArray != null) {
			assetParents = new ArrayList<AssetParent>();
			for (String parentAsString : parentsAsStringArray) {
				if (parentAsString != null) {
					String[] parentValuesAsArray = StringUtils.split(
							parentAsString, "+");
					if (parentValuesAsArray != null) {
						AssetParent assetParent = new AssetParent(
								parentValuesAsArray[0], parentValuesAsArray[1]);
						assetParents.add(assetParent);
					}
				}
			}
		}
		return assetParents;
	}

	private Map<String, List<Keyword>> getKeywordGroupsMap(
			List<KeywordGroup> keywordGroups) {
		Map<String, List<Keyword>> keywordGroupsMap = new HashMap<String, List<Keyword>>();
		String keywordGroupAsString = null;
		List<Keyword> keywords = null;

		Iterator<KeywordGroup> itr = keywordGroups.iterator();

		while (itr.hasNext()) {
			KeywordGroup keywordGroup = itr.next();
			if (keywordGroup != null) {
				if (!keywordGroup.getKeywordGroupAsString().equals(
						keywordGroupAsString)) {
					if (keywordGroupAsString != null) {
						keywordGroupsMap.put(keywordGroupAsString, keywords);
					}

					keywordGroupAsString = keywordGroup
							.getKeywordGroupAsString();
					keywords = new ArrayList<Keyword>();
				}
				if (keywordGroup.getKeywordGroupAsString().equals(
						keywordGroupAsString)) {
					keywords.add(keywordGroup.getKeyword());
				}
				if (!itr.hasNext()) {
					keywordGroupsMap.put(keywordGroupAsString, keywords);
				}
			}
		}
		return keywordGroupsMap;
	}

	private KeywordGroup getKeywordGroup(String keywordGroupAsString) {
		KeywordGroup keywordGroup = null;

		if (keywordGroupAsString != null) {
			String[] keywordGroupAsArray = StringUtils.split(
					keywordGroupAsString, "+");
			if (keywordGroupAsArray != null) {
				keywordGroup = new KeywordGroup(
						Long.parseLong(keywordGroupAsArray[0]),
						Integer.parseInt(keywordGroupAsArray[1]),
						keywordGroupAsArray[2]);
			}
		}
		return keywordGroup;
	}
}
