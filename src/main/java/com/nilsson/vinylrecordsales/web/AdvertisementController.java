package com.nilsson.vinylrecordsales.web;

import com.nilsson.vinylrecordsales.lookup.LookupService;
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
    private final LookupService lookupService;

    public AdvertisementController(LookupService lookupService) {
        this.lookupService = requireNonNull(lookupService, "lookupService");
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
            lookupService.getMonoRecordInformationByCatalogueNumber(recordFinder.getCatalogueId(), recordFinder.getExtraTitleWords())
                    .subscribe(recordInformation -> LOG.info("{}", recordInformation.orElseThrow()));
        }
        return "redirect:/record";
    }

}
