package com.nilsson.vinylrecordsales.domain;

public enum TargetMarketplace {
    TRADERA("53501");
    private final String id;

    TargetMarketplace(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
