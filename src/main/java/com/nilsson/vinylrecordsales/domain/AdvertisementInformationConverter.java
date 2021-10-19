package com.nilsson.vinylrecordsales.domain;

import org.json.JSONException;
import org.json.JSONObject;

public class AdvertisementInformationConverter {

    private AdvertisementInformationConverter() {
    }

    public static JSONObject asJson(AdvertisementInformation ad) throws JSONException {
        JSONObject parentJson = new JSONObject();
        parentJson.put("folder_id", ad.getFolderId());
        parentJson.put("condition", ad.getCondition().getValue());
        parentJson.put("tax", ad.getTax().getValue());
        parentJson.put("quantity", ad.getQuantityInStock().getValue());
        parentJson.put("purchase_price", ad.getPurchasePrice().doubleValue());
        parentJson.put("texts", populateTextField(ad));
        parentJson.put("categories", populateCategoriesField(ad));
        parentJson.put("prices", populatePricesField(ad));
        parentJson.put("shipping", populateShippingField(ad));
        return parentJson;


    }

    private static JSONObject populateShippingField(AdvertisementInformation ad) throws JSONException {
        ShippingInformation shippingInfo = ad.getShippingInformation();

        JSONObject marketplaceShipping = new JSONObject();
        marketplaceShipping.put("pickup", shippingInfo.getPickupStrategy().isPickupAllowed());
        marketplaceShipping.put(shippingInfo.getShippingCompany().getValue(), shippingInfo.getShippingPrice().toPlainString());
        JSONObject targetMarketplaceId = new JSONObject();
        targetMarketplaceId.put(TargetMarketplace.TRADERA.getId(), marketplaceShipping);

        return targetMarketplaceId;
    }

    private static JSONObject populatePricesField(AdvertisementInformation ad) throws JSONException {
        JSONObject auctionInformation = new JSONObject();
        auctionInformation.put("start", ad.getAuctionPrice().intValue());
        JSONObject marketplacePricing = new JSONObject();
        marketplacePricing.put("auction", auctionInformation);
        marketplacePricing.put("currency", ad.getCurrency().getCurrencyCode());
        JSONObject targetMarketplaceId = new JSONObject();
        targetMarketplaceId.put(TargetMarketplace.TRADERA.getId(), marketplacePricing);
        return targetMarketplaceId;
    }

    private static JSONObject populateCategoriesField(AdvertisementInformation ad) throws JSONException {
        JSONObject defaultCategory = new JSONObject();
        defaultCategory.put("id", ad.getProductCategory().getId());
        JSONObject categories = new JSONObject();
        categories.put("default", defaultCategory);
        return categories;
    }

    private static JSONObject populateTextField(AdvertisementInformation ad) throws JSONException {
        JSONObject texts = new JSONObject();
        JSONObject defaultText = new JSONObject();
        JSONObject swedishText = new JSONObject();
        swedishText.put("name", ad.getTitle());
        swedishText.put("description", ad.getDescription().replace("\n", "<br>"));
        defaultText.put("sv", swedishText);
        texts.put("default", defaultText);
        return texts;
    }
}