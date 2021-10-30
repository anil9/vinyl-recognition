package com.nilsson.vinylrecordsales;

import com.nilsson.vinylrecordsales.advertisement.AdvertisementFacade;
import com.nilsson.vinylrecordsales.domain.AdvertisementInformationFactory;
import com.nilsson.vinylrecordsales.domain.ProductId;
import com.nilsson.vinylrecordsales.lookup.LookupService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URL;

import static java.util.Objects.requireNonNull;

public class AdvertisementServiceImpl implements AdvertisementService {

    private final LookupService lookupService;
    private final AdvertisementFacade advertisementFacade;
    private final AdvertisementInformationFactory adFactory;

    public AdvertisementServiceImpl(LookupService lookupService, AdvertisementFacade advertisementFacade, AdvertisementInformationFactory adFactory) {
        this.lookupService = requireNonNull(lookupService, "lookupService");
        this.advertisementFacade = requireNonNull(advertisementFacade, "advertisementFacade");
        this.adFactory = requireNonNull(adFactory, "adFactory");
    }

    @Override
    public ProductId createAdvertisement(String catalogueNumber, String... extraTitleWords) {
        return monoCreateAdvertisement(catalogueNumber, extraTitleWords).block();
    }

    @Override
    public Mono<ProductId> monoCreateAdvertisement(String catalogueNumber, String... extraTitleWords) {
        return lookupService.getMonoRecordInformationByCatalogueNumber(catalogueNumber, extraTitleWords)
                .flatMap(Mono::justOrEmpty)
                .log()
                .map(adFactory::fromTemplate)
                .flatMap(advertisementFacade::monoCreateProduct)
                .log();
    }

    @Override
    public Flux<URL> addImages(Mono<ProductId> productId, Flux<URL> imageUrls) {
        return advertisementFacade.addImagesToProduct(productId, imageUrls);
    }
}
