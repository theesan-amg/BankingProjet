package org.example;

import java.time.Clock;

public class Main {
    public static void main(String[] args) {
        Account account = new Account(Clock.systemDefaultZone());

        account.deposit(700);
        account.withdraw(400);

        System.out.println(account.printStatement());
    }
}