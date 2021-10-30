package com.nilsson.vinylrecordsales.image;

import java.net.URL;

public interface UrlRepository {

    void store(URL exampleFileUrl);

    URL getURLByInsertionOrderIndex(int index);

    boolean haveStoredURLs();
}
