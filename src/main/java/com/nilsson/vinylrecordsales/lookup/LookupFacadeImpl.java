package com.nilsson.vinylrecordsales.lookup;

import com.nilsson.vinylrecordsales.domain.ApiToken;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import static java.util.Objects.requireNonNull;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

public class LookupFacadeImpl implements LookupFacade {
    private final ApiToken apiToken;
    private final WebClient client;

    public LookupFacadeImpl(ApiToken apiToken, WebClient client) {
        this.apiToken = requireNonNull(apiToken, "apiToken");
        this.client = requireNonNull(client, "client");
    }

    @Override
    public Mono<String> findByCatalogueNumber(String catalogueNumber) {

        WebClient.RequestHeadersUriSpec<?> uriSpec = client.get();

        WebClient.RequestHeadersSpec<?> headersSpec = uriSpec.uri("/database/search?catno=" + catalogueNumber);
        return headersSpec.header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON)
                .header("Authorization", "Discogs token=" + apiToken.getToken())
                .retrieve()
                .bodyToMono(String.class);
    }

    @Override
    public Mono<String> getByReleaseId(Integer releaseId) {
        WebClient.RequestHeadersUriSpec<?> uriSpec = client.get();

        WebClient.RequestHeadersSpec<?> headersSpec = uriSpec.uri("/releases/" + releaseId);
        return headersSpec.header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON)
                .header("Authorization", "Discogs token=" + apiToken.getToken())
                .retrieve()
                .bodyToMono(String.class);
    }
}
