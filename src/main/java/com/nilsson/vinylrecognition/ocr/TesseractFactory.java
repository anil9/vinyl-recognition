package com.nilsson.vinylrecognition.ocr;

import net.sourceforge.tess4j.Tesseract;

public class TesseractFactory {
    public static Tesseract createTesseractInstance() {
        Tesseract tesseract = new Tesseract();
        tesseract.setDatapath("src/main/resources/tessdata");
        tesseract.setLanguage("swe");
        tesseract.setPageSegMode(3);
        tesseract.setTessVariable("user_defined_dpi", "270");
        return tesseract;

    }
}
