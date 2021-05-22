package com.nilsson.vinylrecognition.preprocessing;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.assertj.core.api.Assertions.assertThat;

public class ImagePreProcessorTest {

    private static final Path resources = Paths.get("src", "test", "resources");
    private static final Path imagesFolder = resources.resolve("images");
    private static final Path srcFolder = resources.resolve("srcFolder");
    private final String jpgImage = "skansens_spelmanslag.jpg";
    private final String tiffImage = "skansens_spelmanslag.tiff";
    private ImagePreProcessor imagePreProcessor;

    @BeforeEach
    public void setUp() throws Exception {
        imagePreProcessor = new ImagePreProcessor();
        srcFolder.toFile().mkdirs();
        FileUtils.cleanDirectory(srcFolder.toFile());

    }

    @AfterEach
    public void tearDown() throws Exception {
        FileUtils.cleanDirectory(srcFolder.toFile());
    }

    @Test
    public void shouldConvertToTiff() throws IOException {
        FileUtils.copyFile(imagesFolder.resolve(jpgImage).toFile(), srcFolder.resolve(jpgImage).toFile());

        assertThat(srcFolder).isDirectoryContaining(path -> path.endsWith(Path.of(jpgImage)));

        imagePreProcessor.preProcess(srcFolder.resolve(Path.of(jpgImage)).toFile());

        assertThat(Files.list(srcFolder)).hasSize(1);
        assertThat(srcFolder).isDirectoryContaining(path -> path.endsWith(Path.of(tiffImage)));
    }

    @Test
    public void shouldConvertImageToGrayScale() throws IOException {
        FileUtils.copyFile(imagesFolder.resolve(jpgImage).toFile(), srcFolder.resolve(jpgImage).toFile());

        assertThat(srcFolder).isDirectoryContaining(path -> path.endsWith(Path.of(jpgImage)));

        imagePreProcessor.preProcess(srcFolder.resolve(Path.of(jpgImage)).toFile());

        BufferedImage image = ImageIO.read(srcFolder.resolve(Path.of(tiffImage)).toFile());
        assertThat(isGrayScale(image)).isTrue();
    }

    boolean isGrayScale(BufferedImage image) {
        // Test the type
        if (image.getType() == BufferedImage.TYPE_BYTE_GRAY) return true;
        if (image.getType() == BufferedImage.TYPE_USHORT_GRAY) return true;
        // Test the number of channels / bands
        if (image.getRaster().getNumBands() == 1) return true; // Single channel => gray scale

        // Multi-channels image; then you have to test the color for each pixel.
        for (int y = 0; y < image.getHeight(); y++)
            for (int x = 0; x < image.getWidth(); x++)
                for (int c = 1; c < image.getRaster().getNumBands(); c++)
                    if (image.getRaster().getSample(x, y, c - 1) != image.getRaster().getSample(x, y, c)) return false;

        return true;
    }
}