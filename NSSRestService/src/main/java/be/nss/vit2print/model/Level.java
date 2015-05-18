package be.nss.vit2print.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "id", "namePath", "parentId", "hasChildren", "scope",
		"name", "idPath", "assetCount", "type", "actions" })
public class Level {

	@JsonProperty("id")
	private String id;

	@JsonProperty("namePath")
	private String namePath;

	@JsonProperty("parentId")
	private int parentId;

	@JsonProperty("hasChildren")
	private String hasChildren;

	@JsonProperty("scope")
	private String scope;

	@JsonProperty("name")
	private String name;

	@JsonProperty("idPath")
	private String idPath;

	@JsonProperty("assetCount")
	private int assestCount;

	@JsonProperty("type")
	private String type;

	@JsonProperty("actions")
	private LevelAction levelAction;

	public Level() {
		// Default Constructor
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNamePath() {
		return namePath;
	}

	public void setNamePath(String namePath) {
		this.namePath = namePath;
	}

	public int getParentId() {
		return parentId;
	}

	public void setParentId(int parentId) {
		this.parentId = parentId;
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

	public int getAssestCount() {
		return assestCount;
	}

	public void setAssestCount(int assestCount) {
		this.assestCount = assestCount;
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
}
