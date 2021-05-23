package com.nilsson.vinylrecognition.lookup;

import com.nilsson.vinylrecognition.domain.ApiToken;
import com.nilsson.vinylrecognition.domain.ApiTokenFactory;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

public class LookupFacadeImpl implements LookupFacade {
    private final ApiToken apiToken;

    public LookupFacadeImpl(ApiToken apiToken) {
        this.apiToken = apiToken;
    }

    @Override
    public String lookupCatalogue(String catalogueNumber) {
        var client = WebClient.create("https://api.discogs.com");

        WebClient.RequestHeadersUriSpec<?> uriSpec = client.get();

        WebClient.RequestHeadersSpec<?> headersSpec = uriSpec.uri("/database/search?catno=" + catalogueNumber);
        String responseJson = headersSpec.header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON)
                .header("Authorization", "Discogs token=" + apiToken.getValue())
                .retrieve()
                .bodyToMono(String.class)
                .block();

        System.out.println("responseJson = " + responseJson);

        return null;
    }

    public static void main(String[] args) {
        new LookupFacadeImpl(ApiTokenFactory.getApiToken()).lookupCatalogue("MLPH 1622");
    }
}
