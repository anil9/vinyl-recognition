package com.nilsson.vinylrecordsales.domain;

import static java.util.Objects.requireNonNull;

public record TargetMarketplace(String id) {
    public TargetMarketplace(String id) {
        this.id = requireNonNull(id);
    }
}
