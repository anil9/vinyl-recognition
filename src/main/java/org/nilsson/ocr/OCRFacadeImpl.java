package org.nilsson.ocr;

import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

import java.io.File;

public class OCRFacadeImpl implements OCRFacade {
    @Override
    public String extractTextFromImage(File image) {
        if(!image.exists()){
            throw new IllegalArgumentException("Image doesn't exist: " + image.getAbsolutePath());
        }
        Tesseract tesseract = new Tesseract();
        tesseract.setDatapath("src/main/resources/tessdata");
        tesseract.setLanguage("swe");
        tesseract.setPageSegMode(3);
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
