package main.java.com.example.marketplace.shared.utils;

public final class Normalizer {

    public static String normalizeName(String name) {

        name = java.text.Normalizer.normalize(name, java.text.Normalizer.Form.NFD);
        name = name.replaceAll("\\p{InCombiningDiacriticalMarks}", "");

        String[] words = name.trim().split("\\s+");
        StringBuilder result = new StringBuilder();

        for (String word : words) {
            if (!word.isEmpty()) {
                if (word.length() > 1) {
                    result.append(Character.toUpperCase(word.charAt(0)))
                            .append(word.substring(1).toLowerCase())
                            .append(" ");
                }
            }
        }

        return result.toString().trim();
    }

    public static String normalizeStoreName(String name) {

        name = java.text.Normalizer.normalize(name, java.text.Normalizer.Form.NFD);
        return name.replaceAll("\\p{InCombiningDiacriticalMarks}", "")
                .replaceAll("\\s+", " ");
    }

    public static String normalizeBrandName(String name) {

        name = java.text.Normalizer.normalize(name, java.text.Normalizer.Form.NFD);
        return name.replaceAll("\\p{InCombiningDiacriticalMarks}", "").toUpperCase();
    }
}
