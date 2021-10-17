package com.nilsson.vinylrecordsales.advertisement;

import com.nilsson.vinylrecordsales.domain.AdvertisementInformation;
import com.nilsson.vinylrecordsales.domain.ProductId;

public interface AdvertisementFacade {
	ProductId publishProduct(AdvertisementInformation advertisementInformation);
}
