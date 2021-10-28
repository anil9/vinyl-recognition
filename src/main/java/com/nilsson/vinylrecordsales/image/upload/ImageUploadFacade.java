package com.nilsson.vinylrecordsales.image.upload;

import java.io.File;
import java.net.URL;

public interface ImageUploadFacade {
    URL uploadImage(File file);
}
