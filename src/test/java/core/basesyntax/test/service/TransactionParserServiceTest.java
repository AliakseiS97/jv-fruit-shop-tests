package core.basesyntax.test.service;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.TransactionParser;
import core.basesyntax.service.TransactionParserService;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TransactionParserServiceTest {

    @Test
    public void transactionParserService_transactionParserIsNotNull_ok() {
        TransactionParser transactionParser = lines -> null;
        TransactionParserService service = new TransactionParserService(transactionParser);
        Assertions.assertNotNull(service);
    }

    @Test
    public void transactionParserService_transactionParserIsNull_ok() {
        NullPointerException exception =
                Assertions.assertThrows(NullPointerException.class,
                        () -> new TransactionParserService(null));
        Assertions.assertEquals("transactionParser is null", exception.getMessage());
    }

    @Test
    public void parseTransaction_validLinesBanana_ok() {
        TransactionParser transactionParser = lines -> {
            FruitTransaction transaction = new FruitTransaction();
            transaction.setOperation(FruitTransaction.Operation.BALANCE);
            transaction.setFruit("banana");
            transaction.setQuantity(1);
            return transaction;
        };
        TransactionParserService service = new TransactionParserService(transactionParser);
        List<String> lines = List.of("b,banana,1");
        List<FruitTransaction> transactions = service.parseTransactions(lines);
        Assertions.assertEquals(1, transactions.size());
        Assertions.assertEquals("banana", transactions.get(0).getFruit());
        Assertions.assertEquals(1, transactions.get(0).getQuantity());
        Assertions.assertEquals(FruitTransaction.Operation.BALANCE,
                transactions.get(0).getOperation());
    }

    @Test
    public void parseTransaction_validLinesAppleAndOperationSupply_ok() {
        TransactionParser transactionParser = lines -> {
            FruitTransaction transaction = new FruitTransaction();
            transaction.setOperation(FruitTransaction.Operation.SUPPLY);
            transaction.setFruit("apple");
            transaction.setQuantity(170);
            return transaction;
        };
        TransactionParserService service = new TransactionParserService(transactionParser);
        List<String> lines = List.of("s,apple,170");
        List<FruitTransaction> transactions = service.parseTransactions(lines);
        Assertions.assertEquals(1, transactions.size());
        Assertions.assertEquals("apple", transactions.get(0).getFruit());
        Assertions.assertEquals(170, transactions.get(0).getQuantity());
        Assertions.assertEquals(FruitTransaction.Operation.SUPPLY,
                transactions.get(0).getOperation());
    }

    @Test
    public void parseTransaction_validLinesAppleAndOperationReturn_ok() {
        TransactionParser transactionParser = lines -> {
            FruitTransaction transaction = new FruitTransaction();
            transaction.setOperation(FruitTransaction.Operation.RETURN);
            transaction.setFruit("apple");
            transaction.setQuantity(50000);
            return transaction;
        };
        TransactionParserService service = new TransactionParserService(transactionParser);
        List<String> lines = List.of("s,apple,50000");
        List<FruitTransaction> transactions = service.parseTransactions(lines);
        Assertions.assertEquals(1, transactions.size());
        Assertions.assertEquals("apple", transactions.get(0).getFruit());
        Assertions.assertEquals(50000, transactions.get(0).getQuantity());
        Assertions.assertEquals(FruitTransaction.Operation.RETURN,
                transactions.get(0).getOperation());
    }

    @Test
    public void parseTransaction_nullParser_throwsException() {
        TransactionParser transactionParser = lines -> null;
        TransactionParserService service = new TransactionParserService(transactionParser);
        NullPointerException exception =
                Assertions.assertThrows(NullPointerException.class,
                        () -> service.parseTransactions(null));
        Assertions.assertEquals("lines is null", exception.getMessage());
    }

    @Test
    public void parseTransaction_emptyLines_skipped() {
        TransactionParser transactionParser = lines -> {
            FruitTransaction transaction = new FruitTransaction();
            transaction.setOperation(FruitTransaction.Operation.RETURN);
            transaction.setFruit("banana");
            transaction.setQuantity(15);
            return transaction;
        };
        TransactionParserService service = new TransactionParserService(transactionParser);
        List<String> lines = List.of("b,banana,15", "        ", "");
        List<FruitTransaction> transactions = service.parseTransactions(lines);
        Assertions.assertEquals(1, transactions.size());
    }

    @Test
    public void parseTransaction_emptyLinesAndContainsHeading_skipped() {
        TransactionParser transactionParser = lines -> {
            FruitTransaction transaction = new FruitTransaction();
            transaction.setOperation(FruitTransaction.Operation.PURCHASE);
            transaction.setFruit("apple");
            transaction.setQuantity(125);
            return transaction;
        };
        TransactionParserService service = new TransactionParserService(transactionParser);
        List<String> lines = List.of("", "   ", "type,fruit,quantity");
        List<FruitTransaction> transactions = service.parseTransactions(lines);
        Assertions.assertEquals(0, transactions.size());
    }
}
