package com.nilsson.vinylrecordsales.domain;

import com.nilsson.vinylrecordsales.advertisement.AdvertisementInformationTestBuilder;
import org.assertj.core.data.Offset;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;

import java.net.URL;

import static org.assertj.core.api.Assertions.assertThat;

class AdvertisementInformationConverterTest {

    private AdvertisementInformationConverter converter;

    @BeforeEach
    void setUp() {
        converter = new AdvertisementInformationConverter();
    }

    @Test
    void shouldConvertAdvertisementToJSON() throws JSONException {
        //given
        AdvertisementInformation ad = AdvertisementInformationTestBuilder.populatedAdvertisementInformationBuilder().build();
        //when
        JSONObject parentJson = converter.asJson(ad);
        //then
        assertThat(parentJson.get("condition")).isEqualTo(ad.getCondition().getValue());
        assertThat(parentJson.getInt("quantity")).isEqualTo(ad.getQuantityInStock().value());
        assertThat(parentJson.getInt("tax")).isEqualTo(ad.getTax().value());
        assertThat(parentJson.getInt("folder_id")).isEqualTo(ad.getFolderId());
        assertThat(parentJson.getDouble("purchase_price")).isCloseTo(ad.getPurchasePrice().doubleValue(), Offset.offset(0.001));

        JSONObject textObject = parentJson.getJSONObject("texts").getJSONObject("default").getJSONObject("sv");
        assertThat(textObject.get("name")).isEqualTo(ad.getTitle());
        assertThat(textObject.get("description")).isEqualTo(ad.getDescription().replace("\n", "<br>"));

        JSONObject shipping = parentJson.getJSONObject("shipping").getJSONObject(ad.getTargetMarketplace().id());
        assertThat(shipping.getBoolean("pickup")).isEqualTo(ad.getShippingInformation().pickupStrategy().isPickupAllowed());
        assertThat(shipping.get(ad.getShippingInformation().shippingCompany().getValue())).isEqualTo(ad.getShippingInformation().shippingPrice().toPlainString());

        assertThat(parentJson.getJSONObject("categories").getJSONObject("default").getInt("id")).isEqualTo(ad.getProductCategory().getId());

        JSONObject prices = parentJson.getJSONObject("prices").getJSONObject(ad.getTargetMarketplace().id());
        assertThat(prices.get("currency")).isEqualTo(ad.getCurrency().getCurrencyCode());
        assertThat(prices.getJSONObject("auction").getInt("start")).isEqualTo(ad.getAuctionPrice().intValue());

    }

    @Test
    void shouldConvertAddImageURLsToJSON() throws Exception {
        //given
        final URL anURL = new URL("https://httpstat.us/");
        final URL anotherURL = new URL("https://another-url.us/");

        //when
        JSONObject parentJson = converter.asJson(Flux.just(anURL, anotherURL));

        //then
        final JSONArray urls = parentJson.getJSONArray("urls");
        assertThat(urls.length()).isEqualTo(2);
        assertThat(urls.getString(0)).isEqualTo(anURL.toString());
        assertThat(urls.getString(1)).isEqualTo(anotherURL.toString());


    }
}