package com.nilsson.vinylrecordsales.file;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class FileServiceTest {

    private FileService fileService;
    private static final Path resources = Paths.get("src", "test", "resources");
    private static final Path folder = resources.resolve("testFolder");


    @BeforeEach
    void setUp() {
        fileService = new FileService();
        folder.toFile().mkdirs();

    }

    @AfterEach
    void tearDown() throws IOException {
        FileUtils.deleteDirectory(folder.toFile());
    }

    @Test
    void throwsExceptionIfInputIsNotDirectory() throws IOException {
        //given
        File file = File.createTempFile("aFile", ".jpg", folder.toFile());
        //then
        assertThrows(IllegalArgumentException.class, () -> fileService.getFilesInDirectoryOrderedByName(file));
    }

    @Test
    void shouldGetFilesOrderedByName() throws IOException {
        //given
        File fileInSecondPosition = File.createTempFile("IMG_20211024_161211", ".jpg", folder.toFile());
        File fileInFirstPosition = File.createTempFile("IMG_20211024_161011", ".jpg", folder.toFile());
        File fileInThirdPosition = File.createTempFile("IMG_20211024_170111", ".jpg", folder.toFile());
        //when
        List<File> files = fileService.getFilesInDirectoryOrderedByName(folder.toFile());
        //then
        assertThat(files).containsExactly(fileInFirstPosition, fileInSecondPosition, fileInThirdPosition);

    }
}