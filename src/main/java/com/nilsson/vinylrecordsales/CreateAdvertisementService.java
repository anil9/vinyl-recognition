package com.nilsson.vinylrecordsales;

import com.nilsson.vinylrecordsales.domain.ProductId;

public interface CreateAdvertisementService {
    ProductId createAdvertisement(String catalogueNumber, String... extraTitleWords);
}
