package core.basesyntax.service.impl;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.ShopService;
import core.basesyntax.service.strategy.OperationHandler;
import core.basesyntax.service.strategy.OperationStrategy;
import java.util.List;

public class ShopServiceImpl implements ShopService {
    private final OperationStrategy operationStrategy;
    private final Storage storage;

    public ShopServiceImpl(OperationStrategy operationStrategy, Storage storage) {
        if (operationStrategy == null) {
            throw new NullPointerException("operationStrategy is null");
        }
        if (storage == null) {
            throw new NullPointerException("storage is null");
        }
        this.operationStrategy = operationStrategy;
        this.storage = storage;
    }

    @Override
    public void process(List<FruitTransaction> transactions) {
        if (transactions == null || transactions.isEmpty()) {
            throw new IllegalArgumentException("transactions is null or empty");
        }
        for (FruitTransaction transaction : transactions) {
            FruitTransaction.Operation operation = transaction.getOperation();
            OperationHandler handler = operationStrategy.getHandler(operation);
            handler.apply(storage, transaction);
        }
    }
}
