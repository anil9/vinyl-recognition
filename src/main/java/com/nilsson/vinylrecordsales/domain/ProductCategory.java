package com.nilsson.vinylrecordsales.domain;

import static java.util.Objects.requireNonNull;

public enum ProductCategory {
    DECADE_50S_60S(210901),
    DECADE_70S(210903),
    DECADE_80S(210905),
    DECADE_90S(210907),
    DECADE_2000(302335),
    OTHER(210830);


    private final Integer id;

    ProductCategory(Integer id) {
        this.id = requireNonNull(id, "id");
    }

    public Integer getId() {
        return id;
    }
}
