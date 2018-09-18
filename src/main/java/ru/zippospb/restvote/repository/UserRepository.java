package ru.zippospb.restvote.repository;

import ru.zippospb.restvote.model.User;

import java.util.List;

public interface UserRepository {
    List<User> getAll();

    User get(int id);

    User save(User user);

    boolean delete(int id);

    User getByEmail(String email);
}
