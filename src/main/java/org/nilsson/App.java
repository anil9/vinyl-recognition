package org.nilsson;

import org.apache.commons.io.FileUtils;
import org.nilsson.catno.CatalogueNumberExtractor;
import org.nilsson.file.FileMover;
import org.nilsson.ocr.OCRFacadeImpl;
import org.nilsson.preprocessing.ImagePreProcessor;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

public class App {
    //    private static final Path TARGET_DIR = Paths.get("src", "main", "resources", "images");
    private static final Path SOURCE_DIR = Paths.get("/home/andreas/Bilder/lp");
    private static final Path TARGET_DIR = Paths.get("/home/andreas/Bilder/interesting_lp");


    public static void main(String[] args) throws IOException {
        FileUtils.cleanDirectory(TARGET_DIR.toFile());
        FileMover.moveEveryNthFiles(SOURCE_DIR, TARGET_DIR, 5);

        ImagePreProcessor imagePreProcessor = new ImagePreProcessor();
        File[] jpgFiles = getSortedJpgFiles();
        if (jpgFiles != null) {
            imagePreProcessor.preProcess(jpgFiles);
        }

        OCRFacadeImpl ocrFacade = new OCRFacadeImpl();
        System.out.println(Arrays.toString(getSortedTiffFiles()));
        List<String> catalogueNumbers = Arrays.stream(getSortedTiffFiles())
                .map(ocrFacade::extractTextFromImage)
                .map(CatalogueNumberExtractor::extractCatalogueNumber)
                .collect(Collectors.toList());
        System.out.println("catalogueNumbers = " + catalogueNumbers);

        Set<String> correctCatalogueNumbers = correctCatalogueNumbers();
        long matchedCount = catalogueNumbers.stream()
                .filter(correctCatalogueNumbers::contains)
                .count();
        System.out.printf("correctly matched = %s/%s (%s)%n", matchedCount, correctCatalogueNumbers.size(), matchedCount / (double) correctCatalogueNumbers.size());
        long nullCount = catalogueNumbers.stream().filter(Objects::isNull).count();
        long notMatched = correctCatalogueNumbers.size() - matchedCount;
        long falsePositives = notMatched - nullCount;
        System.out.printf("False positives (lower is better): %s/%s (%s)%n", falsePositives, notMatched, falsePositives / ((double) notMatched));

    }

    private static File[] getSortedTiffFiles() {
        return getSortedFiles(".tiff");
    }

    private static File[] getSortedJpgFiles() {
        return getSortedFiles(".jpg");
    }

    private static File[] getSortedFiles(String s) {
        File[] files = TARGET_DIR.toFile().listFiles((d, name) -> name.endsWith(s));
        Arrays.sort(files, Comparator.comparing(File::lastModified));
        return files;
    }


    public static Set<String> correctCatalogueNumbers() {
        return new HashSet<>(Arrays.asList("KULP-3300", "7C 034-34207", "POLL 117", "SSL 10247", "LPRO 51", "HLP-10521-A"
                , "7C 138-35747", "2600831", "6316 090", "PL 40164", "KLP 8", "MLPH 1622", "BBRLP 108", "HLP 10.533"
                , "MLP 15.555", "2379.007", "ARLP 101", "ABLP-501", "SLP-3124", "4E 048-35144", "PMES 572"));
    }
}
