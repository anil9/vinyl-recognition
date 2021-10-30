package com.nilsson.vinylrecordsales.web;

import com.nilsson.vinylrecordsales.file.FileService;
import com.nilsson.vinylrecordsales.image.ImageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import reactor.core.publisher.Flux;

import java.io.File;
import java.lang.invoke.MethodHandles;
import java.net.URL;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.requireNonNull;

@Controller
public class ImageUploadController {

	private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
	private final ImageService imageService;
	private final FileService fileService;

	public ImageUploadController(ImageService imageService, FileService fileService) {
		this.imageService = requireNonNull(imageService, "imageService");
		this.fileService = requireNonNull(fileService, "fileService");
	}

	@GetMapping("/image")
	public String imageForm(Model model) {
		model.addAttribute("imageFolderLocation", new ImageFolderLocation());
		if (!imageService.haveStoredURLs()) {
			LOG.info("Haven't uploaded any images yet");
		}
		return "image-upload";
	}

	@PostMapping("/image")
	public String imageSubmit(@ModelAttribute ImageFolderLocation imageFolderLocation, Model model) {
		model.addAttribute("imageFolderLocation", new ImageFolderLocation());
		LOG.info("Received image folder location {}", imageFolderLocation.getLocation());
		File imageDirectory = new File(imageFolderLocation.getLocation());
		List<File> images = fileService.getImageFilesInDirectoryOrderedByName(imageDirectory).stream().limit(20).collect(Collectors.toList());
		Flux<URL> urls = imageService.uploadImages(images);
		imageService.storeURLs(urls);
		return "redirect:/image";
	}

}
