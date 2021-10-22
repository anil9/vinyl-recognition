package com.nilsson.vinylrecordsales.domain;

import org.springframework.core.env.Environment;
import org.springframework.web.reactive.function.client.WebClient;

import static java.util.Objects.requireNonNull;

public class WebClientFactory {
    private final Environment environment;

    public WebClientFactory(Environment environment) {
        this.environment = requireNonNull(environment, "environment");
    }

    public WebClient discogsWebClient() {
        return WebClient.create(requireNonNull(environment.getProperty(UrlProperty.DISCOGS.value)));
    }

    public WebClient selloWebClient() {
        return WebClient.create(requireNonNull(environment.getProperty(UrlProperty.SELLO.value)));
    }

    public enum UrlProperty {
        DISCOGS("api.url.base.discogs"),
        SELLO("api.url.base.sello");

        public final String value;

        UrlProperty(String value) {
            this.value = value;
        }
    }
}
