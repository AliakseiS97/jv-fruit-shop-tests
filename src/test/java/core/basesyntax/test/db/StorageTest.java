package core.basesyntax.test.db;

import core.basesyntax.db.Storage;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class StorageTest {
    private Storage storage;

    @BeforeEach
    public void setUp() {
        storage = new Storage();
    }

    @Test
    public void put_nullKey_nok() {
        IllegalArgumentException exception = Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> storage.put(null, 1));
        Assertions.assertEquals("Fruit name cannot be null", exception.getMessage());
    }

    @Test
    public void put_value_ok() {
        storage.put("apple", 20);
        Assertions.assertEquals(20, storage.get("apple"));
    }

    @Test
    public void put_sameKey_ok() {
        storage.put("apple", 20);
        storage.put("apple", 49);
        storage.put("apple", 3);
        Assertions.assertEquals(3, storage.get("apple"));
    }

    @Test
    public void get_defaultValue_ok() {
        Assertions.assertEquals(0, storage.get("unknownFruit"));
    }

    @Test
    public void get_value_ok() {
        storage.put("apple", 9);
        Assertions.assertEquals(9, storage.get("apple"));
    }

    @Test
    public void getStorage_isCopy_nok() {
        Map<String, Integer> copy = storage.getStorage();
        Assertions.assertThrows(UnsupportedOperationException.class,
                () -> copy.put("apple", 10));
    }

    @Test
    public void getStorage_containsActualData_ok() {
        storage.put("apple", 2);
        storage.put("banana", 10);
        Map<String, Integer> copy = storage.getStorage();
        Assertions.assertEquals(2, copy.get("apple"));
        Assertions.assertEquals(10, copy.get("banana"));
    }

    @Test
    public void getStorage_isIndependentCopy_ok() {
        storage.put("apple", 2);
        Map<String, Integer> copy = storage.getStorage();
        storage.put("apple", 50);
        Assertions.assertEquals(2, copy.get("apple"));
    }

    @AfterEach
    public void tearDown() {
        storage = null;
    }
}
