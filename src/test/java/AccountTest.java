import org.example.Account;
import org.junit.jupiter.api.Test;

import java.time.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class AccountTest {

    @Test
    void shouldPrintEmptyStatement() {
        Account account = new Account(fixedClock(2026, 5, 13));

        String expected =
                String.format("%-12s %8s %8s%n", "Date", "Amount", "Balance");

        assertEquals(expected, account.printStatement());
    }

    @Test
    void shouldPrintStatementWithOneDeposit() {
        Account account = new Account(fixedClock(2026, 5, 13));
        account.deposit(500);

        String expected =
                String.format("%-12s %8s %8s%n", "Date", "Amount", "Balance") +
                        String.format("%-12s %+8d %8d%n", "13.05.2026", 500, 500);

        assertEquals(expected, account.printStatement());
    }

    @Test
    void shouldPrintStatementWithOneWithdrawal() {
        Account account = new Account(fixedClock(2026, 5, 13));
        account.withdraw(200);

        String expected =
                String.format("%-12s %8s %8s%n", "Date", "Amount", "Balance") +
                        String.format("%-12s %+8d %8d%n", "13.05.2026", -200, -200);

        assertEquals(expected, account.printStatement());
    }

    @Test
    void shouldPrintStatementInReverseChronologicalOrderWithRunningBalance() {
        MutableClock clock = new MutableClock(LocalDate.of(2026, 5, 13), ZoneId.systemDefault());
        Account account = new Account(clock);

        account.deposit(1000);

        clock.setDate(LocalDate.of(2026, 5, 14));
        account.withdraw(300);

        String expected =
                String.format("%-12s %8s %8s%n", "Date", "Amount", "Balance") +
                        String.format("%-12s %+8d %8d%n", "14.05.2026", -300, 700) +
                        String.format("%-12s %+8d %8d%n", "13.05.2026", 1000, 1000);

        assertEquals(expected, account.printStatement());
    }

    @Test
    void shouldThrowExceptionWhenDepositAmountIsInvalid() {
        Account account = new Account(fixedClock(2026, 5, 13));

        assertThrows(IllegalArgumentException.class, () -> account.deposit(0));
        assertThrows(IllegalArgumentException.class, () -> account.deposit(-10));
    }

    @Test
    void shouldThrowExceptionWhenWithdrawAmountIsInvalid() {
        Account account = new Account(fixedClock(2026, 5, 13));

        assertThrows(IllegalArgumentException.class, () -> account.withdraw(0));
        assertThrows(IllegalArgumentException.class, () -> account.withdraw(-10));
    }

    private Clock fixedClock(int year, int month, int day) {
        return Clock.fixed(
                LocalDate.of(year, month, day).atStartOfDay(ZoneId.systemDefault()).toInstant(),
                ZoneId.systemDefault()
        );
    }

    static class MutableClock extends Clock {
        private Instant instant;
        private final ZoneId zone;

        MutableClock(LocalDate date, ZoneId zone) {
            this.zone = zone;
            this.instant = date.atStartOfDay(zone).toInstant();
        }

        void setDate(LocalDate date) {
            this.instant = date.atStartOfDay(zone).toInstant();
        }

        @Override
        public ZoneId getZone() {
            return zone;
        }

        @Override
        public Clock withZone(ZoneId zone) {
            return new MutableClock(LocalDate.ofInstant(instant, zone), zone);
        }

        @Override
        public Instant instant() {
            return instant;
        }
    }
}