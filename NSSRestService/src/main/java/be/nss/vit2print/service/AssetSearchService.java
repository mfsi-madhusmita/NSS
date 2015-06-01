package be.nss.vit2print.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import be.nss.vit2print.dto.AssetSearchDTO;
import be.nss.vit2print.model.Asset;
import be.nss.vit2print.model.AssetData;
import be.nss.vit2print.model.BasketAction;
import be.nss.vit2print.repository.AssetSearchRepository;
import be.nss.vit2print.utility.AssetSearchTransformUtility;

@Service
public class AssetSearchService {

	@Autowired
	private AssetSearchRepository asssetSearchRepository;

	@Autowired
	private AssetSearchTransformUtility utility;

	/**
	 * Service method for search assets api
	 */
	public AssetData searchAssetData(AssetSearchDTO assetSearch) {
		utility.transformAssetSearchDTO(assetSearch);
		AssetData assetData = null;
		List<Asset> assets = asssetSearchRepository.findAssets(assetSearch);

		if (assets != null) {
			for (Asset asset : assets) {
				if (asset != null) {
					utility.transformAssetLevel(asset.getAssetLevel());
				}
			}
			List<Asset> transformedAssets = utility
					.transformAssetsWithKeywordGroup(asssetSearchRepository
							.findKeywordGroups(assetSearch.getUsername(),
									assetSearch.getLibraryId(),
									utility.transformAssetIdsToString(assets)),
							assets, String.valueOf(assetSearch.getLibraryId()));

			assetData = prepareAssetData(transformedAssets,
					assetSearch.getPage());
		}

		return assetData;
	}

	/**
	 * Helper method to prepare data for AssetData(root) object
	 */
	private AssetData prepareAssetData(List<Asset> assets, int pages) {
		int basketAssetCount = 0;
		for (Asset asset : assets) {
			basketAssetCount += asset.getBasketAssetCount();
		}

		BasketAction basketAction = new BasketAction("Y");

		AssetData assetData = new AssetData();
		assetData.setAssetCount(assets.size());
		assetData.setPages(pages);
		assetData.setBasketAssetCount(basketAssetCount);
		assetData.setBasketAction(basketAction);
		assetData.setAssets(assets);

		return assetData;
	}
}