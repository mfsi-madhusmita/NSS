package be.nss.vit2print.utility;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javax.activation.MimetypesFileTypeMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import be.nss.vit2print.exception.APIException;
import be.nss.vit2print.model.ThumbFileData;
import be.nss.vit2print.model.ThumbFileDetail;

/**
 * Helper utility class for file handling related operations
 */
@Component
public class ThumbFileHandling {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	private static final String[] imageTypes = { "PNG", "JPEG", "GIF" };
	private static final String IMAGE_TYPE_PREFIX = "IMAGE/";

	/**
	 * Method to fetch input stream from a file
	 */
	public void addInputStreamFromFile(String filePath,
			ThumbFileData thumbFileData) {

		try {
			thumbFileData.setInputStream(new BufferedInputStream(
					new FileInputStream(filePath)));
		} catch (FileNotFoundException e) {
			StringBuilder message = new StringBuilder();
			message.append("FileNotFoundException, ").append(e.getMessage());
			logger.error(message.toString());
			throw new APIException(message.toString(), HttpStatus.NOT_FOUND);
		}
	}

	/**
	 * 
	 Method to decide content type of file
	 */
	public void addThumbContentType(ThumbFileDetail thumbFileDetail,
			ThumbFileData thumbFileData) {
		StringBuilder sb = null;

		for (String imageType : imageTypes) {
			if (imageType.equals(thumbFileDetail.getFileType())) {
				sb = new StringBuilder();
				sb.append(IMAGE_TYPE_PREFIX).append(imageType);
				break;
			}
		}

		if (sb != null) {
			thumbFileData.setContentType(sb.toString());
			thumbFileData.setImage(true);
		} else {
			thumbFileData.setContentType(getMimeTypeOfThumb(thumbFileDetail
					.getFilePath()));
			thumbFileData.setImage(false);
			thumbFileData.setFilename(thumbFileDetail.getFileName());
		}
	}

	private String getMimeTypeOfThumb(String filePath) {
		MimetypesFileTypeMap fileTypeMap = new MimetypesFileTypeMap();
		return fileTypeMap.getContentType(filePath);
	}
}