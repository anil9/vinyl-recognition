package com.nilsson.vinylrecordsales;

import com.nilsson.vinylrecordsales.domain.ProductId;
import reactor.core.publisher.Mono;

public interface CreateAdvertisementService {
    ProductId createAdvertisement(String catalogueNumber, String... extraTitleWords);

    Mono<ProductId> monoCreateAdvertisement(String catalogueNumber, String... extraTitleWords);
}
