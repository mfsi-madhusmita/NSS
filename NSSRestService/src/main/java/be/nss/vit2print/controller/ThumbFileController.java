package be.nss.vit2print.controller;

import java.io.IOException;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import be.nss.vit2print.dto.ThumbFile;
import be.nss.vit2print.model.ThumbFileData;
import be.nss.vit2print.service.ThumbFileService;

@RestController
public class ThumbFileController {

	@Autowired
	private ThumbFileService thumbFileService;

	/**
	 * HTTP GET call to get Asset file by assetId sample url is
	 * localhost:8080/NSS/photovit_action/Thumb?assetId=34_85
	 */
	@RequestMapping(value = "/photovit_action/Thumb", method = RequestMethod.GET)
	public ResponseEntity<InputStreamResource> getThumbFile(
			@Valid ThumbFile thumbfile) throws IOException {
		return buildResponseEntity(thumbFileService.getThumbFile(thumbfile));
	}

	/**
	 * Helper method to build ResponseEntity object from ThumbFileData
	 */
	private ResponseEntity<InputStreamResource> buildResponseEntity(
			ThumbFileData thumbFileData) {
		HttpHeaders respHeaders = new HttpHeaders();
		respHeaders.setContentType(MediaType.parseMediaType(thumbFileData
				.getContentType()));
		/*
		 * If file is not image, send it as an attachment
		 */
		if (!thumbFileData.isImage()) {
			respHeaders.setContentDispositionFormData("attachment",
					thumbFileData.getFilename());
		}
		return ResponseEntity.ok().headers(respHeaders)
				.body(new InputStreamResource(thumbFileData.getInputStream()));
	}
}
