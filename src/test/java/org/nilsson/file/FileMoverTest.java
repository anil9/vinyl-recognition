package org.nilsson.file;

import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;


public class FileMoverTest {
    private static final Path resources = Paths.get("src", "test", "resources");
    private static final Path srcFolder = resources.resolve("srcFolder");
    private static final Path destFolder = resources.resolve("destFolder");


    @Before
    public void setUp() throws Exception {
        srcFolder.toFile().mkdirs();
        destFolder.toFile().mkdirs();
        FileUtils.cleanDirectory(srcFolder.toFile());
        FileUtils.cleanDirectory(destFolder.toFile());
    }

    @After
    public void tearDown() throws Exception {
        FileUtils.cleanDirectory(srcFolder.toFile());
        FileUtils.cleanDirectory(destFolder.toFile());
    }

    @Test
    public void shouldCopyFile() throws IOException {
        File.createTempFile("test", "a", srcFolder.toFile());

        FileMover.moveFiles(srcFolder, destFolder);
        assertThat(destFolder.toFile()).isDirectoryContaining(file -> file.getName().matches("test.*a"));

    }

    @Test
    public void shouldCopyEveryNthFile() throws IOException, InterruptedException {
        for (int i = 1; i <= 6; i++) {
            File.createTempFile(i + "test", "a", srcFolder.toFile());
            TimeUnit.MILLISECONDS.sleep(50);
        }

        FileMover.moveFiles(srcFolder, destFolder);

        assertThat(Files.list(destFolder)).hasSize(2);
        assertThat(destFolder.toFile())
                .isDirectoryContaining(file -> file.getName().matches("1test.*a"))
                .isDirectoryContaining(file -> file.getName().matches("6test.*a"));


    }
}