package com.nilsson.vinylrecordsales.image.upload;

import reactor.core.publisher.Mono;

import java.io.File;
import java.net.URL;

public interface ImageUploadFacade {
    Mono<URL> uploadImage(File file);
}
