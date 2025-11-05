package core.basesyntax.test.service.strategy;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.strategy.BalanceHandler;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BalanceHandlerTest {
    private BalanceHandler handler;
    private Storage storage;

    @BeforeEach
    public void setUp() {
        storage = new Storage();
        handler = new BalanceHandler();
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
    public void apply_setsInitialBalance_ok() {
        FruitTransaction transaction = new FruitTransaction();
        transaction.setFruit("apple");
        transaction.setQuantity(10);
        handler.apply(storage, transaction);
        Assertions.assertEquals(10, storage.get("apple"));
    }

    @Test public void apply_overwritesExistingBalance_ok() {
        FruitTransaction transaction = new FruitTransaction();
        storage.put("apple", 5);
        transaction.setFruit("apple");
        transaction.setQuantity(10);
        handler.apply(storage, transaction);
        Assertions.assertEquals(10, storage.get("apple"));
    }

    @Test
    public void apply_putNewFruit_ok() {
        FruitTransaction bananaTransaction = new FruitTransaction();
        bananaTransaction.setFruit("banana");
        bananaTransaction.setQuantity(25);
        FruitTransaction appleTransaction = new FruitTransaction();
        appleTransaction.setFruit("apple");
        appleTransaction.setQuantity(50);
        handler.apply(storage, bananaTransaction);
        handler.apply(storage, appleTransaction);
        Assertions.assertEquals(25, storage.get("banana"));
        Assertions.assertEquals(50, storage.get("apple"));
    }
}
