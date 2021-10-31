package com.nilsson.vinylrecordsales.advertisement;

import com.nilsson.vinylrecordsales.domain.AdvertisementInformation;
import com.nilsson.vinylrecordsales.domain.ProductId;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URL;

public interface AdvertisementFacade {
	ProductId createProduct(AdvertisementInformation advertisementInformation);

	Mono<ProductId> monoCreateProduct(AdvertisementInformation advertisementInformation);

	Flux<URL> addImagesToProduct(ProductId productId, Flux<URL> imageUrls);
}
