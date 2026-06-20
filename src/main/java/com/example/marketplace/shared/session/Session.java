package main.java.com.example.marketplace.shared.session;

public final class Session {

    private String loggedUserEmail;
    private String loggedUserStoreCnpj;
    private String loggedUserStoreName;
    private boolean logged;
    private boolean hasStore;
    private String lastStoreViewed;
    private String lastProductViewed;

    public Session() {
        this.loggedUserEmail = null;
        this.loggedUserStoreCnpj = null;
        this.loggedUserStoreName = null;
    }

    public void login(String email) {
        this.loggedUserEmail = email;
        this.logged = true;
        this.hasStore = false;
    }
    public void login(String email, String cnpj, String storeName) {
        this.loggedUserEmail = email;
        this.loggedUserStoreCnpj = cnpj;
        this.loggedUserStoreName = storeName;
        this.logged = true;
        this.hasStore = true;
    }

    public void logout() {
        this.loggedUserEmail = null;
        this.loggedUserStoreCnpj = null;
        this.loggedUserStoreName = null;
        this.logged = false;
        this.hasStore = false;
    }

    public void updateHasStore(String storeName, String cnpj, boolean hasStore) {
        this.loggedUserStoreName = storeName;
        this.loggedUserStoreCnpj = cnpj;
        this.hasStore = hasStore;
    }

    public void updateLastStoreViewed(String storeName) {
        this.lastStoreViewed = storeName;
    }

    public void updateLastProductViewed(String productId) {
        this.lastProductViewed = productId;
    }

    // Getters
    public String getLoggedUserStoreCnpj() {
        return loggedUserStoreCnpj;
    }

    public String getLoggedUserEmail() {
        return loggedUserEmail;
    }

    public String getLoggedUserStoreName() {
        return loggedUserStoreName;
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
    public void setLoggedUserEmail(String loggedUserEmail) {
        this.loggedUserEmail = loggedUserEmail;
    }

    public void setLoggedUserStoreName(String loggedUserStoreName) {
        this.loggedUserStoreName = loggedUserStoreName;
    }

    public void setHasStore(boolean hasStore) {
        this.hasStore = hasStore;
    }
}
