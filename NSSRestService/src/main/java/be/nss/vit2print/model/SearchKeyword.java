package be.nss.vit2print.model;

public class SearchKeyword {

	private int id;
	private String name;
	private String group;

	public SearchKeyword() {
		// Default Constructor
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

}
