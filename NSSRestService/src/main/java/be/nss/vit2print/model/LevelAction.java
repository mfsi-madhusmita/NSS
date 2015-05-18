package be.nss.vit2print.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "searchDoubles", "approveAsset", "moveAsset", "editLevel",
		"uploadAsset", "addSubLevel", "copyAsset", "disapproveAsset",
		"deleteLevel" })
public class LevelAction {

	@JsonProperty("searchDoubles")
	private String searchDoubles;

	@JsonProperty("approveAsset")
	private String approveAsset;

	@JsonProperty("moveAsset")
	private String moveAsset;

	@JsonProperty("editLevel")
	private String editLevel;

	@JsonProperty("uploadAsset")
	private String uploadAsset;

	@JsonProperty("addSubLevel")
	private String addSubLevel;

	@JsonProperty("copyAsset")
	private String copyAsset;

	@JsonProperty("disapproveAsset")
	private String disapproveAsset;

	@JsonProperty("deleteLevel")
	private String deleteLevel;

	public LevelAction() {
		// Default Constructor
	}

	public String getSearchDoubles() {
		return searchDoubles;
	}

	public void setSearchDoubles(String searchDoubles) {
		this.searchDoubles = searchDoubles;
	}

	public String getApproveAsset() {
		return approveAsset;
	}

	public void setApproveAsset(String approveAsset) {
		this.approveAsset = approveAsset;
	}

	public String getMoveAsset() {
		return moveAsset;
	}

	public void setMoveAsset(String moveAsset) {
		this.moveAsset = moveAsset;
	}

	public String getEditLevel() {
		return editLevel;
	}

	public void setEditLevel(String editLevel) {
		this.editLevel = editLevel;
	}

	public String getUploadAsset() {
		return uploadAsset;
	}

	public void setUploadAsset(String uploadAsset) {
		this.uploadAsset = uploadAsset;
	}

	public String getAddSubLevel() {
		return addSubLevel;
	}

	public void setAddSubLevel(String addSubLevel) {
		this.addSubLevel = addSubLevel;
	}

	public String getCopyAsset() {
		return copyAsset;
	}

	public void setCopyAsset(String copyAsset) {
		this.copyAsset = copyAsset;
	}

	public String getDisapproveAsset() {
		return disapproveAsset;
	}

	public void setDisapproveAsset(String disapproveAsset) {
		this.disapproveAsset = disapproveAsset;
	}

	public String getDeleteLevel() {
		return deleteLevel;
	}

	public void setDeleteLevel(String deleteLevel) {
		this.deleteLevel = deleteLevel;
	}
}
