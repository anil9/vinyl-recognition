package com.nilsson.vinylrecordsales.domain;

import java.util.Objects;

import static java.util.Objects.requireNonNull;

public class TargetMarketplace {
    private final String id;

    public TargetMarketplace(String id) {
        this.id = requireNonNull(id);
    }

    public String getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TargetMarketplace that = (TargetMarketplace) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "TargetMarketplace{" +
                "id='" + id + '\'' +
                '}';
    }
}
