package core.basesyntax.test.service.impl;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.impl.TransactionParserImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TransactionParserImplTest {

    @Test
    public void transactionParserImpl_lineIsNull_nok() {
        TransactionParserImpl transactionParserImpl = new TransactionParserImpl();
        IllegalArgumentException exception =
                Assertions.assertThrows(IllegalArgumentException.class,
                        () -> transactionParserImpl.parse(null));
        Assertions.assertEquals("line is null or empty", exception.getMessage());
    }

    @Test
    public void transactionParserImpl_lineIsEmpty_nok() {
        TransactionParserImpl transactionParserImpl = new TransactionParserImpl();
        IllegalArgumentException exception =
                Assertions.assertThrows(IllegalArgumentException.class,
                        () -> transactionParserImpl.parse(""));
        Assertions.assertEquals("line is null or empty", exception.getMessage());
    }

    @Test
    public void transactionParserImpl_invalidLineFormat_nok() {
        String line = "b,apple";
        TransactionParserImpl transactionParserImpl = new TransactionParserImpl();
        RuntimeException exception =
                Assertions.assertThrows(RuntimeException.class,
                        () -> transactionParserImpl.parse(line));
        Assertions.assertEquals("Invalid line format: " + line, exception.getMessage());
    }

    @Test
    public void transactionParserImpl_quantiyIsNegative_nok() {
        TransactionParserImpl transactionParserImpl = new TransactionParserImpl();
        int quantity = -50;
        IllegalArgumentException exception =
                Assertions.assertThrows(IllegalArgumentException.class,
                        () -> transactionParserImpl.parse("b,apple,-50"));
        Assertions.assertEquals("Quantity cannot be negative: " + quantity, exception.getMessage());
    }

    @Test
    public void transactionParserImpl_validParser_ok() {
        TransactionParserImpl transactionParserImpl = new TransactionParserImpl();
        Assertions.assertDoesNotThrow(() -> transactionParserImpl.parse("b,apple,200"));
        FruitTransaction transaction = transactionParserImpl.parse("b,apple,200");
        Assertions.assertEquals(FruitTransaction.Operation.BALANCE, transaction.getOperation());
        Assertions.assertEquals("apple", transaction.getFruit());
        Assertions.assertEquals(200, transaction.getQuantity());
    }

}
