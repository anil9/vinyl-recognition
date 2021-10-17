package com.nilsson.vinylrecordsales.domain;

import java.util.Objects;

public class Tax {
    private final Integer value;

    public Tax(Integer value) {
        if (value == 0 || value == 6 || value == 12 || value == 25) {
            this.value = value;
        } else {
            throw new IllegalArgumentException("Valid values are 0, 6, 12, 25. Input was " + value);
        }
    }

    public Integer getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tax tax = (Tax) o;
        return Objects.equals(value, tax.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return "Tax{" +
                "value=" + value +
                '}';
    }
}
