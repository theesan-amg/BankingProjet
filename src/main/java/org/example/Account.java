package org.example;

import java.time.Clock;

public class Account {

    public Account(Clock clock) {
    }

    public void deposit(int amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Deposit amount must be positive");
        }
    }

    public String printStatement() {
        return String.format("%-12s %-8s %-8s%n", "Date", "Amount", "Balance");
    }
}