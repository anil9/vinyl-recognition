package com.nilsson.vinylrecognition;

import com.nilsson.vinylrecognition.catno.CatalogueNumberExtractor;
import com.nilsson.vinylrecognition.file.FileMover;
import com.nilsson.vinylrecognition.ocr.OCRFacadeImpl;
import com.nilsson.vinylrecognition.preprocessing.ImagePreProcessor;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

import static java.lang.invoke.MethodHandles.lookup;
import static org.slf4j.LoggerFactory.getLogger;

@SpringBootApplication
public class VinylRecognitionApplication {
	//    private static final Path TARGET_DIR = Paths.get("src", "main", "resources", "images");
	private static final Path SOURCE_DIR = Paths.get("/home/andreas/Bilder/lp");
	private static final Path TARGET_DIR = Paths.get("/home/andreas/Bilder/interesting_lp");
	private static final Logger LOG = getLogger(lookup().lookupClass());


	public static void main(String[] args) throws IOException {
		SpringApplication.run(VinylRecognitionApplication.class, args);

		FileUtils.cleanDirectory(TARGET_DIR.toFile());
		FileMover.moveEveryNthFiles(SOURCE_DIR, TARGET_DIR, 5);

		ImagePreProcessor imagePreProcessor = new ImagePreProcessor();
		File[] jpgFiles = getSortedJpgFiles();
		if (jpgFiles != null) {
			imagePreProcessor.preProcess(jpgFiles);
		}

		OCRFacadeImpl ocrFacade = new OCRFacadeImpl();
		LOG.debug(Arrays.toString(getSortedTiffFiles()));
		List<String> catalogueNumbers = Arrays.stream(getSortedTiffFiles())
				.map(ocrFacade::extractTextFromImage)
				.map(CatalogueNumberExtractor::extractCatalogueNumber)
				.collect(Collectors.toList());
		LOG.debug("catalogueNumbers = " + catalogueNumbers);

		Set<String> correctCatalogueNumbers = correctCatalogueNumbers();
		long matchedCount = catalogueNumbers.stream()
				.filter(correctCatalogueNumbers::contains)
				.count();
		LOG.info("correctly matched = {}/{} ({})%n", matchedCount, correctCatalogueNumbers.size(), matchedCount / (double) correctCatalogueNumbers.size());
		long nullCount = catalogueNumbers.stream().filter(Objects::isNull).count();
		long notMatched = correctCatalogueNumbers.size() - matchedCount;
		long falsePositives = notMatched - nullCount;
		LOG.info("False positives (lower is better): {}/{} ({})%n", falsePositives, notMatched, falsePositives / ((double) notMatched));

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
