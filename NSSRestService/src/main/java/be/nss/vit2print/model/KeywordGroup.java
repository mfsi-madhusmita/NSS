package be.nss.vit2print.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "uniqueid", "sortindex", "name", "keywords" })
public class KeywordGroup {

	@JsonProperty("uniqueid")
	private long uniqueId;

	@JsonProperty("sortindex")
	private int sortindex;

	@JsonProperty("name")
	private String name;

	@JsonProperty("keywords")
	private List<Keyword> keywords;

	@JsonIgnore
	private String keywordGroupAsString;

	@JsonIgnore
	private Keyword keyword;

	public KeywordGroup() {
		// Default constructor
	}

	public KeywordGroup(long uniqueId, int sortindex, String name) {
		super();
		this.uniqueId = uniqueId;
		this.sortindex = sortindex;
		this.name = name;
	}

	public long getUniqueId() {
		return uniqueId;
	}

	public void setUniqueId(long uniqueId) {
		this.uniqueId = uniqueId;
	}

	public int getSort() {
		return sortindex;
	}

	public void setSort(int sortindex) {
		this.sortindex = sortindex;
	}

	public List<Keyword> getKeywords() {
		return keywords;
	}

	public void setKeywords(List<Keyword> keywords) {
		this.keywords = keywords;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getKeywordGroupAsString() {
		return keywordGroupAsString;
	}

	public void setKeywordGroupAsString(String keywordGroupAsString) {
		this.keywordGroupAsString = keywordGroupAsString;
	}

	public int getSortindex() {
		return sortindex;
	}

	public void setSortindex(int sortindex) {
		this.sortindex = sortindex;
	}

	public Keyword getKeyword() {
		return keyword;
	}

	public void setKeyword(Keyword keyword) {
		this.keyword = keyword;
	}
}
