package be.nss.vit2print.model;

public class Level {

	private String id;
	private String namePath;
	private int parentId;
	private String hasChildren;
	private String scope;
	private String name;
	private String idPath;
	private int assestCount;
	private String type;
	private LevelAction actions;

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

	public LevelAction getActions() {
		return actions;
	}

	public void setActions(LevelAction actions) {
		this.actions = actions;
	}
}
