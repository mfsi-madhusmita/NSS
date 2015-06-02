package be.nss.vit2print.model;

public class ThumbFileDetail {

	private String fileBinaryName;
	private String fileName;
	private String filePath;
	private String fileType;

	public ThumbFileDetail() {
		// Default Constructor
	}

	public String getFileBinaryName() {
		return fileBinaryName;
	}

	public void setFileBinaryName(String fileBinaryName) {
		this.fileBinaryName = fileBinaryName;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}
}
