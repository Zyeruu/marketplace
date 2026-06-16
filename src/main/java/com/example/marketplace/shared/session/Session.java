package main.java.com.example.marketplace.shared.session;

public final class Session {

    private String email;
    private String cnpj;
    private String storeName;
    private boolean logged;
    private boolean hasStore;
    private String lastStoreViewed;
    private String lastProductViewed;

    public Session() {
        this.email = null;
        this.cnpj = null;
        this.storeName = null;
    }

    public void login(String email) {
        this.email = email;
        this.logged = true;
        this.hasStore = false;
    }
    public void login(String email, String cnpj, String storeName) {
        this.email = email;
        this.cnpj = cnpj;
        this.storeName = storeName;
        this.logged = true;
        this.hasStore = true;
    }

    public void logout() {
        this.email = null;
        this.cnpj = null;
        this.storeName = null;
        this.logged = false;
        this.hasStore = false;
    }

    public void updateHasStore(String storeName, String cnpj, boolean hasStore) {
        this.storeName = storeName;
        this.cnpj = cnpj;
        this.hasStore = hasStore;
    }

    public void updateLastStoreViewed(String storeName) {
        this.lastStoreViewed = storeName;
    }

    public void updateLastProductViewed(String productId) {
        this.lastProductViewed = productId;
    }

    // Getters
    public String getCnpj() {
        return cnpj;
    }

    public String getEmail() {
        return email;
    }

    public String getStoreName() {
        return storeName;
    }

    public boolean isLogged() {
        return logged;
    }

    public boolean hasStore() {
        return hasStore;
    }

    public String getLastStoreViewed() {
        return lastStoreViewed;
    }

    public String getLastProductViewed() {
        return lastProductViewed;
    }

    // Setters
    public void setEmail(String email) {
        this.email = email;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public void setHasStore(boolean hasStore) {
        this.hasStore = hasStore;
    }
}
