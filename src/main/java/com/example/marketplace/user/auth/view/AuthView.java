package main.java.com.example.marketplace.user.auth.view;

import main.java.com.example.marketplace.user.auth.dto.AuthRequest;

import java.util.Scanner;

public final class AuthView {

    Scanner scanner = new Scanner(System.in);

    public AuthRequest collectRegistrationData() {

        System.out.println("\n------| REGISTER |------");

        System.out.print("E-mail: ");
        System.out.flush();
        String email = scanner.nextLine().toLowerCase();

        System.out.print("Password: ");
        System.out.flush();
        String password = scanner.nextLine();

        System.out.print("Your name: ");
        System.out.flush();
        String name = scanner.nextLine();

        return new AuthRequest(email, password, name);
    }

    public AuthRequest collectEmailAndPassword() {

        System.out.println("\n--------| LOGIN |--------");

        System.out.print("E-mail: ");
        System.out.flush();
        String email = scanner.nextLine();

        System.out.print("Password: ");
        System.out.flush();
        String password = scanner.nextLine();

        return new AuthRequest(email, password, "");
    }

    public void printMessage(String message) {
        System.out.println("\n" + message + "\n");
    }
}
