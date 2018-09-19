package ru.zippospb.restvote.repository.datajpa;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.zippospb.restvote.model.Role;
import ru.zippospb.restvote.model.User;
import ru.zippospb.restvote.repository.AbstractRepositoryTest;
import ru.zippospb.restvote.repository.UserRepository;

import java.util.Collections;
import java.util.List;

import static ru.zippospb.restvote.UserTestData.*;

class UserRepositoryTest extends AbstractRepositoryTest {

    @Autowired
    private UserRepository repository;

    @BeforeEach
    void setUp() throws Exception {
//        cacheManager.getCache("users").clear();
    }

    @Test
    void create() throws Exception {
        User newUser = new User(null, "New", "new@gmail.com", "newPass", Role.ROLE_USER);
        User created = repository.save(new User(newUser));
        newUser.setId(created.getId());
        assertMatch(repository.getAll(), USER1, USER2, ADMIN, newUser);
    }

//    @Test
//    void duplicateMailCreate() throws Exception {
//        assertThrows(DataAccessException.class, () ->
//                repository.save(new User(null, "Duplicate", "user@yandex.ru", "newPass", Role.ROLE_USER)));
//    }

    @Test
    void delete() throws Exception {
        repository.delete(USER1_ID);
        assertMatch(repository.getAll(), USER2, ADMIN);
    }

//    @Test
//    void deletedNotFound() throws Exception {
//        assertThrows(NotFoundException.class, () ->
//                repository.delete(1));
//    }

    @Test
    void get() throws Exception {
        User user = repository.get(ADMIN_ID);
        assertMatch(user, ADMIN);
    }

//    @Test
//    void getNotFound() throws Exception {
//        assertThrows(NotFoundException.class, () ->
//                repository.get(1));
//    }

    @Test
    void getByEmail() throws Exception {
        User user = repository.getByEmail("admin@gmail.com");
        assertMatch(user, ADMIN);
    }

    @Test
    void update() throws Exception {
        User updated = new User(USER1);
        updated.setName("UpdatedName");
        updated.setRoles(Collections.singletonList(Role.ROLE_ADMIN));
        repository.save(new User(updated));
        assertMatch(repository.get(USER1_ID), updated);
    }

    @Test
    void getAll() throws Exception {
        List<User> all = repository.getAll();
        assertMatch(all, USER1, USER2, ADMIN);
    }

//    @Test
//    void enable() {
//        repository.enable(USER_ID, false);
//        assertFalse(repository.get(USER_ID).isEnabled());
//        repository.enable(USER_ID, true);
//        assertTrue(repository.get(USER_ID).isEnabled());
//    }
}
