package com.nilsson.vinylrecognition.ocr;

import java.io.File;

public interface OCRFacade {
    String extractTextFromImage(File file);
}
