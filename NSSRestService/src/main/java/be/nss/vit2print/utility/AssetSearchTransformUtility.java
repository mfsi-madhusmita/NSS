package be.nss.vit2print.utility;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import be.nss.vit2print.dto.AssetSearchDTO;
import be.nss.vit2print.model.Asset;
import be.nss.vit2print.model.AssetLevel;
import be.nss.vit2print.model.AssetParent;
import be.nss.vit2print.model.Keyword;
import be.nss.vit2print.model.KeywordGroup;

/**
 * Transform Utility class for search assets api
 */
@Component
public class AssetSearchTransformUtility {

	private static final String TOKEN_FOR_PARENTS = "\\|";
	private static final String PLUS_TOKEN = "\\+";
	private static final String TOKEN_FOR_ASSET_ID = "\\_";
	private static final String TOKEN_TO_COMPLETE_ASSETID = "_";
	private static final String TOKEN_FOR_SEARCHLEVEL_AND_ASSETIDS = ",";

	@Autowired
	private StringParser parser;

	public void transformAssetSearchDTO(AssetSearchDTO assetSearch) {
		assetSearch.setRecurse(transformRecurse(assetSearch.getRecurse()));
		assetSearch.setSearchLevel(transformSearchLevel(assetSearch
				.getSearchlevels()));
	}

	public void transformAssetLevel(AssetLevel assetLevel) {
		if (assetLevel != null) {
			String parentsAsString = assetLevel.getParentsAsString();
			// sample string 3+My images|3_5+Test|3_8+25HO52 FullFamily_Brochure
			if (parentsAsString != null) {
				List<AssetParent> assetParents = getAssetParents(parser
						.doSplit(parentsAsString, TOKEN_FOR_PARENTS));
				assetLevel.setAssetParents(assetParents);
			}
		}
	}

	public String transformAssetIdsToString(List<Asset> assets) {
		String transFormedAssetIdsToString = null;
		List<String> transformedAssetIds = null;
		if (assets != null) {
			transformedAssetIds = new ArrayList<String>();
			for (Asset asset : assets) {
				transformedAssetIds.add(transformAssetId(asset.getAssetId()));
			}
			if (transformedAssetIds.size() > 0) {
				transFormedAssetIdsToString = parser
						.doTokenizeList(transformedAssetIds,
								TOKEN_FOR_SEARCHLEVEL_AND_ASSETIDS);
			}
		}

		return transFormedAssetIdsToString;
	}

	public List<Asset> transformAssetsWithKeywordGroup(
			List<KeywordGroup> keywordGroups, List<Asset> assets,
			String libraryId) {
		// Logic:
		// visualize data format like this
		// asset_id:1,keywordGroupAsString:0+TECHNICAL,keyword:{32,assetid,3_1,N}
		// asset_id:1,keywordGroupAsString:0+TECHNICAL,keyword:{33,assetid,3_1,N}
		// asset_id:2,keywordGroupAsString:0+TECHNICAL,keyword:{34,assetid,3_1,N}
		// asset_id:3,keywordGroupAsString:0+TECHNICAL,keyword:{35,assetid,3_1,N}
		Map<String, List<KeywordGroup>> normalisedKeywordGroupsByAssetId = normaliseKeywordGroupsByAssetId(keywordGroups);
		List<KeywordGroup> normaliseKeywordsByKeywordGroup = normaliseKeywordsByKeywordGroup(normalisedKeywordGroupsByAssetId);

		// each keywordgroup has asset_id which comes from database,
		// asset_id in Asset object is different as asset_id in Asset object
		// contains libraryId+_ before asset_id like 3_152
		// transform asset_id in each keywordgroup by left padding libraryid+_
		if (normaliseKeywordsByKeywordGroup != null) {
			for (KeywordGroup keywordGroup : normaliseKeywordsByKeywordGroup) {
				appendLibraryIdToKeywordGroupAssetId(keywordGroup, libraryId);
			}

			// find which keywordgroup relates to which Asset object with the
			// help
			// of asset_id
			for (Asset asset : assets) {
				if (normaliseKeywordsByKeywordGroup != null) {
					for (KeywordGroup keywordGroup : normaliseKeywordsByKeywordGroup) {
						if (asset.getAssetId()
								.equals(keywordGroup.getAssetId())) {
							asset.setKeywordGroups(keywordGroup);
							break;
						}
					}
				}
			}
		}
		return assets;
	}

	private String transformRecurse(String recurse) {
		return recurse.equals("Y") ? "1" : "0";
	}

	private String transformSearchLevel(String[] searchLevel) {
		return parser.doTokenizeArray(searchLevel,
				TOKEN_FOR_SEARCHLEVEL_AND_ASSETIDS);
	}

	private List<AssetParent> getAssetParents(String[] parentsAsStringArray) {
		List<AssetParent> assetParents = null;
		// sample parentsAsStringArray={3+My images,3_5+Test}
		if (parentsAsStringArray != null) {
			assetParents = new ArrayList<AssetParent>();
			for (String parentAsString : parentsAsStringArray) {
				if (parentAsString != null) {
					String[] parentValuesAsArray = parser.doSplit(
							parentAsString, PLUS_TOKEN);
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

	private String transformAssetId(String assetId) {
		String transformedAssetId = null;
		if (assetId != null) {
			transformedAssetId = parser.doSplit(assetId, TOKEN_FOR_ASSET_ID)[1];
		}

		return transformedAssetId;
	}

	private Map<String, List<KeywordGroup>> normaliseKeywordGroupsByAssetId(
			List<KeywordGroup> keywordGroups) {
		// Logic:
		// normalise multiple keywordgroups to one asset id
		// asset_id=1,list<keywordgroups>
		// asset_id=1,list<keywordgroups>
		// into hashmap
		Map<String, List<KeywordGroup>> normalisedKeywordGroupsByAssetId = new HashMap<String, List<KeywordGroup>>();
		List<KeywordGroup> normalisedKeywordGroups = null;
		String tempAssetId = null;

		if (keywordGroups != null) {
			Iterator<KeywordGroup> itr = keywordGroups.iterator();

			while (itr.hasNext()) {
				KeywordGroup keywordGroup = itr.next();
				if (keywordGroup != null) {
					if (!keywordGroup.getAssetId().equals(tempAssetId)) {
						if (tempAssetId != null) {
							normalisedKeywordGroupsByAssetId.put(tempAssetId,
									normalisedKeywordGroups);
						}
						tempAssetId = keywordGroup.getAssetId();
						normalisedKeywordGroups = new ArrayList<KeywordGroup>();
					}
					if (keywordGroup.getAssetId().equals(tempAssetId)) {
						normalisedKeywordGroups.add(keywordGroup);
					}
					if (!itr.hasNext()) {
						normalisedKeywordGroupsByAssetId.put(tempAssetId,
								normalisedKeywordGroups);
					}
				}
			}
		}

		return normalisedKeywordGroupsByAssetId;
	}

	private List<KeywordGroup> normaliseKeywordsByKeywordGroup(
			Map<String, List<KeywordGroup>> normalisedKeywordGroupsByAssetId) {
		// According to the logic there always be one keywordgroup per
		// asset,
		// and multiple keywords to one keywordgroup, if we go by
		// normalisedKeywordGroupsByAssetId
		// 1 asset_id has multiple keyword group, keyword group data must be
		// duplicated
		// normalize this hashmap into list of keywordgroup and each
		// keywordgroup contains list of keywords
		List<KeywordGroup> normaliseKeywordsByKeywordGroup = null;

		if (normalisedKeywordGroupsByAssetId.size() > 0) {
			normaliseKeywordsByKeywordGroup = new ArrayList<KeywordGroup>();
			for (Map.Entry<String, List<KeywordGroup>> me : normalisedKeywordGroupsByAssetId
					.entrySet()) {
				KeywordGroup preparedKeywordGroup = prepareKeywordGroup(me
						.getValue().get(0));
				if (preparedKeywordGroup != null) {
					preparedKeywordGroup.setKeywords(prepareKeywords(
							preparedKeywordGroup, me.getValue()));
				}
				normaliseKeywordsByKeywordGroup.add(preparedKeywordGroup);
			}
		}

		return normaliseKeywordsByKeywordGroup;

	}

	private KeywordGroup prepareKeywordGroup(KeywordGroup keywordGroup) {
		// prepare keywordgroup data, from database application gets data in
		// concatenated string as keywordGroupAsString,split this string to get
		// sortindex, name and uniqueId is randomly generated number of length 6
		if (keywordGroup != null) {
			keywordGroup.setUniqueId(generateRandomNumbers());
			String[] keywordGroupAsArray = parser.doSplit(
					keywordGroup.getKeywordGroupAsString(), PLUS_TOKEN);
			if (keywordGroupAsArray != null) {
				keywordGroup.setSortindex(Integer
						.valueOf(keywordGroupAsArray[0]));
				keywordGroup.setName(keywordGroupAsArray[1]);
			}
		}
		return keywordGroup;
	}

	private List<Keyword> prepareKeywords(KeywordGroup preparedKeywordGroup,
			List<KeywordGroup> keywordGroups) {
		// prepare keywords data, uniqueId of each keyword should be serially
		// generated uniqueId of their parent keywordgroup
		List<Keyword> keywords = null;

		if (preparedKeywordGroup != null && keywordGroups != null) {
			long tempUniqueId = preparedKeywordGroup.getUniqueId();
			keywords = new ArrayList<Keyword>();
			for (KeywordGroup keywordGroup : keywordGroups) {
				tempUniqueId++;
				keywords.add(prepareKeyword(keywordGroup.getKeyword(),
						tempUniqueId));
			}
		}

		return keywords;
	}

	private Keyword prepareKeyword(Keyword keyword, long uniqueId) {
		keyword.setUniqueId(uniqueId);
		return keyword;
	}

	private long generateRandomNumbers() {
		Random rand = new Random();
		String[] charset = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9" };
		StringBuffer sb = new StringBuffer();
		for (int n = 0; n < 6; n++) {
			sb = sb.append(charset[rand.nextInt(10)]);
		}
		return Long.valueOf(sb.toString());
	}

	private void appendLibraryIdToKeywordGroupAssetId(
			KeywordGroup keywordGroup, String libraryId) {
		StringBuilder sb = new StringBuilder(libraryId);
		sb.append(TOKEN_TO_COMPLETE_ASSETID);
		sb.append(keywordGroup.getAssetId());
		keywordGroup.setAssetId(sb.toString());
	}
}