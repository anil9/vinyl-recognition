package org.nilsson.file;

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
    private static final String SOURCE_DIR = "/home/andreas/Bilder/lp";
    private static final String TARGET_DIR = "/home/andreas/Bilder/interesting_lp";
    public static final int EVERY_NTH_FILE = 5;

    public static void moveFiles(String source, String target) {
        List<Path> files;
        try {
            files = getFilesOrderedByModified(source);

            for (int i = 0; i < files.size(); i++) {
                if (i % EVERY_NTH_FILE == 0) {
                    Path sourceFilePath = files.get(i);
                    Path fileName = sourceFilePath.getFileName();
                    Files.copy(sourceFilePath, Paths.get(target, fileName.toString()), REPLACE_EXISTING);
                }
            }
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    private static List<Path> getFilesOrderedByModified(String source) {
        List<Path> files;
        File[] listFiles = Paths.get(source).toFile().listFiles();
        Arrays.sort(listFiles, Comparator.comparing(File::lastModified));
        files = Arrays.stream(listFiles)
                .map(File::toPath)
                .collect(Collectors.toList());
        return files;
    }

    public static void main(String[] args) {
        moveFiles("/home/andreas/Bilder/lp", "/home/andreas/Bilder/interesting_lp");
    }

}
