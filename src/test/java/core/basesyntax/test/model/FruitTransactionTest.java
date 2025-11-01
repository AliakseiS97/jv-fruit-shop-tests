package core.basesyntax.test.model;

import static core.basesyntax.model.FruitTransaction.Operation.BALANCE;
import static core.basesyntax.model.FruitTransaction.Operation.PURCHASE;
import static core.basesyntax.model.FruitTransaction.Operation.RETURN;
import static core.basesyntax.model.FruitTransaction.Operation.SUPPLY;

import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class FruitTransactionTest {

    @Test
    public void formCode_balance_ok() {
        Assertions.assertEquals(BALANCE, FruitTransaction.Operation.fromCode("b"));
    }

    @Test
    public void formCode_supply_ok() {
        Assertions.assertEquals(SUPPLY, FruitTransaction.Operation.fromCode("s"));
    }

    @Test
    public void formCode_purchase_ok() {
        Assertions.assertEquals(PURCHASE, FruitTransaction.Operation.fromCode("p"));
    }

    @Test
    public void formCode_return_ok() {
        Assertions.assertEquals(RETURN, FruitTransaction.Operation.fromCode("r"));
    }

    @Test
    public void formCode_unknownOperation_nok() {
        String code = "u";
        IllegalArgumentException exception = Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> FruitTransaction.Operation.fromCode(code));
        Assertions.assertEquals("Unknown operation code: " + code, exception.getMessage());
    }

    @Test
    public void formCode_supply_nok() {
        String code = "S";
        IllegalArgumentException exception = Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> FruitTransaction.Operation.fromCode(code));
        Assertions.assertEquals("Unknown operation code: " + code, exception.getMessage());
    }

    @Test
    public void getOperation_ok() {
        FruitTransaction fruitTransaction = new FruitTransaction();
        fruitTransaction.setOperation(SUPPLY);
        Assertions.assertEquals(SUPPLY, fruitTransaction.getOperation());
    }

    @Test
    public void getOperation_unknownOperation_nok() {
        FruitTransaction fruitTransaction = new FruitTransaction();
        Assertions.assertNull(fruitTransaction.getOperation());
    }

    @Test
    public void getFruit_ok() {
        FruitTransaction fruitTransaction = new FruitTransaction();
        fruitTransaction.setFruit("banana");
        Assertions.assertEquals("banana", fruitTransaction.getFruit());
    }

    @Test
    public void getFruit_unknownFruit_nok() {
        FruitTransaction fruitTransaction = new FruitTransaction();
        Assertions.assertNull(fruitTransaction.getFruit());
    }

    @Test
    public void getQuantity_ok() {
        FruitTransaction fruitTransaction = new FruitTransaction();
        fruitTransaction.setQuantity(24);
        Assertions.assertEquals(24, fruitTransaction.getQuantity());
    }

    @Test
    public void getFruit_unknownQuantity_nok() {
        FruitTransaction fruitTransaction = new FruitTransaction();
        Assertions.assertEquals(0, fruitTransaction.getQuantity());
    }
}
