package main.java.com.example.marketplace.user.auth.dto;

public final class AuthRequest {

    private final String email;
    private final String password;
    private String name;

    public AuthRequest(String email, String password, String name) {
        this.email = email;
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
