package com.nilsson.vinylrecordsales.image;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class UrlRepositoryImpl implements UrlRepository {
    private final List<URL> urls;

    public UrlRepositoryImpl() {
        urls = new ArrayList<>();
    }

    @Override
    public void store(URL url) {
        urls.add(url);
    }

    @Override
    public URL getURLByInsertionOrderIndex(int index) {
        return urls.get(index);
    }
}
