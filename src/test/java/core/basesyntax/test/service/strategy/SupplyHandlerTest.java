package core.basesyntax.test.service.strategy;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.strategy.SupplyHandler;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SupplyHandlerTest {
    private SupplyHandler handler;
    private Storage storage;

    @BeforeEach
    public void setUp() {
        storage = new Storage();
        handler = new SupplyHandler();
    }

    @Test
    public void apply_storageIsNull_nok() {
        FruitTransaction transaction = new FruitTransaction();
        NullPointerException exception =
                Assertions.assertThrows(NullPointerException.class,
                        () -> handler.apply(null, transaction));
        Assertions.assertEquals("Storage is null", exception.getMessage());
    }

    @Test
    public void apply_transactionIsNull_nok() {
        NullPointerException exception =
                Assertions.assertThrows(NullPointerException.class,
                        () -> handler.apply(storage, null));
        Assertions.assertEquals("Transaction is null", exception.getMessage());
    }

    @Test
    public void apply_increasesQuantityAfterSupply_ok() {
        FruitTransaction transaction = new FruitTransaction();
        transaction.setFruit("apple");
        transaction.setQuantity(300);
        storage.put("banana", 1000);
        storage.put("apple", 200);
        Assertions.assertDoesNotThrow(() -> handler.apply(storage, transaction));
        Assertions.assertEquals(500, storage.get("apple"));
        Assertions.assertEquals(1000, storage.get("banana"));
    }

    @Test
    public void apply_addsFruitDoesNotExist_ok() {
        FruitTransaction transaction = new FruitTransaction();
        transaction.setFruit("apple");
        transaction.setQuantity(300);
        storage.put("banana", 1000);
        Assertions.assertDoesNotThrow(() -> handler.apply(storage, transaction));
        Assertions.assertEquals(1000, storage.get("banana"));
        Assertions.assertEquals(300, storage.get("apple"));
    }
}
