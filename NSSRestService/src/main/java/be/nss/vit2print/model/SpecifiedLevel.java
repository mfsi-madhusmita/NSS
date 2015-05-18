package be.nss.vit2print.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "id", "namePath", "hasCollections", "hasChildren",
		"scope", "searchKeywords", "name", "idPath", "assetCount", "type",
		"actions" })
public class SpecifiedLevel {

	@JsonProperty("id")
	private int id;

	@JsonProperty("namePath")
	private String namePath;

	@JsonProperty("hasCollections")
	private String hasCollections;

	@JsonProperty("hasChildren")
	private String hasChildren;

	@JsonProperty("scope")
	private String scope;

	@JsonProperty("name")
	private String name;

	@JsonProperty("idPath")
	private String idPath;

	@JsonProperty("assetCount")
	private int assetCount;

	@JsonProperty("type")
	private String type;

	@JsonProperty("actions")
	private LevelAction levelAction;

	@JsonProperty("searchKeywords")
	private List<SearchKeyword> searchKeywords;

	public SpecifiedLevel() {
		// Default constructor
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNamePath() {
		return namePath;
	}

	public void setNamePath(String namePath) {
		this.namePath = namePath;
	}

	public String getHasCollections() {
		return hasCollections;
	}

	public void setHasCollections(String hasCollections) {
		this.hasCollections = hasCollections;
	}

	public String getHasChildren() {
		return hasChildren;
	}

	public void setHasChildren(String hasChildren) {
		this.hasChildren = hasChildren;
	}

	public String getScope() {
		return scope;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIdPath() {
		return idPath;
	}

	public void setIdPath(String idPath) {
		this.idPath = idPath;
	}

	public int getAssetCount() {
		return assetCount;
	}

	public void setAssetCount(int assetCount) {
		this.assetCount = assetCount;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public LevelAction getLevelAction() {
		return levelAction;
	}

	public void setLevelAction(LevelAction levelAction) {
		this.levelAction = levelAction;
	}

	public List<SearchKeyword> getSearchKeywords() {
		return searchKeywords;
	}

	public void setSearchKeywords(List<SearchKeyword> searchKeywords) {
		this.searchKeywords = searchKeywords;
	}
}
