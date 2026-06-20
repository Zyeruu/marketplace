package main.java.com.example.marketplace.user.auth.repository;

import main.java.com.example.marketplace.shared.session.Session;
import main.java.com.example.marketplace.user.auth.dto.AuthRequest;
import main.java.com.example.marketplace.user.model.User;
import main.java.com.example.marketplace.database.DataBase;
import main.java.com.example.marketplace.exceptions.AlreadyExistsException;

public final class AuthRepository {

    private final DataBase dataBase;

    public AuthRepository(DataBase dataBase) {
        this.dataBase = dataBase;
    }

    public boolean existsByEmail(String email) {
        return dataBase.existsUserByEmail(email);
    }

    public void save(User user) {
        dataBase.saveUser(user);
    }

    public User findByEmailAndPassword(AuthRequest request) {
        return dataBase.findUserByEmailAndPassword(request.getEmail(), request.getPassword());
    }
}
