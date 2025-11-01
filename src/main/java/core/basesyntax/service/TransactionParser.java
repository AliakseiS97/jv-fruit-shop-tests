package core.basesyntax.service;

import core.basesyntax.model.FruitTransaction;

public interface TransactionParser {
    FruitTransaction parse(String lines);
}
