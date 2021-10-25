package com.nilsson.vinylrecordsales.web;

public class RecordFinder {
    private String catalogueId;
    private String extraTitleWords;

    public String getCatalogueId() {
        return catalogueId;
    }

    public void setCatalogueId(String catalogueId) {
        this.catalogueId = catalogueId;
    }

    public String getExtraTitleWords() {
        return extraTitleWords;
    }

    public void setExtraTitleWords(String extraTitleWords) {
        this.extraTitleWords = extraTitleWords;
    }

    @Override
    public String toString() {
        return "RecordFinder{" +
                "catalogueId='" + catalogueId + '\'' +
                ", extraTitleWords='" + extraTitleWords + '\'' +
                '}';
    }
}
