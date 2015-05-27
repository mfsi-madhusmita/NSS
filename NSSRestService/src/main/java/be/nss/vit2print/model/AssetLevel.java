package be.nss.vit2print.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "full_id", "name", "parents" })
public class AssetLevel {

	@JsonProperty("full_id")
	private String fullId;

	@JsonProperty("name")
	private String name;

	@JsonProperty("parents")
	private List<AssetParent> assetParents;

	@JsonIgnore
	private String parentsAsString;

	public AssetLevel() {
		// Default constructor
	}

	public String getFullId() {
		return fullId;
	}

	public void setFullId(String fullId) {
		this.fullId = fullId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getParentsAsString() {
		return parentsAsString;
	}

	public void setParentsAsString(String parentsAsString) {
		this.parentsAsString = parentsAsString;
	}

	public List<AssetParent> getAssetParents() {
		return assetParents;
	}

	public void setAssetParents(List<AssetParent> assetParents) {
		this.assetParents = assetParents;
	}
}