package com.nilsson.vinylrecordsales.domain;

import org.json.JSONObject;

public class AdvertisementInformationConverter {


    public JSONObject asJson(AdvertisementInformation ad) {
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

    private JSONObject populateShippingField(AdvertisementInformation ad) {
        ShippingInformation shippingInfo = ad.getShippingInformation();

        JSONObject marketplaceShipping = new JSONObject();
        marketplaceShipping.put("pickup", shippingInfo.getPickupStrategy().isPickupAllowed());
        marketplaceShipping.put(shippingInfo.getShippingCompany().getValue(), shippingInfo.getShippingPrice().toPlainString());
        JSONObject targetMarketplaceId = new JSONObject();
        targetMarketplaceId.put(ad.getTargetMarketplace().getId(), marketplaceShipping);

        return targetMarketplaceId;
    }

    private JSONObject populatePricesField(AdvertisementInformation ad) {
        JSONObject auctionInformation = new JSONObject();
        auctionInformation.put("start", ad.getAuctionPrice().intValue());
        JSONObject marketplacePricing = new JSONObject();
        marketplacePricing.put("auction", auctionInformation);
        marketplacePricing.put("currency", ad.getCurrency().getCurrencyCode());
        JSONObject targetMarketplaceId = new JSONObject();
        targetMarketplaceId.put(ad.getTargetMarketplace().getId(), marketplacePricing);
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
}