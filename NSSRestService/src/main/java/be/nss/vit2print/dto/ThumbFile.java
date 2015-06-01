package be.nss.vit2print.dto;

import static be.nss.vit2print.exception.ExceptionMessages.INVALID_ASSET_ID;
import static be.nss.vit2print.validation.ValidationPatterns.ASSET_ID_OR_LIBRARY_ID_PATTERN;

import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotEmpty;

public class ThumbFile {

	@NotEmpty(message = INVALID_ASSET_ID)
	@Pattern(regexp = ASSET_ID_OR_LIBRARY_ID_PATTERN, message = INVALID_ASSET_ID)
	private String assetId;

	public ThumbFile() {
		// Default constructor
	}

	public String getAssetId() {
		return assetId;
	}

	public void setAssetId(String assetId) {
		this.assetId = assetId;
	}
}
