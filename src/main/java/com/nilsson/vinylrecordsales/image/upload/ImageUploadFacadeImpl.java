package com.nilsson.vinylrecordsales.image.upload;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;

import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.lang.invoke.MethodHandles;
import java.net.MalformedURLException;
import java.net.URL;

import static java.lang.String.format;
import static java.util.Objects.requireNonNull;

public class ImageUploadFacadeImpl implements ImageUploadFacade {

    private static final String URL_KEY = "secure_url";
    private final Cloudinary cloudinary;
    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    public ImageUploadFacadeImpl(Cloudinary cloudinary) {
        this.cloudinary = requireNonNull(cloudinary, "cloudinary");
    }

    @Override
    public Mono<URL> uploadImage(File file) {
        try {
            LOG.info("Uploading file {}", file.getName());
            return Mono.just(cloudinary.uploader().upload(file, ObjectUtils.emptyMap()))
                    .map(uploadedInformation -> (String) uploadedInformation.get(URL_KEY))
                    .map(this::createURL);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    private URL createURL(String site) {
        try {
            return new URL(site);
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException(format("Failed creating url for string %s", site), e);
        }

    }
}
