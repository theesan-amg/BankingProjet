package org.example;

import java.time.LocalDate;

public record Transaction(LocalDate date, int amount) {
}

