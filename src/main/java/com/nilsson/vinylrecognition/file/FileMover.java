package com.nilsson.vinylrecognition.file;

import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

public class FileMover {
    private static final Path SOURCE_DIR = Paths.get("/home/andreas/Bilder/lp");
    private static final Path TARGET_DIR = Paths.get("/home/andreas/Bilder/interesting_lp");

    public static void moveEveryNthFiles(Path source, Path target, int everyNthFile) {
        List<Path> files;
        try {
            files = getFilesOrderedByModified(source);

            for (int i = 0; i < files.size(); i++) {
                if (i % everyNthFile == 0) {
                    Path sourceFilePath = files.get(i);
                    Path fileName = sourceFilePath.getFileName();
                    Files.copy(sourceFilePath, target.resolve(fileName.toString()), REPLACE_EXISTING);
                }
            }
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    private static List<Path> getFilesOrderedByModified(Path source) {
        List<Path> files;
        File[] listFiles = source.toFile().listFiles();
        Arrays.sort(listFiles, Comparator.comparing(File::lastModified));
        files = Arrays.stream(listFiles)
                .map(File::toPath)
                .collect(Collectors.toList());
        return files;
    }

    public static void main(String[] args) {
        moveEveryNthFiles(SOURCE_DIR, TARGET_DIR, 5);
    }

}
