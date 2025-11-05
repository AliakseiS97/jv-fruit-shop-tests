package core.basesyntax.test.service.strategy;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.strategy.PurchaseHandler;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PurchaseHandlerTest {
    private PurchaseHandler purchaseHandler;
    private Storage storage;

    @BeforeEach
    public void setup() {
        storage = new Storage();
        purchaseHandler = new PurchaseHandler();
    }

    @Test
    public void apply_storageIsNull_nok() {
        NullPointerException exception =
                Assertions.assertThrows(NullPointerException.class,
                        () -> purchaseHandler.apply(null, new FruitTransaction()));
        Assertions.assertEquals("Storage is null", exception.getMessage());
    }

    @Test
    public void apply_transactionIsNull_nok() {
        NullPointerException exception =
                Assertions.assertThrows(NullPointerException.class,
                        () -> purchaseHandler.apply(storage, null));
        Assertions.assertEquals("Transaction is null", exception.getMessage());
    }

    @Test
    public void apply_quantityIsNegative_nok() {
        FruitTransaction transaction = new FruitTransaction();
        transaction.setFruit("banana");
        transaction.setQuantity(50);
        storage.put("banana", 10);
        ArithmeticException exception =
                Assertions.assertThrows(ArithmeticException.class,
                        () -> purchaseHandler.apply(storage, transaction));
        Assertions.assertEquals("Not enough "
                        + transaction.getFruit()
                        + " in the store."
                        + " Available: "
                        + storage.get(transaction.getFruit())
                        + ", requested: " + transaction.getQuantity(),
                        exception.getMessage());
    }

    @Test
    public void apply_quantityIsPositive_ok() {
        FruitTransaction transaction = new FruitTransaction();
        transaction.setFruit("banana");
        transaction.setQuantity(50);
        storage.put("banana", 100);
        Assertions.assertDoesNotThrow(() -> purchaseHandler.apply(storage, transaction));
        Assertions.assertEquals(50, storage.get("banana"));
    }

    @Test
    public void apply_quantityBecomesZero_ok() {
        FruitTransaction transaction = new FruitTransaction();
        transaction.setFruit("banana");
        transaction.setQuantity(50);
        storage.put("banana", 50);
        Assertions.assertDoesNotThrow(() -> purchaseHandler.apply(storage, transaction));
        Assertions.assertEquals(0, storage.get("banana"));
    }
}
