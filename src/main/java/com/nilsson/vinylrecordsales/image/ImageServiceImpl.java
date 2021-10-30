package com.nilsson.vinylrecordsales.image;

import com.nilsson.vinylrecordsales.image.upload.ImageUploadFacade;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.File;
import java.net.URL;
import java.util.List;

import static java.util.Objects.requireNonNull;

public class ImageServiceImpl implements ImageService {

    private static final int MAX_CONCURRENT = 10;
    private final ImageUploadFacade imageUploadFacade;
    private final UrlRepository urlRepository;

    public ImageServiceImpl(ImageUploadFacade imageUploadFacade, UrlRepository urlRepository) {
        this.imageUploadFacade = requireNonNull(imageUploadFacade, "imageUploadFacade");
        this.urlRepository = requireNonNull(urlRepository, "urlRepository");
    }


    @Override
    public Flux<URL> uploadImages(List<File> images) {
        return Flux.fromStream(images.stream())
                .flatMapSequential(imageUploadFacade::uploadImage, MAX_CONCURRENT);
    }

    @Override
    public void storeURLs(Flux<URL> urls) {
        urls.subscribe(urlRepository::add);
    }

    @Override
    public Mono<URL> pollUrl() {
        return Mono.just(urlRepository.poll());
    }

    @Override
    public boolean haveStoredURLs() {
        return urlRepository.haveStoredURLs();
    }


}
