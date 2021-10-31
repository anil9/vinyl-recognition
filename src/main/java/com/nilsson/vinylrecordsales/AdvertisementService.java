package com.nilsson.vinylrecordsales;

import com.nilsson.vinylrecordsales.domain.ProductId;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URL;

public interface AdvertisementService {

    Mono<ProductId> createAdvertisement(String catalogueNumber, String... extraTitleWords);

    Flux<URL> addImages(Mono<ProductId> productId, Flux<URL> imageUrls);
}
