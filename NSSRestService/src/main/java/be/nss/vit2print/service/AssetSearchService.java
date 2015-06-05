package be.nss.vit2print.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
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

	@Autowired
	private HttpServletRequest request;

	/**
	 * Service method for search assets api
	 */
	public AssetData searchAssetData(AssetSearchDTO assetSearch) {
		utility.transformAssetSearchDTO(assetSearch);
		String username = ((User) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal()).getUsername();
		AssetData assetData = null;
		List<Asset> assets = asssetSearchRepository.findAssets(assetSearch,
				username);

		if (assets != null) {
			for (Asset asset : assets) {
				if (asset != null) {
					utility.transformAssetLevel(asset.getAssetLevel());
				}
			}
			List<Asset> transformedAssets = utility
					.transformAssetsWithKeywordGroup(asssetSearchRepository
							.findKeywordGroups(username,
									assetSearch.getLibraryId(),
									utility.transformAssetIdsToString(assets)),
							assets, String.valueOf(assetSearch.getLibraryId()));

			utility.addThumbStringUrlToEachAsset(transformedAssets, request
					.getRequestURL().toString(), request.getPathInfo());

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