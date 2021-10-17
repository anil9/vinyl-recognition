package com.nilsson.vinylrecordsales.advertisement;

import com.nilsson.vinylrecordsales.domain.*;

import java.math.BigDecimal;

public class AdvertisementInformationTestBuilder {
    public static AdvertisementInformation.AdvertisementInformationBuilder populatedAdvertisementInformationBuilder() {
        return AdvertisementInformation.builder()
                .withAuctionPrice(new BigDecimal("25"))
                .withCondition(Condition.USED)
                .withFolderId("folderId")
                .withProductCategory(ProductCategory.DECADE_80S)
                .withPurchasePrice(BigDecimal.ONE)
                .withQuantityInStock(Quantity.ONE)
                .withRecordInformation(RecordInformationTestBuilder.populatedRecordInformationBuilder().build())
                .withShippingInformation(new ShippingInformation(ShippingCompany.SCHENKER, new BigDecimal("70"), PickupStrategy.ALLOW_PICKUP))
                .withTax(new Tax(25));
    }
}