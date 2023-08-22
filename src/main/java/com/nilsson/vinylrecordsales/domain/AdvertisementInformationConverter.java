package com.nilsson.vinylrecordsales.domain;

import org.json.JSONArray;
import org.json.JSONObject;
import reactor.core.publisher.Flux;

import java.net.URL;

public class AdvertisementInformationConverter {


    public JSONObject asJson(AdvertisementInformation ad) {
        JSONObject parentJson = new JSONObject();
        parentJson.put("folder_id", ad.getFolderId());
        parentJson.put("condition", ad.getCondition().getValue());
        parentJson.put("tax", ad.getTax().value());
        parentJson.put("quantity", ad.getQuantityInStock().value());
        parentJson.put("purchase_price", ad.getPurchasePrice().doubleValue());
        parentJson.put("texts", populateTextField(ad));
        parentJson.put("categories", populateCategoriesField(ad));
        parentJson.put("prices", populatePricesField(ad));
        parentJson.put("shipping", populateShippingField(ad));
        return parentJson;
    }

    private JSONObject populateShippingField(AdvertisementInformation ad) {
        ShippingInformation shippingInfo = ad.getShippingInformation();

        JSONObject marketplaceShipping = new JSONObject();
        marketplaceShipping.put("pickup", shippingInfo.pickupStrategy().isPickupAllowed());
        marketplaceShipping.put(shippingInfo.shippingCompany().getValue(), shippingInfo.shippingPrice().toPlainString());
        JSONObject targetMarketplaceId = new JSONObject();
        targetMarketplaceId.put(ad.getTargetMarketplace().id(), marketplaceShipping);

        return targetMarketplaceId;
    }

    private JSONObject populatePricesField(AdvertisementInformation ad) {
        JSONObject auctionInformation = new JSONObject();
        auctionInformation.put("start", ad.getAuctionPrice().intValue());
        JSONObject marketplacePricing = new JSONObject();
        marketplacePricing.put("auction", auctionInformation);
        marketplacePricing.put("currency", ad.getCurrency().getCurrencyCode());
        JSONObject targetMarketplaceId = new JSONObject();
        targetMarketplaceId.put(ad.getTargetMarketplace().id(), marketplacePricing);
        return targetMarketplaceId;
    }

    private JSONObject populateCategoriesField(AdvertisementInformation ad) {
        JSONObject defaultCategory = new JSONObject();
        defaultCategory.put("id", ad.getProductCategory().getId());
        JSONObject categories = new JSONObject();
        categories.put("default", defaultCategory);
        return categories;
    }

    private JSONObject populateTextField(AdvertisementInformation ad) {
        JSONObject texts = new JSONObject();
        JSONObject defaultText = new JSONObject();
        JSONObject swedishText = new JSONObject();
        swedishText.put("name", ad.getTitle());
        swedishText.put("description", ad.getDescription().replace("\n", "<br>"));
        defaultText.put("sv", swedishText);
        texts.put("default", defaultText);
        return texts;
    }

    public JSONObject asJson(Flux<URL> imageUrls) {
        final JSONArray urls = new JSONArray();
        imageUrls.subscribe(urls::put);
        final JSONObject parentJson = new JSONObject();
        parentJson.put("urls", urls);
        return parentJson;
    }
}