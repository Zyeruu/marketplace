package main.java.com.example.marketplace.shared.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public final class Formatter {

    public static String formatDateTime(LocalDateTime date) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        return date.format(formatter);
    }

    public static String formatFloat(float value) {
        return String.format("%.2f", value);
    }
}
