package main.java.com.example.marketplace.shared.utils;

public final class Normalizer {

    public static String normalizeName(String name) {

        name = java.text.Normalizer.normalize(name, java.text.Normalizer.Form.NFD);
        return name.replaceAll("\\p{InCombiningDiacriticalMarks}", "")
                .replaceAll("\\s+", " ").toUpperCase();
    }
}
