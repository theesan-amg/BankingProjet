import org.junit.jupiter.api.Test;

import java.time.Clock;
import java.time.LocalDate;
import java.time.ZoneId;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AccountTest {

    @Test
    void shouldPrintHeaderWhenNoTransaction() {
        Clock clock = Clock.fixed(
                LocalDate.of(2026, 5, 13).atStartOfDay(ZoneId.systemDefault()).toInstant(),
                ZoneId.systemDefault()
        );

        Account account = new Account(clock);

        assertEquals(
                String.format("%-12s %-8s %-8s%n", "Date", "Amount", "Balance"),
                account.printStatement()
        );
    }
}