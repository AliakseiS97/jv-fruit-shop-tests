package core.basesyntax.test.service.impl;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.impl.OperationStrategyImpl;
import core.basesyntax.service.strategy.OperationHandler;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class OperationStrategyImplTest {

    @Test
    public void operationStrategyImpl_constructorIsNull_nok() {
        IllegalArgumentException exception =
                Assertions.assertThrows(IllegalArgumentException.class,
                        () -> new OperationStrategyImpl(null));
        Assertions.assertEquals("map is null", exception.getMessage());
    }

    @Test
    public void operationStrategyImpl_constructorIsValid_ok() {
        OperationHandler handler = (storage, transaction) -> {};
        Map<FruitTransaction.Operation, OperationHandler> map =
                Map.of(FruitTransaction.Operation.SUPPLY, handler);
        OperationStrategyImpl operationStrategy = new OperationStrategyImpl(map);
        Assertions.assertNotNull(operationStrategy);
    }

    @Test
    public void getHandler_operationIsNull_nok() {
        Map<FruitTransaction.Operation, OperationHandler> map = new HashMap<>();
        OperationStrategyImpl operationStrategy = new OperationStrategyImpl(map);
        IllegalArgumentException exception =
                Assertions.assertThrows(IllegalArgumentException.class,
                        () -> operationStrategy.getHandler(null));
        Assertions.assertEquals("operation is null", exception.getMessage());
    }

    @Test
    public void getHandler_operationIsValid_ok() {
        OperationHandler handler = (storage, transaction) -> {};
        Map<FruitTransaction.Operation, OperationHandler> map =
                Map.of(FruitTransaction.Operation.SUPPLY, handler);
        OperationStrategyImpl operationStrategy = new OperationStrategyImpl(map);
        Assertions.assertEquals(handler,
                operationStrategy.getHandler(FruitTransaction.Operation.SUPPLY));
    }

    @Test
    public void getHandler_handlerNotFound_nok() {
        Map<FruitTransaction.Operation, OperationHandler> map = new HashMap<>();
        OperationStrategyImpl operationStrategy = new OperationStrategyImpl(map);
        RuntimeException exception =
                Assertions.assertThrows(RuntimeException.class,
                        () -> operationStrategy.getHandler(FruitTransaction.Operation.SUPPLY));
        Assertions.assertEquals("Operation: "
                + FruitTransaction.Operation.SUPPLY
                + " not found", exception.getMessage());
    }

    @Test
    public void getHandler_handlerIsValid_ok() {
        OperationHandler handler = (storage, transaction) -> {};
        Map<FruitTransaction.Operation, OperationHandler> map =
                Map.of(FruitTransaction.Operation.SUPPLY, handler);
        OperationStrategyImpl operationStrategy = new OperationStrategyImpl(map);
        Assertions.assertEquals(handler,
                operationStrategy.getHandler(FruitTransaction.Operation.SUPPLY));
    }

    @Test
    public void getMap_mapIsUnmodifiable_nok() {
        Map<FruitTransaction.Operation, OperationHandler> map = new HashMap<>();
        OperationStrategyImpl operationStrategy = new OperationStrategyImpl(map);
        Map<FruitTransaction.Operation, OperationHandler> coppyOfmap = operationStrategy.getMap();
        Assertions.assertThrows(UnsupportedOperationException.class,
                () -> coppyOfmap.put(FruitTransaction.Operation.BALANCE,
                        (storage, fruitTransaction) -> {}));
    }
}
