package main.java.com.example.marketplace.shared.utils;

import java.util.regex.Pattern;

public final class Validator {

    private static final Pattern EMAIL = Pattern.compile("^[a-zA-Z0-9._-]{5,40}+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{3,}$");
    private static final Pattern PASSWORD = Pattern.compile("^[a-zA-ZÀ-ÿ0-9!-/:-@\\[-`{-~]{8,128}$");
    private static final Pattern USER_NAME = Pattern.compile("^(?!.* {2})[a-zA-Z ]{5,50}$");
    private static final Pattern STORE_NAME = Pattern.compile("^[a-zA-ZÀ-ÿ0-9]{5,15}$");

    public static void isValidEmail(String email) {
        if (!EMAIL.matcher(email).matches())
            throw new IllegalArgumentException("[!] Invalid email format.");
    }

    public static void isValidPassword(String password) {
        if (!PASSWORD.matcher(password).matches())
            throw new IllegalArgumentException("[!] Your password must be between 8 and 128 characters long.");
    }

    public static void isValidUserName(String userName) {
        if (!USER_NAME.matcher(userName).matches())
            throw new IllegalArgumentException("[!] The name provided has an invalid format. Symbols are not supported.");
    }

    public static void isValidStoreName(String storeName) {
        if (!STORE_NAME.matcher(storeName).matches())
            throw new IllegalArgumentException("[!] The store name provided has an invalid format. Symbols and spaces are not supported.");
    }
}
