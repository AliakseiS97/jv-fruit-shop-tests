package core.basesyntax.service.strategy;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;

public class BalanceHandler implements OperationHandler {
    @Override
    public void apply(Storage storage, FruitTransaction transaction) {
        if (storage == null) {
            throw new NullPointerException("Storage is null");
        }
        if (transaction == null) {
            throw new NullPointerException("Transaction is null");
        }
        storage.put(transaction.getFruit(), transaction.getQuantity());
    }
}
