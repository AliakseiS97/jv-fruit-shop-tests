package core.basesyntax.test.service.impl;

import core.basesyntax.service.impl.WriterServiceImpl;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class WriterServiceImplTest {

    @Test
    public void write_pathToWriteIsNull_nok() {
        WriterServiceImpl writerService = new WriterServiceImpl();
        Map<String, Integer> storage = new HashMap<>();
        NullPointerException exception =
                Assertions.assertThrows(NullPointerException.class,
                        () -> writerService.write(storage,null)
                );
        Assertions.assertEquals("pathToWrite is null", exception.getMessage());
    }

    @Test
    public void write_storageIsNull_nok() {
        WriterServiceImpl writerService = new WriterServiceImpl();
        NullPointerException exception =
                Assertions.assertThrows(NullPointerException.class,
                        () -> writerService.write(null, "somePathToWrite"));
        Assertions.assertEquals("storage is null", exception.getMessage());
    }

    @Test
    public void write_writerIsValid_ok() throws IOException {
        File tempFile = File.createTempFile("test", ".svc");
        try {
            WriterServiceImpl writerService = new WriterServiceImpl();
            Map<String, Integer> storage = new HashMap<>();
            storage.put("apple", 10);
            storage.put("banana", 20);
            writerService.write(storage, tempFile.getAbsolutePath());
            List<String> lines = Files.readAllLines(tempFile.toPath());
            Assertions.assertEquals("fruit,quantity", lines.get(0));
            Assertions.assertTrue(lines.contains("apple,10"));
            Assertions.assertTrue(lines.contains("banana,20"));
        } finally {
            Files.deleteIfExists(tempFile.toPath());
        }
    }

}
