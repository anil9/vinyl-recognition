package com.nilsson.vinylrecordsales.domain;

import java.time.Year;

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

    public static ProductCategory getDecade(Year year) {
        if (year.isAfter(Year.of(1949)) && year.isBefore(Year.of(1970))) {
            return DECADE_50S_60S;
        } else if (year.isAfter(Year.of(1969)) && year.isBefore(Year.of(1980))) {
            return DECADE_70S;
        } else if (year.isAfter(Year.of(1979)) && year.isBefore(Year.of(1990))) {
            return DECADE_80S;
        } else if (year.isAfter(Year.of(1989)) && year.isBefore(Year.of(2000))) {
            return DECADE_90S;
        } else if (year.isAfter(Year.of(1999)) && year.isBefore(Year.now().plusYears(1))) {
            return DECADE_2000;
        } else {
            return OTHER;
        }

    }

    public Integer getId() {
        return id;
    }
}
