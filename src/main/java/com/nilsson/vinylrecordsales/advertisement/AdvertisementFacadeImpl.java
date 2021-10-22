package com.nilsson.vinylrecordsales.advertisement;

import com.nilsson.vinylrecordsales.domain.AdvertisementInformation;
import com.nilsson.vinylrecordsales.domain.AdvertisementInformationConverter;
import com.nilsson.vinylrecordsales.domain.ApiToken;
import com.nilsson.vinylrecordsales.domain.ProductId;
import org.json.JSONObject;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import static java.lang.String.format;
import static java.util.Objects.requireNonNull;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

public class AdvertisementFacadeImpl implements AdvertisementFacade {
    private final ApiToken apiToken;
    private final WebClient client;
    private final AdvertisementInformationConverter converter;

    public AdvertisementFacadeImpl(ApiToken apiToken, WebClient client, AdvertisementInformationConverter converter) {
        this.apiToken = requireNonNull(apiToken, "apiToken");
        this.client = requireNonNull(client, "client");
        this.converter = requireNonNull(converter, "converter");
    }

    @Override
    public ProductId createProduct(AdvertisementInformation advertisementInformation) {
        String requestBody = converter.asJson(advertisementInformation).toString();
        try {
            return client.post()
                    .uri("/products")
                    .header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
                    .header(AUTHORIZATION, apiToken.getToken())
                    .accept(APPLICATION_JSON)
                    .body(BodyInserters.fromValue(requestBody))
                    .retrieve()
                    .bodyToMono(String.class)
                    .map(JSONObject::new)
                    .map(jsonObject -> jsonObject.getInt("id"))
                    .map(ProductId::new)
                    .block();
        } catch (Exception e) {
            throw new AdvertisementFacadeException(format("Error creating product. requestBody=%s", requestBody), e);
        }
    }
}
