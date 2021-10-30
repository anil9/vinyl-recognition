package com.nilsson.vinylrecordsales.image;

import java.net.URL;

public interface UrlRepository {

    void add(URL exampleFileUrl);

    URL poll();

    boolean haveStoredURLs();
}
