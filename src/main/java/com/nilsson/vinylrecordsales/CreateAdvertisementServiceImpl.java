package com.nilsson.vinylrecordsales;

import com.nilsson.vinylrecordsales.advertisement.AdvertisementFacade;
import com.nilsson.vinylrecordsales.domain.AdvertisementInformation;
import com.nilsson.vinylrecordsales.domain.AdvertisementInformationFactory;
import com.nilsson.vinylrecordsales.domain.ProductId;
import com.nilsson.vinylrecordsales.domain.RecordInformation;
import com.nilsson.vinylrecordsales.lookup.LookupService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;

import static java.util.Objects.requireNonNull;

public class CreateAdvertisementServiceImpl implements CreateAdvertisementService {

    private final LookupService lookupService;
    private final AdvertisementFacade advertisementFacade;
    private final AdvertisementInformationFactory adFactory;
    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    public CreateAdvertisementServiceImpl(LookupService lookupService, AdvertisementFacade advertisementFacade, AdvertisementInformationFactory adFactory) {
        this.lookupService = requireNonNull(lookupService, "lookupService");
        this.advertisementFacade = requireNonNull(advertisementFacade, "advertisementFacade");
        this.adFactory = requireNonNull(adFactory, "adFactory");
    }

    @Override
    public ProductId createAdvertisement(String catalogueNumber, String... extraTitleWords) {
        RecordInformation recordInformation = lookupService.getRecordInformationByCatalogueNumber(catalogueNumber, extraTitleWords).orElseThrow();
        LOG.info("Fetched record information, title={}", recordInformation.getTitle());
        LOG.debug("{}", recordInformation);

        AdvertisementInformation ad = adFactory.fromTemplate(recordInformation);
        LOG.info("Generated ad from template. Will auction for price={}", ad.getAuctionPrice());
        LOG.debug("{}", ad);

        ProductId productId = advertisementFacade.createProduct(ad);
        LOG.info("Published ad, got productId={} from response", productId);
        return productId;
    }
}
