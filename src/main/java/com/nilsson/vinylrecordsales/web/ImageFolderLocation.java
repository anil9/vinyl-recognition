package com.nilsson.vinylrecordsales.web;

public class ImageFolderLocation {
    private String location;

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }


    @Override
    public String toString() {
        return "ImageFolderLocation{" +
                "location='" + location + '\'' +
                '}';
    }
}
