package be.nss.vit2print.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "full_id", "name" })
public class AssetParent {

	@JsonProperty("full_id")
	private String fullId;

	@JsonProperty("name")
	private String name;

	public AssetParent() {
		// Default constructor
	}

	public AssetParent(String fullId, String name) {
		super();
		this.fullId = fullId;
		this.name = name;
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
}