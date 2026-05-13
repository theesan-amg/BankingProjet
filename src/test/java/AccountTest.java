import org.example.Account;
import org.junit.jupiter.api.Test;

import java.time.Clock;
import java.time.LocalDate;
import java.time.ZoneId;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class AccountTest {

    @Test
    void shouldPrintStatementWithOneDeposit() {
        Clock clock = Clock.fixed(
                LocalDate.of(2026, 5, 13).atStartOfDay(ZoneId.systemDefault()).toInstant(),
                ZoneId.systemDefault()
        );

        Account account = new Account(clock);
        account.deposit(500);

        String expected =
                String.format("%-12s %-8s %-8s%n", "Date", "Amount", "Balance") +
                        String.format("%-12s %+8d %8d%n", "13.05.2026", 500, 500);

        assertEquals(expected, account.printStatement());
    }


    @Test
    void shouldThrowExceptionWhenDepositAmountIsInvalid() {
        Clock clock = Clock.fixed(
                LocalDate.of(2026, 5, 13).atStartOfDay(ZoneId.systemDefault()).toInstant(),
                ZoneId.systemDefault()
        );

        Account account = new Account(clock);

        assertThrows(IllegalArgumentException.class, () -> account.deposit(0));
        assertThrows(IllegalArgumentException.class, () -> account.deposit(-10));
    }


}