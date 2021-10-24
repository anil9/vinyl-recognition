package com.nilsson.vinylrecordsales.domain;

import org.springframework.core.env.Environment;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.Locale;

import static com.nilsson.vinylrecordsales.domain.AdvertisementInformationFactory.AdvertisementInfoConfigProperty.*;
import static java.util.Objects.requireNonNull;

public class AdvertisementInformationFactory {

    private final Integer folderId;
    private final BigDecimal auctionPrice;
    private final ShippingInformation shippingInformation;
    private final TargetMarketplace targetMarketplace;

    public AdvertisementInformationFactory(Environment environment) {
        requireNonNull(environment, "environment");
        this.folderId = Integer.parseInt(environment.getRequiredProperty(FOLDER_ID.value));
        this.auctionPrice = new BigDecimal(environment.getRequiredProperty(AUCTION_PRICE.value));
        this.targetMarketplace = new TargetMarketplace(environment.getRequiredProperty(MARKETPLACE_ACCOUNT_ID.value));
        BigDecimal shippingCost = new BigDecimal(environment.getRequiredProperty(SHIPPING_COST.value));
        this.shippingInformation = new ShippingInformation(ShippingCompany.SCHENKER, shippingCost, PickupStrategy.ALLOW_PICKUP);
    }

    public AdvertisementInformation fromTemplate(RecordInformation recordInformation) {
        ProductCategory productCategory = recordInformation.getYear()
                .map(ProductCategory::getDecade)
                .orElse(ProductCategory.OTHER);
        return AdvertisementInformation.builder()
                .withAuctionPrice(auctionPrice)
                .withCondition(Condition.USED)
                .withFolderId(folderId)
                .withPurchasePrice(new BigDecimal("0.5"))
                .withQuantityInStock(Quantity.ONE)
                .withRecordInformation(recordInformation)
                .withShippingInformation(shippingInformation)
                .withTax(new Tax(25))
                .withCurrency(Currency.getInstance(new Locale("sv", "SE")))
                .withTargetMarketplace(targetMarketplace)
                .withProductCategory(productCategory)
                .build();
    }

    enum AdvertisementInfoConfigProperty {
        FOLDER_ID("advertisement.template.folderid"),
        AUCTION_PRICE("advertisement.template.auctionprice"),
        SHIPPING_COST("advertisement.template.shippingcost"),
        MARKETPLACE_ACCOUNT_ID("advertisement.template.marketplace.tradera.id");


        public final String value;

        AdvertisementInfoConfigProperty(String value) {

            this.value = value;
        }
    }
}
