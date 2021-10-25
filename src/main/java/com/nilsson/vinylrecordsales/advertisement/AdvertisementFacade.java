package com.nilsson.vinylrecordsales.advertisement;

import com.nilsson.vinylrecordsales.domain.AdvertisementInformation;
import com.nilsson.vinylrecordsales.domain.ProductId;
import reactor.core.publisher.Mono;

public interface AdvertisementFacade {
	ProductId createProduct(AdvertisementInformation advertisementInformation);

	Mono<ProductId> monoCreateProduct(AdvertisementInformation advertisementInformation);
}
