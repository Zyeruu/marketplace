package main.java.com.example.marketplace.user.auth.view;

import main.java.com.example.marketplace.user.auth.dto.AuthRequest;

import java.util.Scanner;

public final class AuthView {

    Scanner scanner = new Scanner(System.in);

    public AuthRequest collectRegistrationData() {

        System.out.println("\n-----------| REGISTER |------------");

        String email = collectEmail();
        String password = collectPassword();
        String name = collectName();

        return new AuthRequest(email, password, name);
    }

    public AuthRequest collectEmailAndPassword() {

        System.out.println("\n-------------| LOGIN |-------------");

        String email = collectEmail();
        String password = collectPassword();

        return new AuthRequest(email, password, null);
    }

    public String collectEmail() {

        System.out.print("E-mail: ");
        System.out.flush();
        return scanner.nextLine().toLowerCase();
    }

    public String collectPassword() {

        System.out.print("Password: ");
        System.out.flush();
        return scanner.nextLine();
    }

    public String collectName() {

        System.out.print("Your name: ");
        System.out.flush();
        return scanner.nextLine();
    }

    public void printMessage(String message) {
        System.out.println("\n" + message + "\n");
    }
}
