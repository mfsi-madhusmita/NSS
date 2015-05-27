package be.nss.vit2print.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "assets", "pages", "assetcount", "basketactions",
		"basketassetcount" })
public class AssetData {

	@JsonProperty("assets")
	private List<Asset> assets;

	@JsonProperty("pages")
	private int pages;

	@JsonProperty("assetcount")
	private int assetCount;

	@JsonProperty("basketassetcount")
	private int basketAssetCount;

	@JsonProperty("basketactions")
	private BasketAction basketAction;

	public AssetData() {
		// Default Constructor
	}

	public int getPages() {
		return pages;
	}

	public void setPages(int pages) {
		this.pages = pages;
	}

	public int getAssetCount() {
		return assetCount;
	}

	public void setAssetCount(int assetCount) {
		this.assetCount = assetCount;
	}

	public int getBasketAssetCount() {
		return basketAssetCount;
	}

	public void setBasketAssetCount(int basketAssetCount) {
		this.basketAssetCount = basketAssetCount;
	}

	public BasketAction getBasketAction() {
		return basketAction;
	}

	public void setBasketAction(BasketAction basketAction) {
		this.basketAction = basketAction;
	}

	public List<Asset> getAssets() {
		return assets;
	}

	public void setAssets(List<Asset> assets) {
		this.assets = assets;
	}

}