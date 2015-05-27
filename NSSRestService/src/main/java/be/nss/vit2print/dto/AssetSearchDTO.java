package be.nss.vit2print.dto;

import static be.nss.vit2print.exception.ExceptionMessages.INVALID_ASSET_PER_PAGE;
import static be.nss.vit2print.exception.ExceptionMessages.INVALID_ASSET_SEARCH_SORT_BY;
import static be.nss.vit2print.exception.ExceptionMessages.INVALID_LIBRARY_ID;
import static be.nss.vit2print.exception.ExceptionMessages.INVALID_PAGE;
import static be.nss.vit2print.exception.ExceptionMessages.INVALID_RECURSE;
import static be.nss.vit2print.exception.ExceptionMessages.INVALID_SEARCH_LEVELS;
import static be.nss.vit2print.exception.ExceptionMessages.INVALID_SEARCH_VALUE;
import static be.nss.vit2print.exception.ExceptionMessages.INVALID_SORT_ORDER;
import static be.nss.vit2print.exception.ExceptionMessages.INVALID_USERNAME;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnore;

import be.nss.vit2print.validation.SortByValidation;
import be.nss.vit2print.validation.SortOrderValidation;

public class AssetSearchDTO {

	@NotBlank(message = INVALID_USERNAME)
	private String username;

	@Min(value = 1, message = INVALID_LIBRARY_ID)
	private int libraryId;

	@NotEmpty(message = INVALID_SEARCH_LEVELS)
	@Valid
	private String[] searchlevels;

	@Min(value = 1, message = INVALID_PAGE)
	private int page;

	@Min(value = 1, message = INVALID_ASSET_PER_PAGE)
	private int assetPerPage;

	@NotBlank(message = INVALID_ASSET_SEARCH_SORT_BY)
	@SortByValidation(message = INVALID_ASSET_SEARCH_SORT_BY)
	private String sortBy;

	@NotBlank(message = INVALID_SORT_ORDER)
	@SortOrderValidation(message = INVALID_SORT_ORDER)
	private String sortOrder;

	@NotBlank(message = INVALID_RECURSE)
	@Pattern(regexp = "Y|N", message = INVALID_RECURSE)
	private String recurse;

	@NotBlank(message = INVALID_SEARCH_VALUE)
	private String searchValue;

	@JsonIgnore
	private String searchLevel;

	public AssetSearchDTO() {
		// Default constructor
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public int getLibraryId() {
		return libraryId;
	}

	public void setLibraryId(int libraryId) {
		this.libraryId = libraryId;
	}

	public String[] getSearchlevels() {
		return searchlevels;
	}

	public void setSearchlevels(String[] searchlevels) {
		this.searchlevels = searchlevels;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getAssetPerPage() {
		return assetPerPage;
	}

	public void setAssetPerPage(int assetPerPage) {
		this.assetPerPage = assetPerPage;
	}

	public String getSortBy() {
		return sortBy;
	}

	public void setSortBy(String sortBy) {
		this.sortBy = sortBy;
	}

	public String getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(String sortOrder) {
		this.sortOrder = sortOrder;
	}

	public String getRecurse() {
		return recurse;
	}

	public void setRecurse(String recurse) {
		this.recurse = recurse;
	}

	public String getSearchValue() {
		return searchValue;
	}

	public void setSearchValue(String searchValue) {
		this.searchValue = searchValue;
	}

	public String getSearchLevel() {
		return searchLevel;
	}

	public void setSearchLevel(String searchLevel) {
		this.searchLevel = searchLevel;
	}
}
