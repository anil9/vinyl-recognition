package com.nilsson.vinylrecognition.ocr;

import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.lang.invoke.MethodHandles;

public class OCRFacadeImpl implements OCRFacade {

    private final Tesseract tesseract;
    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    public OCRFacadeImpl(Tesseract tesseract) {
        this.tesseract = tesseract;
    }

    @Override
    public String extractTextFromImage(File image) {
        if (!image.exists()) {
            throw new IllegalArgumentException("Image doesn't exist: " + image.getAbsolutePath());
        }
//        tesseract.setOcrEngineMode(1);
//        tesseract.setHocr(true);
        try {
            return tesseract.doOCR(image);
        } catch (TesseractException e) {
            LOG.error("Failed for image {}", image.getName(), e);
        }
        return "";
    }
}
