package com.nilsson.vinylrecordsales.web;

import com.nilsson.vinylrecordsales.AdvertisementService;
import com.nilsson.vinylrecordsales.domain.ProductId;
import com.nilsson.vinylrecordsales.image.ImageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.lang.invoke.MethodHandles;
import java.net.URL;

import static java.util.Objects.requireNonNull;

@Controller
public class AdvertisementController {

	private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
	private static final int NUMBER_OF_IMAGES_PER_AD = 5;
	private static final int MAX_CONCURRENT = 10;
	private final AdvertisementService advertisementService;
	private final ImageService imageService;

	public AdvertisementController(AdvertisementService advertisementService, ImageService imageService) {
		this.advertisementService = requireNonNull(advertisementService, "advertisementService");
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
		Flux<URL> imageUrls = Flux.range(0, NUMBER_OF_IMAGES_PER_AD)
				.flatMapSequential(i -> imageService.pollUrl(), MAX_CONCURRENT);

		Mono<ProductId> productId = advertisementService.monoCreateAdvertisement(recordFinder.getCatalogueId(),
				recordFinder.getExtraTitleWords()).log();

		advertisementService.addImages(productId, imageUrls)
				.subscribe(url -> LOG.info("Stored url on product, url={}", url));
		return "redirect:/record";
	}

}
