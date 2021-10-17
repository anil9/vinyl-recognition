package com.nilsson.vinylrecordsales.lookup;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.nilsson.vinylrecordsales.domain.ApiToken;
import com.nilsson.vinylrecordsales.domain.RecordInformation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.lang.invoke.MethodHandles;
import java.util.HashSet;
import java.util.Optional;

import static com.nilsson.vinylrecordsales.lookup.ExternalIdentifier.RECORD_TITLE;
import static com.nilsson.vinylrecordsales.lookup.ExternalIdentifier.RELEASE_ID;
import static java.util.Objects.requireNonNull;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

public class LookupFacadeImpl implements LookupFacade {
    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private final ApiToken apiToken;
    private final WebClient client;
    private final RecordInformationConverter recordInformationConverter;

    public LookupFacadeImpl(ApiToken apiToken, WebClient client, RecordInformationConverter recordInformationConverter) {
        this.apiToken = requireNonNull(apiToken, "apiToken");
        this.client = requireNonNull(client, "client");
        this.recordInformationConverter = requireNonNull(recordInformationConverter, "recordInformationConverter");
    }

    @Override
    public Optional<RecordInformation> getRecordInformationByCatalogueNumber(String catalogueNumber) {
        JsonArray catalogueNumberResponse = getResponseFromCatalogueNumber(catalogueNumber);
        if (!hasDistinctTitle(catalogueNumberResponse)) {
            return Optional.empty();
        }
        var chosenRecord = catalogueNumberResponse.get(0).getAsJsonObject();
        var releaseId = extractReleaseId(chosenRecord);
        JsonObject releaseResponse = getResponseFromReleaseId(releaseId);
        return recordInformationConverter.getRecordInformation(chosenRecord, releaseResponse);

    }

    private String extractReleaseId(JsonObject chosenRecord) {
        return chosenRecord.get(RELEASE_ID.toString()).getAsString();
    }

    private JsonObject getResponseFromReleaseId(String releaseId) {
        String response = getByReleaseId(releaseId).block();
        assert response != null;
        LOG.debug(response);
        return JsonParser.parseString(response).getAsJsonObject();
    }

    private JsonArray getResponseFromCatalogueNumber(String catalogueNumber) {
        String response = findByCatalogueNumber(catalogueNumber).block();
        assert response != null;
        LOG.debug(response);
        var asJsonObject = JsonParser.parseString(response).getAsJsonObject();
        return asJsonObject.getAsJsonArray("results");
    }

    private boolean hasDistinctTitle(JsonArray results) {
        var titles = new HashSet<String>();
        for (JsonElement result : results) {
            var recordObj = result.getAsJsonObject();
            titles.add(recordObj.get(RECORD_TITLE.toString()).getAsString());
        }

        boolean distinctTitle = titles.size() == 1;
        if (!distinctTitle) {
            LOG.warn("Cannot determine which title to use. Found titles <{}>", titles);
        }

        return distinctTitle;
    }

    Mono<String> findByCatalogueNumber(String catalogueNumber) {

        WebClient.RequestHeadersUriSpec<?> uriSpec = client.get();

        WebClient.RequestHeadersSpec<?> headersSpec = uriSpec.uri("/database/search?catno=" + catalogueNumber);
        return headersSpec.header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON)
                .header("Authorization", "Discogs token=" + apiToken.getToken())
                .retrieve()
                .bodyToMono(String.class);
    }

    Mono<String> getByReleaseId(String releaseId) {
        WebClient.RequestHeadersUriSpec<?> uriSpec = client.get();

        WebClient.RequestHeadersSpec<?> headersSpec = uriSpec.uri("/releases/" + releaseId);
        return headersSpec.header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON)
                .header("Authorization", "Discogs token=" + apiToken.getToken())
                .retrieve()
                .bodyToMono(String.class);
    }
}
