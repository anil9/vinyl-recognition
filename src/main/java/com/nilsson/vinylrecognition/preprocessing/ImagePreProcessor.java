package com.nilsson.vinylrecognition.preprocessing;

import org.im4java.core.ConvertCmd;
import org.im4java.core.GMOperation;
import org.im4java.core.IM4JavaException;
import org.im4java.core.IMOperation;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

public class ImagePreProcessor {

    private static final Path resources = Paths.get("src", "main", "resources");
    private static final Path images = resources.resolve("images");
    private final ConvertCmd convertCmd;


    public ImagePreProcessor() {
        convertCmd = new ConvertCmd();
    }

    public void preProcess(File... files) {
        GMOperation gmOperation = new GMOperation();
        gmOperation.addImage();
        gmOperation.type("Grayscale");
        gmOperation.depth(8);

        IMOperation imOperation = new IMOperation();
        imOperation.format("TIFF");
//        imOperation.crop(0, 0, 0, 900);
//        imOperation.crop(0, 0, 0, -500);
//        imOperation.crop(0, 0, 2000, 0);
        imOperation.addSubOperation(gmOperation);
        imOperation.addImage();

        try {
            for (File file : files) {
                convertCmd.run(imOperation, file.getPath(), file.getPath().replace(".jpg", ".tiff"));
            }
        } catch (IOException | InterruptedException | IM4JavaException e) {
            throw new RuntimeException(e);
        }
        Arrays.stream(files).forEach(File::delete);
    }

    public static void main(String[] args) {

        ImagePreProcessor imagePreProcessor = new ImagePreProcessor();
        imagePreProcessor.preProcess(images.resolve("skansens_spelmanslag.jpg").toFile());
    }

}