package main.java.com.example.marketplace.user.auth.repository;

import main.java.com.example.marketplace.shared.session.Session;
import main.java.com.example.marketplace.user.auth.dto.AuthRequest;
import main.java.com.example.marketplace.user.model.User;
import main.java.com.example.marketplace.database.DataBase;
import main.java.com.example.marketplace.exceptions.AlreadyExistsException;
import main.java.com.example.marketplace.exceptions.NotFoundException;

public final class AuthRepository {

    private final DataBase dataBase;
    private final Session session;

    public AuthRepository(DataBase dataBase, Session session) {
        this.dataBase = dataBase;
        this.session = session;
    }

    public void save(User user) {

        if (dataBase.existsUserByEmail(user.getEmail()))
            throw new AlreadyExistsException("[!] E-mail already registered.");

        dataBase.saveUser(user);
    }

    public User login(AuthRequest userRequest) {

        if (!dataBase.existsUserByEmailAndPassword(userRequest.getEmail(), userRequest.getPassword()))
            throw new NotFoundException("[!] Incorrect e-mail or password.");

        return dataBase.findUserByEmail(userRequest.getEmail());
    }
}
