package core.basesyntax.test.service;

import core.basesyntax.service.ReaderService;
import core.basesyntax.service.TransactionReaderService;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TransactionReaderServiceTest {

    @Test
    public void transactionReaderService_readerServiceIsNull_nok() {
        NullPointerException exception =
                Assertions.assertThrows(NullPointerException.class,
                        () -> new TransactionReaderService(null));
        Assertions.assertEquals("readerService is null", exception.getMessage());
    }

    @Test
    public void transactionReaderService_readerServiceIsValid_ok() {
        ReaderService readerService = path -> List.of("b,apple,235");
        TransactionReaderService service =
                new TransactionReaderService(readerService);
        List<String> result = service.readTransactions("anyPath.csv");
        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals("b,apple,235", result.get(0));
    }

    @Test
    public void readTransactions_validPath_ok() {
        List<String> paths = List.of("b,apple,235");
        Assertions.assertEquals(1, paths.size());
    }

    @Test
    public void readTransactions_pathIsNull_nok() {
        TransactionReaderService service = new TransactionReaderService(path -> List.of());
        NullPointerException exception =
                Assertions.assertThrows(NullPointerException.class,
                        () -> service.readTransactions(null));
        Assertions.assertEquals("path is null", exception.getMessage());
    }
}
