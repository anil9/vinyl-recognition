package org.nilsson;

import org.nilsson.catno.CatalogueNumberExtractor;
import org.nilsson.ocr.OCRFacadeImpl;
import org.nilsson.preprocessing.ImagePreProcesser;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class App {
    private static final Path resources = Paths.get("src", "main", "resources");
    private static final Path images = resources.resolve("images");
//    private static final Path images = Paths.get("/home/andreas/Bilder/interesting_lp");


    public static void main(String[] args) {


        ImagePreProcesser imagePreProcesser = new ImagePreProcesser();
        File[] jpgFiles = images.toFile().listFiles((d, name) -> name.endsWith(".jpg"));
        if (jpgFiles != null) {
            imagePreProcesser.preProcess(jpgFiles);
        }

        OCRFacadeImpl ocrFacade = new OCRFacadeImpl();
        List<String> catalogueNumbers = Arrays.stream(images.toFile().listFiles((d, name) -> name.endsWith(".tiff")))
                .map(ocrFacade::extractTextFromImage)
                .map(CatalogueNumberExtractor::extractCatalogueNumber)
                .collect(Collectors.toList());

        System.out.println("catalogueNumbers = " + catalogueNumbers);

    }

    public static List<String> correctCatalogueNumbers() {
        //TODO
        return Arrays.asList("KULP-3300", "7C 034-34207", "POLL 117", "SSL 10247", "LPRO 51");
    }
}
