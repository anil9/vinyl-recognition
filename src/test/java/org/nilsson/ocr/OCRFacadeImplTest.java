package org.nilsson.ocr;

import org.junit.Test;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.assertj.core.api.Assertions.assertThat;


public class OCRFacadeImplTest {

    private static final Path resources = Paths.get("src", "test", "resources");
    private static final Path images = resources.resolve("images");

    @Test
    public void shouldFindLabelInImage() {
        File file = images.resolve("zarah2.tiff").toFile();
        String text = new OCRFacadeImpl().extractTextFromImage(file);
        assertThat(text)
                .contains("E 048-35144")
                .contains("ZARAH LEANDER");
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowExceptionIfFileDoesntExist() {
        File file = images.resolve("zarah3.tiff").toFile();
        new OCRFacadeImpl().extractTextFromImage(file);
    }
}