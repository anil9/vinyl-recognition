package com.nilsson.vinylrecordsales.domain;

public record Tax(Integer value) {
    public Tax(Integer value) {
        if (value == 0 || value == 6 || value == 12 || value == 25) {
            this.value = value;
        } else {
            throw new IllegalArgumentException("Valid values are 0, 6, 12, 25. Input was " + value);
        }
    }

}
