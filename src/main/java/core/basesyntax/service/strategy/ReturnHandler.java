package core.basesyntax.service.strategy;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;

public class ReturnHandler implements OperationHandler {
    @Override
    public void apply(Storage storage, FruitTransaction transaction) {
        if (storage == null) {
            throw new NullPointerException("storage is null");
        }
        if (transaction == null) {
            throw new NullPointerException("transaction is null");
        }
        storage.put(transaction.getFruit(),
                storage.get(transaction.getFruit()) + transaction.getQuantity());
    }
}
