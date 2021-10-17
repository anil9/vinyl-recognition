package com.nilsson.vinylrecordsales.domain;

import org.springframework.core.env.Environment;

import static java.util.Objects.requireNonNull;

public class ApiTokenFactory {
    private final Environment environment;

    public ApiTokenFactory(Environment environment) {
        this.environment = requireNonNull(environment);
    }

    public ApiToken discogsApiToken() {
        return new ApiToken(environment, TokenProducer.DISCOGS);
    }

    public ApiToken selloApiToken() {
        return new ApiToken(environment, TokenProducer.SELLO);
    }
}
