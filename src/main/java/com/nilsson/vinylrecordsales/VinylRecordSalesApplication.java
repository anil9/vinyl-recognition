package com.nilsson.vinylrecordsales;

import com.nilsson.vinylrecordsales.advertisement.AdvertisementFacade;
import com.nilsson.vinylrecordsales.lookup.LookupFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.lang.invoke.MethodHandles;
import java.math.BigDecimal;

import static com.nilsson.vinylrecordsales.domain.AdvertisementInformationFactory.advertisementTemplate;

@SpringBootApplication
public class VinylRecordSalesApplication {
    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    public static void main(String[] args) {

        var context = new AnnotationConfigApplicationContext();
        context.register(SpringConfiguration.class);
        context.refresh();
        SpringApplication.run(VinylRecordSalesApplication.class, args);

        var lookupFacade = context.getBean(LookupFacade.class);
        var catalogueNumber = "MLPH 1622";
        var recordInformation = lookupFacade.getRecordInformationByCatalogueNumber(catalogueNumber).orElseThrow();
        LOG.info("Catalogue number {} returns record information {}", catalogueNumber, recordInformation);

        var advertisementFacade = context.getBean(AdvertisementFacade.class);
        var ad = advertisementTemplate(recordInformation, new BigDecimal("25"));
        var product = advertisementFacade.createProduct(ad);
        LOG.info("Ad created with id={} ad={}", product, ad);
        System.exit(0);

    }

}
