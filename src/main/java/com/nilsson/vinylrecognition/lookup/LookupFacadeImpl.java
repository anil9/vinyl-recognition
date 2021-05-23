package com.nilsson.vinylrecognition.lookup;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.nilsson.vinylrecognition.domain.ApiToken;
import com.nilsson.vinylrecognition.domain.ApiTokenFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.lang.invoke.MethodHandles;
import java.util.HashSet;
import java.util.Optional;

import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

public class LookupFacadeImpl implements LookupFacade {
    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private final ApiToken apiToken;
    private final WebClient client;

    public LookupFacadeImpl(ApiToken apiToken, WebClient client) {
        this.apiToken = apiToken;
        this.client = client;
    }

    @Override
    public Optional<String> findTitleByCatalogueNumber(String catalogueNumber) {
        String response = findByCatalogueNumber(catalogueNumber).block();
        assert response != null;
        LOG.debug(response);
        var asJsonObject = JsonParser.parseString(response).getAsJsonObject();
        var results = asJsonObject.getAsJsonArray("results");
        var titles = new HashSet<String>();
        for (JsonElement result : results) {
            var recordObj = result.getAsJsonObject();
            titles.add(recordObj.get("title").getAsString());
        }

        if (titles.size() > 1) {
            LOG.warn("Catalogue number: <{}> resulted in <{}> unique titles. Returning empty instead: {}", catalogueNumber, titles.size(), titles);
            return Optional.empty();
        }
        return titles.stream().findFirst();
    }

    Mono<String> findByCatalogueNumber(String catalogueNumber) {

        WebClient.RequestHeadersUriSpec<?> uriSpec = client.get();

        WebClient.RequestHeadersSpec<?> headersSpec = uriSpec.uri("/database/search?catno=" + catalogueNumber);
        return headersSpec.header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON)
                .header("Authorization", "Discogs token=" + apiToken.getValue())
                .retrieve()
                .bodyToMono(String.class);
    }

    public static void main(String[] args) {
        LookupFacade lookupFacade = new LookupFacadeImpl(ApiTokenFactory.getApiToken(), WebClient.create("https://api.discogs.com"));
        var catalogueNumber = "MLPH 1622";
        String title = lookupFacade.findTitleByCatalogueNumber(catalogueNumber).orElseThrow();
        LOG.info("Catalogue number {} returns title {}", catalogueNumber, title);
    }
}
