package core.basesyntax.main;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.ReaderService;
import core.basesyntax.service.ShopService;
import core.basesyntax.service.TransactionParserService;
import core.basesyntax.service.TransactionReaderService;
import core.basesyntax.service.WriterService;
import core.basesyntax.service.impl.OperationStrategyImpl;
import core.basesyntax.service.impl.ReaderServiceImpl;
import core.basesyntax.service.impl.ShopServiceImpl;
import core.basesyntax.service.impl.TransactionParserImpl;
import core.basesyntax.service.impl.WriterServiceImpl;
import core.basesyntax.service.strategy.BalanceHandler;
import core.basesyntax.service.strategy.OperationHandler;
import core.basesyntax.service.strategy.PurchaseHandler;
import core.basesyntax.service.strategy.ReturnHandler;
import core.basesyntax.service.strategy.SupplyHandler;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    private static final String PATH_TO_READ = "src/main/resources/reportToRead.csv";
    private static final String PATH_TO_WRITE = "src/main/resources/reportToWrite.csv";

    public static void main(String[] args) {
        ReaderService readerService = new ReaderServiceImpl();
        TransactionReaderService transactionReaderService =
                new TransactionReaderService(readerService);
        List<String> lines = transactionReaderService.readTransactions(PATH_TO_READ);

        TransactionParserService transactionParserService =
                new TransactionParserService(new TransactionParserImpl());

        Map<FruitTransaction.Operation, OperationHandler> storageStrategy = new HashMap<>();
        storageStrategy.put(FruitTransaction.Operation.BALANCE, new BalanceHandler());
        storageStrategy.put(FruitTransaction.Operation.SUPPLY, new SupplyHandler());
        storageStrategy.put(FruitTransaction.Operation.PURCHASE, new PurchaseHandler());
        storageStrategy.put(FruitTransaction.Operation.RETURN, new ReturnHandler());

        OperationStrategyImpl operationStrategy = new OperationStrategyImpl(storageStrategy);
        List<FruitTransaction> transactions = transactionParserService.parseTransactions(lines);
        Storage storage = new Storage();
        ShopService shopService = new ShopServiceImpl(operationStrategy, storage);
        shopService.process(transactions);

        WriterService writerService = new WriterServiceImpl();
        writerService.write(storage.getStorage(), PATH_TO_WRITE);
    }
}
