package com.nilsson.vinylrecordsales.image;

import java.net.URL;
import java.util.LinkedList;
import java.util.Queue;

public class UrlRepositoryImpl implements UrlRepository {
    private final Queue<URL> urls;

    public UrlRepositoryImpl() {
        urls = new LinkedList<>();
    }

    @Override
    public void add(URL url) {
        urls.add(url);
    }

    @Override
    public URL poll() {
        return urls.poll();
    }

    @Override
    public boolean haveStoredURLs() {
        return !urls.isEmpty();
    }
}
