package be.nss.vit2print.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import be.nss.vit2print.dto.ThumbFile;
import be.nss.vit2print.model.ThumbFileData;
import be.nss.vit2print.model.ThumbFileDetail;
import be.nss.vit2print.repository.ThumbFileRepository;
import be.nss.vit2print.utility.StringParser;
import be.nss.vit2print.utility.ThumbFileHandling;

@Service
public class ThumbFileService {

	private static final String ASSET_ID_TOKEN = "_";

	@Autowired
	private ThumbFileRepository thumbFileRepository;

	@Autowired
	private ThumbFileHandling thumbFileHandling;

	@Autowired
	private StringParser parser;

	/**
	 * Service method for /photovit_action/Thumb api
	 */
	public ThumbFileData getThumbFile(ThumbFile thumbfile) {
		String[] assetIdAsArray = parser.doSplit(thumbfile.getAssetId(),
				ASSET_ID_TOKEN);
		ThumbFileDetail thumbFileDetail = thumbFileRepository
				.findAssetFileData(assetIdAsArray[0], assetIdAsArray[1]);
		ThumbFileData thumbFileData = null;

		if (thumbFileDetail != null) {
			thumbFileData = prepareThumbFileData(thumbFileDetail);
		}

		return thumbFileData;
	}

	/**
	 * 
	 Helper method to prepare ThumbFileData Object
	 */
	private ThumbFileData prepareThumbFileData(ThumbFileDetail thumbFileDetail) {
		ThumbFileData thumbFileData = new ThumbFileData();
		thumbFileHandling.addInputStreamFromFile(thumbFileDetail.getFilePath(),
				thumbFileData);
		thumbFileHandling.addThumbContentType(thumbFileDetail, thumbFileData);
		return thumbFileData;
	}
}
