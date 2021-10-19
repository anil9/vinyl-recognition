package com.nilsson.vinylrecordsales.advertisement;

import com.nilsson.vinylrecordsales.domain.*;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.Locale;

public class AdvertisementInformationTestBuilder {
    public static AdvertisementInformation.AdvertisementInformationBuilder populatedAdvertisementInformationBuilder() {
        return AdvertisementInformation.builder()
                .withAuctionPrice(new BigDecimal("25"))
                .withCondition(Condition.USED)
                .withFolderId(1337)
                .withProductCategory(ProductCategory.DECADE_80S)
                .withPurchasePrice(new BigDecimal("0.5"))
                .withQuantityInStock(Quantity.ONE)
                .withRecordInformation(RecordInformationTestBuilder.populatedRecordInformationBuilder().build())
                .withShippingInformation(new ShippingInformation(ShippingCompany.SCHENKER, new BigDecimal("70"), PickupStrategy.ALLOW_PICKUP))
                .withTax(new Tax(25))
                .withCurrency(Currency.getInstance(new Locale("sv", "SE")))
                .withTargetMarketplace(TargetMarketplace.TRADERA);
    }
}