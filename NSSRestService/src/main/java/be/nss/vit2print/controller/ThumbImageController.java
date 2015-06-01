package be.nss.vit2print.controller;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.activation.MimetypesFileTypeMap;

import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ThumbImageController {

	// @RequestMapping(value = "/photovit_action/Thumb", method =
	// RequestMethod.GET, produces = MediaType.IMAGE_PNG_VALUE)
	// public byte[] getImage() throws IOException {
	// InputStream in = new BufferedInputStream(new FileInputStream(
	// "/home/nishant/Desktop/sample.png"));
	// return IOUtils.toByteArray(in);
	// }

	@RequestMapping(value = "/photovit_action/Thumb", method = RequestMethod.GET)
	public ResponseEntity<InputStreamResource> getImage() throws IOException {
		InputStream in = new BufferedInputStream(new FileInputStream(
				"/home/nishant/Desktop/sample.png"));

		// ResponseEntity.ok().contentLength(service.getContentLength).contentType(service.getContentType)
		// .body(new InputStreamResource(in))

		MimetypesFileTypeMap fileTypeMap = new MimetypesFileTypeMap();
		System.out.println(fileTypeMap
				.getContentType("/home/nishant/Desktop/sample.png"));
		return ResponseEntity.ok().contentType(MediaType.parseMediaType("image/png"))
				.body(new InputStreamResource(in));
	}
}
