package be.nss.vit2print.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "asset_id", "level", "name", "approval", "thumbstring",
		"importtime", "filetype", "browseable", "size", "keywordgroups",
		"actions" })
public class Asset {

	@JsonProperty("asset_id")
	private String assetId;

	@JsonProperty("name")
	private String name;

	@JsonProperty("approval")
	private String approval;

	@JsonProperty("thumbstring")
	private String thumbString;

	@JsonProperty("importtime")
	private String importTime;

	@JsonProperty("filetype")
	private String fileType;

	@JsonProperty("browseable")
	private String browseAble;

	@JsonProperty("size")
	private long size;

	@JsonProperty("actions")
	private AssetAction assetAction;

	@JsonProperty("level")
	private AssetLevel assetLevel;

	@JsonProperty("keywordgroup")
	private KeywordGroup keywordGroup;

	@JsonIgnore
	private int basketAssetCount;

	public Asset() {
		// TODO Auto-generated constructor stub
	}

	public String getAssetId() {
		return assetId;
	}

	public void setAssetId(String assetId) {
		this.assetId = assetId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getApproval() {
		return approval;
	}

	public void setApproval(String approval) {
		this.approval = approval;
	}

	public String getThumbString() {
		return thumbString;
	}

	public void setThumbString(String thumbString) {
		this.thumbString = thumbString;
	}

	public String getImportTime() {
		return importTime;
	}

	public void setImportTime(String importTime) {
		this.importTime = importTime;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public String getBrowseAble() {
		return browseAble;
	}

	public void setBrowseAble(String browseAble) {
		this.browseAble = browseAble;
	}

	public long getSize() {
		return size;
	}

	public void setSize(long size) {
		this.size = size;
	}

	public AssetAction getAssetAction() {
		return assetAction;
	}

	public void setAssetAction(AssetAction assetAction) {
		this.assetAction = assetAction;
	}

	public AssetLevel getAssetLevel() {
		return assetLevel;
	}

	public void setAssetLevel(AssetLevel assetLevel) {
		this.assetLevel = assetLevel;
	}

	public KeywordGroup getKeywordGroup() {
		return keywordGroup;
	}

	public void setKeywordGroups(KeywordGroup keywordGroup) {
		this.keywordGroup = keywordGroup;
	}

	public int getBasketAssetCount() {
		return basketAssetCount;
	}

	public void setBasketAssetCount(int basketAssetCount) {
		this.basketAssetCount = basketAssetCount;
	}
}