package com.nilsson.vinylrecordsales.domain;

public enum TokenProducer {
    DISCOGS("discogs.token.path"),
    SELLO("sello.token.path");

    private final String property;

    TokenProducer(String property) {

        this.property = property;
    }

    public String getProperty() {
        return property;
    }
}
