package com.nilsson.vinylrecordsales.lookup;

public enum ExternalIdentifier {
    RECORD_TITLE("title"),
    STYLES("styles"),
    GENRES("genres"),
    RELEASE_ID("id"),
    YEAR("year"),
    TRACKLIST("tracklist"),
    TRACK_TITLE("title"),
    TRACK_DURATION("duration"),
    TYPE("type"),
    RELEASE("release");

    private final String value;

    ExternalIdentifier(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
