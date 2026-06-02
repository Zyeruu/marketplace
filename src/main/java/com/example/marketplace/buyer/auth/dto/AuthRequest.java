package main.java.com.example.marketplace.buyer.auth.dto;

public final class AuthRequest {

    private String email;
    private String password;
    private String name;

    public AuthRequest(String email, String password, String name) {
        this.email = email.toLowerCase();
        this.password = password;
        this.name = name;
    }

    // Getters
    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    // Setters
    public void setName(String name) {
        this.name = name;
    }
}
