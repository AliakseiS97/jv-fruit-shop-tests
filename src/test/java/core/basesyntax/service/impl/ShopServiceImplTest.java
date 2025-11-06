package core.basesyntax.service.impl;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.strategy.OperationStrategy;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ShopServiceImplTest {

    @Test
    public void shopServiceImpl_operationStrategiIsNull_nok() {
        NullPointerException exception =
                Assertions.assertThrows(NullPointerException.class,
                        () -> new ShopServiceImpl(null, new Storage()));
        Assertions.assertEquals("operationStrategy is null", exception.getMessage());
    }

    @Test
    public void shopServiceImpl_storageIsNull_nok() {
        OperationStrategy operationStrategy = operation -> null;
        NullPointerException exception =
                Assertions.assertThrows(NullPointerException.class,
                        () -> new ShopServiceImpl(operationStrategy, null));
        Assertions.assertEquals("storage is null", exception.getMessage());
    }

    @Test
    public void process_transactionIsNull_nok() {
        OperationStrategy operationStrategy = operation -> null;
        Storage storage = new Storage();
        ShopServiceImpl shopService = new ShopServiceImpl(operationStrategy, storage);
        IllegalArgumentException exception =
                Assertions.assertThrows(IllegalArgumentException.class,
                        () -> shopService.process(null));
        Assertions.assertEquals("transactions is null or empty", exception.getMessage());
    }

    @Test
    public void process_transactionIsEmpty_nok() {
        OperationStrategy operationStrategy = operation -> null;
        Storage storage = new Storage();
        ShopServiceImpl shopService = new ShopServiceImpl(operationStrategy, storage);
        IllegalArgumentException exception =
                Assertions.assertThrows(IllegalArgumentException.class,
                        () -> shopService.process(new ArrayList<>()));
        Assertions.assertEquals("transactions is null or empty", exception.getMessage());
    }

    @Test
    public void shopServiceImpl_constructorIsValid_ok() {
        OperationStrategy operationStrategy = operation -> null;
        Storage storage = new Storage();
        ShopServiceImpl shopService = new ShopServiceImpl(operationStrategy, storage);
        Assertions.assertNotNull(shopService);
    }

    @Test
    public void process_transactionIsValid_ok() {
        FruitTransaction transaction = new FruitTransaction();
        transaction.setFruit("apple");
        transaction.setQuantity(24);
        transaction.setOperation(FruitTransaction.Operation.BALANCE);
        List<FruitTransaction> transactions = new ArrayList<>();
        transactions.add(transaction);
        Storage storage = new Storage();
        OperationStrategy operationStrategy = operation -> (s, t) -> {};
        ShopServiceImpl shopService = new ShopServiceImpl(operationStrategy, storage);
        Assertions.assertDoesNotThrow(() -> shopService.process(transactions));
    }
}
