package be.nss.vit2print.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import be.nss.vit2print.dto.AssetSearchDTO;
import be.nss.vit2print.model.AssetData;
import be.nss.vit2print.service.AssetSearchService;

@RestController
public class AssetSearchController {

	@Autowired
	private AssetSearchService assetSearchService;

	/**
	 * HTTP POST call to search asset data sample url is
	 * http://localhost:8080/NSS/search/assets
	 */
	@RequestMapping(value = "/search/assets", method = RequestMethod.POST)
	public AssetData searchAssetData(
			@RequestBody @Valid AssetSearchDTO assetSearch) {
		return assetSearchService.searchAssetData(assetSearch);
	}
}