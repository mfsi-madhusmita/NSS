package be.nss.vit2print.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import be.nss.vit2print.dto.AssetSearchDTO;
import be.nss.vit2print.model.Asset;
import be.nss.vit2print.model.AssetData;
import be.nss.vit2print.model.AssetLevel;
import be.nss.vit2print.model.KeywordGroup;
import be.nss.vit2print.repository.AssetSearchRepository;
import be.nss.vit2print.utility.AssetSearchTransformUtility;

@Service
public class AssetSearchService {

	private static final String TOKEN_FOR_TOKENIZATION = ",";

	@Autowired
	private AssetSearchRepository asssetSearchRepository;

	@Autowired
	private AssetSearchTransformUtility utility;

	public AssetData searchAssetData(AssetSearchDTO assetSearch) {
		assetSearch.setSearchLevel(StringUtils.arrayToDelimitedString(
				assetSearch.getSearchlevels(), TOKEN_FOR_TOKENIZATION));

		AssetData assetData = asssetSearchRepository.findAssetData(assetSearch);

		if (assetData != null) {
			List<Asset> assets = asssetSearchRepository.findAssets(assetSearch);
			List<String> assetIds = new ArrayList<String>();
			String assetIdsAsAString = null;

			if (assets != null) {
				for (Asset asset : assets) {
					if (asset != null) {
						assetIds.add(asset.getAssetId());

						AssetLevel assetLevel = asset.getAssetLevel();
						utility.transformAssetLevel(assetLevel);
					}
				}
			}

			if (assetIds.size() > 0) {
				assetIdsAsAString = StringUtils.collectionToDelimitedString(
						assetIds, TOKEN_FOR_TOKENIZATION);
			}

			List<KeywordGroup> keywordGroups = utility
					.transformKeywordGroups(asssetSearchRepository
							.findKeywordGroups(assetSearch.getLibraryId(),
									assetIdsAsAString));

			assetData.setAssets(assets);
		}

		return assetData;
	}
}
