package be.nss.vit2print.model;

import java.util.List;

public class SpecifiedLevel {

	private int id;
	private String namePath;
	private String hasCollections;
	private String hasChildren;
	private String scope;
	private String name;
	private String idPath;
	private int assetCount;
	private String type;
	private LevelAction actions;
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

	public LevelAction getActions() {
		return actions;
	}

	public void setActions(LevelAction actions) {
		this.actions = actions;
	}

	public List<SearchKeyword> getSearchKeywords() {
		return searchKeywords;
	}

	public void setSearchKeywords(List<SearchKeyword> searchKeywords) {
		this.searchKeywords = searchKeywords;
	}
}
