package com.nilsson.vinylrecordsales.web;

import com.nilsson.vinylrecordsales.CreateAdvertisementService;
import com.nilsson.vinylrecordsales.image.ImageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.lang.invoke.MethodHandles;

import static java.util.Objects.requireNonNull;

@Controller
public class AdvertisementController {

	private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
	private final CreateAdvertisementService createAdvertisementService;
	private final ImageService imageService;

	public AdvertisementController(CreateAdvertisementService createAdvertisementService, ImageService imageService) {
		this.createAdvertisementService = requireNonNull(createAdvertisementService, "createAdvertisementService");
		this.imageService = requireNonNull(imageService, "imageService");
	}

	@GetMapping("/record")
	public String recordForm(Model model) {
		model.addAttribute("recordFinder", new RecordFinder());
		if (!imageService.haveStoredURLs()) {
			LOG.warn("Haven't uploaded any images yet");
		}
		return "record-finder";
	}

	@PostMapping("/record")
	public String recordSubmit(@ModelAttribute RecordFinder recordFinder, Model model) {
		model.addAttribute("recordFinder", new RecordFinder());
		LOG.info("POSTing {}", recordFinder);
		if (recordFinder.getCatalogueId().isBlank()) {
			return "redirect:/record";

		}
		if (!imageService.haveStoredURLs()) {
			throw new IllegalStateException("Cannot create ad if no image url has been stored");
		}
		createAdvertisementService.monoCreateAdvertisement(recordFinder.getCatalogueId(),
						recordFinder.getExtraTitleWords())
				.subscribe(productId -> LOG.info("Successfullt created ad, productId={}", productId));
		return "redirect:/record";
	}

}
