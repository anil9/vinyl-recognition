package com.nilsson.vinylrecordsales.image.upload;

import com.cloudinary.Cloudinary;

import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.URL;
import java.util.Collections;
import java.util.Map;

import static java.util.Objects.requireNonNull;

public class ImageUploadFacadeImpl implements ImageUploadFacade {

    private static final String URL_KEY = "secure_url";
    private final Cloudinary cloudinary;

    public ImageUploadFacadeImpl(Cloudinary cloudinary) {
        this.cloudinary = requireNonNull(cloudinary, "cloudinary");
    }

    @Override
    public URL uploadImage(File file) {
        try {
            Map<String, String> uploadedInformation = cloudinary.uploader().upload(file, Collections.emptyMap());
            return new URL(uploadedInformation.get(URL_KEY));
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}
