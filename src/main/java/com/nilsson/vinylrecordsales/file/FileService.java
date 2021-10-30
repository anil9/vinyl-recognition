package com.nilsson.vinylrecordsales.file;

import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static java.lang.String.format;

public class FileService {

    public List<File> getImageFilesInDirectoryOrderedByName(File directory) {
        if (!directory.isDirectory()) {
            throw new IllegalArgumentException(format("Input is not a directory %s", directory));
        }
        File[] filesInDirectory = directory.listFiles();
        if (filesInDirectory == null) {
            return Collections.emptyList();
        }
        return Arrays.stream(filesInDirectory)
                .filter(imageFile())
                .sorted(Comparator.comparing(File::getName))
                .collect(Collectors.toList());
    }

    private Predicate<File> imageFile() {
        return file -> {
            String name = file.getName();
            return name.endsWith(".jpg") || name.endsWith(".jpeg") || name.endsWith(".png");
        };
    }

}
