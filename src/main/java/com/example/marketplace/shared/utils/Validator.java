package main.java.com.example.marketplace.shared.utils;

import java.util.regex.Pattern;

public final class Validator {

    private static final Pattern EMAIL = Pattern.compile("^[a-zA-Z0-9._-]{5,40}+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{3,}$");
    private static final Pattern PASSWORD = Pattern.compile("^[a-zA-ZÀ-ÿ0-9!-/:-@\\[-`{-~ ]{8,128}$");
    private static final Pattern USER_NAME = Pattern.compile("^(?!.*(.)\\1{2})(?!^[a-zA-Z]\\s)(?!.*\\s[a-zA-Z]\\s)(?!.*\\s[a-zA-Z]$)(?=.*\\s)[a-zA-Z ]{8,50}$");
    private static final Pattern STORE_NAME = Pattern.compile("^(?!.*_{2})(?!(.*_){3})[a-zA-Z_]{5,20}$");
    private static final Pattern PRODUCT_NAME = Pattern.compile("^(?=(.*[a-zA-Z]){3})(?=.{3,50})([a-zA-Z0-9]{2,})(\\s[a-zA-Z0-9]{2,})*$");
    private static final Pattern BRAND_NAME = Pattern.compile("^(?=(.*[A-Z]){2})[A-Z0-9]{3,15}$");
    private static final Pattern MESSAGE = Pattern.compile("^(?=(?:.*[a-zA-ZÀ-ÿ]){3})[a-zA-ZÀ-ÿ0-9 .,!?;:'\"-]{3,250}$");

    public static void isValidEmail(String email) {

        if (!EMAIL.matcher(email).matches())
            throw new IllegalArgumentException("[!] Invalid e-mail format.");
    }

    public static void isValidPassword(String password) {

        if (!PASSWORD.matcher(password).matches())
            throw new IllegalArgumentException("[!] Your password must be between 8 and 128 characters long.");
    }

    public static void isValidUserName(String userName) {

        if (!USER_NAME.matcher(userName).matches())
            throw new IllegalArgumentException("""
                    [!] The name provided has an invalid format.
                    [!] Symbols and numbers are not supported.
                    [!] The name must be between 8 and 50 characters long and include a middle name or last name.
                    [!] No part of the name may be abbreviated to a single letter.""");
    }

    public static void isValidStoreName(String storeName) {

        if (!STORE_NAME.matcher(storeName).matches())
            throw new IllegalArgumentException("""
                    [!] The store name provided has an invalid format.
                    [!] The store name must be between 5 and 20 characters long.
                    [!] Numbers, spaces and symbols are not supported, with the exception of the underscore (_).
                    [!] A maximum of two underscores is allowed, but they cannot be duplicated (ex.: __).""");
    }

    public static void isValidProductName(String productName) {

        if (!PRODUCT_NAME.matcher(productName).matches())
            throw new IllegalArgumentException("""
                    [!] The product name provided has an invalid format.
                    [!] Symbols are not supported, and the name must be between 3 and 50 characters long and contain at least two letters.
                    [!] Each word must be at least 2 characters long.""");
    }

    public static void isValidBrandName(String brandName) {

        if (!BRAND_NAME.matcher(brandName).matches())
            throw new IllegalArgumentException("[!] The brand name provided has an invalid format.\n" +
                    "[!] Symbols and spaces are not supported, and the name must be between 3 and 15 characters long " +
                    "and contain at least 2 letters.");
    }

    public static void isValidMessage(String message) {

        if (!MESSAGE.matcher(message).matches())
            throw new IllegalArgumentException("""
                    [!] Invalid message.
                    [!] The message must be between 3 and 250 characters long, with a minimum of 3 letters.
                    [!] Simple accentuation is allowed.""");
    }
}
