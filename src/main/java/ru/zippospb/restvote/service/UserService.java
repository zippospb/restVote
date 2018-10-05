package ru.zippospb.restvote.service;

import ru.zippospb.restvote.model.User;
import ru.zippospb.restvote.to.UserTo;

import java.util.List;

public interface UserService {
    List<User> getAll();

    User get(int id);

    User create(User user);

    void update(User user);

    void update(UserTo userTo);

    void delete(int id);

    User getByEmail(String email);
}
