package com.nilsson.vinylrecordsales.image;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.File;
import java.net.URL;
import java.util.List;

public interface ImageService {
    Flux<URL> uploadImages(List<File> images);

    void storeURLs(Flux<URL> urls);

    Mono<URL> pollUrl();

    boolean haveStoredURLs();
}
