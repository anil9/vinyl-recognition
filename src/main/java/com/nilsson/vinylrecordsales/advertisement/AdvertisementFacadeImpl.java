package com.nilsson.vinylrecordsales.advertisement;

import com.nilsson.vinylrecordsales.domain.AdvertisementInformation;
import com.nilsson.vinylrecordsales.domain.AdvertisementInformationConverter;
import com.nilsson.vinylrecordsales.domain.ApiToken;
import com.nilsson.vinylrecordsales.domain.ProductId;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.function.Function;

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
        try {
            return monoCreateProduct(advertisementInformation)
                    .block();
        } catch (Exception e) {
            throw new AdvertisementFacadeException(format("Error creating product. advertisementInformation=%s", advertisementInformation), e);
        }
    }

    @Override
    public Mono<ProductId> monoCreateProduct(AdvertisementInformation advertisementInformation) {
        String requestBody = converter.asJson(advertisementInformation).toString();
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
                .map(ProductId::new);
    }

    @Override
    public Flux<URL> addImagesToProduct(Mono<ProductId> productId, Flux<URL> imageUrls) {
        String requestBody = converter.asJson(imageUrls).toString();

        return productId.flatMapMany(id -> postImageRequest(id, requestBody));

    }

    private Flux<URL> postImageRequest(ProductId productId, String requestBody) {
        return client.post()
                .uri(format("/products/%s/images", productId.getId()))
                .header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
                .header(AUTHORIZATION, apiToken.getToken())
                .accept(APPLICATION_JSON)
                .body(BodyInserters.fromValue(requestBody))
                .retrieve()
                .bodyToMono(String.class)
                .map(JSONObject::new)
                .map(jsonObject -> jsonObject.getJSONObject("data"))
                .map(jsonObject -> jsonObject.getJSONArray("images"))
                .flatMapMany(this::getElements);
    }

    private Flux<URL> getElements(JSONArray array) {
        return Flux.range(0, array.length())
                .map(array::getString)
                .map(createURL());
    }

    private Function<String, URL> createURL() {
        return imageURL -> {
            try {
                return new URL(imageURL);
            } catch (MalformedURLException e) {
                throw new IllegalStateException(format("Cannot parse URL %s", imageURL), e);
            }
        };
    }
}
