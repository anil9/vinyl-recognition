package com.nilsson.vinylrecordsales.web;

import static java.util.Objects.requireNonNull;

import java.lang.invoke.MethodHandles;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import com.nilsson.vinylrecordsales.CreateAdvertisementService;

@Controller
public class AdvertisementController {

	private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
	private final CreateAdvertisementService createAdvertisementService;

	public AdvertisementController(CreateAdvertisementService createAdvertisementService) {
		this.createAdvertisementService = requireNonNull(createAdvertisementService, "createAdvertisementService");
	}

	@GetMapping("/record")
	public String recordForm(Model model) {
		model.addAttribute("recordFinder", new RecordFinder());
		return "record-finder";
	}

	@PostMapping("/record")
	public String recordSubmit(@ModelAttribute RecordFinder recordFinder, Model model) {
		model.addAttribute("recordFinder", new RecordFinder());
		LOG.info("POSTing {}", recordFinder);
		if (!recordFinder.getCatalogueId().isBlank()) {
			createAdvertisementService.monoCreateAdvertisement(recordFinder.getCatalogueId(),
							recordFinder.getExtraTitleWords())
					.subscribe(productId -> LOG.info("Successfullt created ad, productId={}", productId));
		}
		return "redirect:/record";
	}

}
