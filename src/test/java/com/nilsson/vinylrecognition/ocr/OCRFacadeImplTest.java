package com.nilsson.vinylrecognition.ocr;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.assertj.core.api.Assertions.assertThat;


class OCRFacadeImplTest {

    private static final Path resources = Paths.get("src", "test", "resources");
    private static final Path images = resources.resolve("images");
    private OCRFacadeImpl ocrFacade;

    @BeforeEach
    void setUp() {
        ocrFacade = new OCRFacadeImpl(TesseractFactory.createTesseractInstance());
    }

    @Test
    void shouldFindLabelInImage() {
        File file = images.resolve("zarah2.tiff").toFile();
        String text = ocrFacade.extractTextFromImage(file);
        assertThat(text)
                .contains("E 048-35144")
                .contains("ZARAH LEANDER");
    }

    @Test
    void shouldThrowExceptionIfFileDoesntExist() {
        File file = images.resolve("zarah3.tiff").toFile();
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> ocrFacade.extractTextFromImage(file));

    }
}