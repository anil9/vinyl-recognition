package com.nilsson.vinylrecognition.ocr;

import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

import java.io.File;

public class OCRFacadeImpl implements OCRFacade {

    private final Tesseract tesseract;

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
            System.err.println("Failed for image " + image.getName());
            e.printStackTrace();
        }
        return "";
    }
}
