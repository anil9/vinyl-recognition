package com.nilsson.vinylrecordsales.domain;

public enum ApiTokenProducer {
    DISCOGS("api.token.path.discogs"),
    SELLO("api.token.path.sello"),
    CLOUDINARY("api.token.path.cloudinary");

    private final String property;

    ApiTokenProducer(String property) {

        this.property = property;
    }

    public String getProperty() {
        return property;
    }
}
