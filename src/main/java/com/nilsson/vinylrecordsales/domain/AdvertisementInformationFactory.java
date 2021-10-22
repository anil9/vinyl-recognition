package com.nilsson.vinylrecordsales.domain;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.Locale;

public class AdvertisementInformationFactory {

    private static final int GENERATED_ADS_FOLDER = 1102266;

    private AdvertisementInformationFactory() {
    }

    public static AdvertisementInformation advertisementTemplate(RecordInformation recordInformation, BigDecimal auctionPrice) {
        ProductCategory productCategory = recordInformation.getYear()
                .map(ProductCategory::getDecade)
                .orElse(ProductCategory.OTHER);
        return AdvertisementInformation.builder()
                .withAuctionPrice(auctionPrice)
                .withCondition(Condition.USED)
                .withFolderId(GENERATED_ADS_FOLDER)
                .withPurchasePrice(new BigDecimal("0.5"))
                .withQuantityInStock(Quantity.ONE)
                .withRecordInformation(recordInformation)
                .withShippingInformation(new ShippingInformation(ShippingCompany.SCHENKER, new BigDecimal("70"), PickupStrategy.ALLOW_PICKUP))
                .withTax(new Tax(25))
                .withCurrency(Currency.getInstance(new Locale("sv", "SE")))
                .withTargetMarketplace(TargetMarketplace.TRADERA)
                .withProductCategory(productCategory)
                .build();
    }
}
