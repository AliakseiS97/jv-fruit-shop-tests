package core.basesyntax.test.service.impl;

import core.basesyntax.service.ReaderService;
import core.basesyntax.service.impl.ReaderServiceImpl;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ReaderServiceImplTest {

    @Test
    public void readLines_pathIsNull_nok() {
        ReaderService readerService = new ReaderServiceImpl();
        String path = null;
        IllegalArgumentException exception =
                Assertions.assertThrows(IllegalArgumentException.class,
                        () -> readerService.readLines(path));
        Assertions.assertEquals("path is null or empty", exception.getMessage());
    }

    @Test
    public void readLines_pathIsEmpty_nok() {
        ReaderService readerService = new ReaderServiceImpl();
        String path = "";
        IllegalArgumentException exception =
                Assertions.assertThrows(IllegalArgumentException.class,
                        () -> readerService.readLines(path));
        Assertions.assertEquals("path is null or empty", exception.getMessage());
    }

    @Test
    public void readLines_fileNotFound_nok() {
        ReaderService readerService = new ReaderServiceImpl();
        String invalidPath = "somePath";
        RuntimeException exception =
                Assertions.assertThrows(RuntimeException.class,
                        () -> readerService.readLines(invalidPath));
        Assertions.assertEquals("Error reading file " + invalidPath,
                exception.getMessage());
    }

    @Test
    public void readLines_validPath_ok() throws IOException {
        Path tempFile = Files.createTempFile("testFile", ".svc");
        List<String> expectedLines = List.of("banana", "apple");
        Files.write(tempFile, expectedLines);
        ReaderService readerService = new ReaderServiceImpl();
        List<String> actualLines = readerService.readLines(tempFile.toString());
        Assertions.assertEquals(expectedLines, actualLines);
        Files.deleteIfExists(tempFile);
    }
}
