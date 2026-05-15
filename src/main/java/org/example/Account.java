package org.example;

import java.time.Clock;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Account {

    private static final String HEADER_FORMAT = "%-12s %8s %8s%n";
    private static final String LINE_FORMAT = "%-12s %+8d %8d%n";

    private final List<Transaction> transactions = new ArrayList<>();
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    private final Clock clock;

    public Account(Clock clock) {
        this.clock = clock;
    }

    public void deposit(int amount) {
        validatePositiveAmount(amount, "Deposit");
        transactions.add(new Transaction(LocalDate.now(clock), amount));
    }

    public void withdraw(int amount) {
        validatePositiveAmount(amount, "Withdraw");
        transactions.add(new Transaction(LocalDate.now(clock), -amount));
    }

    public String printStatement() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format(HEADER_FORMAT, "Date", "Amount", "Balance"));

        List<StatementLine> lines = new ArrayList<>();
        int balance = 0;

        for (Transaction transaction : transactions) {
            balance += transaction.amount();
            lines.add(new StatementLine(transaction.date(), transaction.amount(), balance));
        }

        for (int i = lines.size() - 1; i >= 0; i--) {
            StatementLine line = lines.get(i);
            sb.append(String.format(
                    LINE_FORMAT,
                    line.date().format(formatter),
                    line.amount(),
                    line.balance()
            ));
        }

        return sb.toString();
    }

    private void validatePositiveAmount(int amount, String operation) {
        if (amount <= 0) {
            throw new IllegalArgumentException(operation + " amount must be positive");
        }
    }

    private record StatementLine(LocalDate date, int amount, int balance) {}
}