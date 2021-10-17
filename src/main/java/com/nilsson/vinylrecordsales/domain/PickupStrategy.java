package com.nilsson.vinylrecordsales.domain;

public enum PickupStrategy {
    ALLOW_PICKUP,
    FORBID_PICKUP;

    public boolean isPickupAllowed() {
        return this == ALLOW_PICKUP;
    }
}
