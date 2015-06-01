package be.nss.vit2print.model;

import java.io.InputStream;

public class ThumbFileData {

	private InputStream inputStream;
	private String contentType;
	private String filename;
	private boolean isImage;

	public ThumbFileData() {
		// Default constructor
	}

	public InputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public boolean isImage() {
		return isImage;
	}

	public void setImage(boolean isImage) {
		this.isImage = isImage;
	}
}
