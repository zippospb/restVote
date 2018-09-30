package ru.zippospb.restvote.repository.datajpa;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import ru.zippospb.restvote.model.Role;
import ru.zippospb.restvote.model.User;
import ru.zippospb.restvote.repository.AbstractRepositoryTest;
import ru.zippospb.restvote.repository.UserRepository;
import ru.zippospb.restvote.util.ValidationUtil;

import javax.validation.ConstraintViolationException;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static ru.zippospb.restvote.UserTestData.*;

class DataJpaUserRepositoryImplTest extends AbstractRepositoryTest {

    @Autowired
    private UserRepository repository;

    @BeforeEach
    void setUp() {
//        cacheManager.getCache("users").clear();
    }

    @Test
    void testGetAll() {
        List<User> all = repository.getAll();
        assertMatch(all, USER1, USER2, ADMIN);
    }

    @Test
    void testGet() {
        User user = repository.get(ADMIN_ID);
        assertMatch(user, ADMIN);
    }

    @Test
    void testGetByEmail() {
        User user = repository.getByEmail("admin@gmail.com");
        assertMatch(user, ADMIN);
    }

    @Test
    void testDelete() {
        repository.delete(USER1_ID);
        assertMatch(repository.getAll(), USER2, ADMIN);
    }

    @Test
    void testCreate() {
        User newUser = new User(null, "New", "new@gmail.com", "newPass", Role.ROLE_USER);
        User created = repository.save(new User(newUser));
        newUser.setId(created.getId());
        assertMatch(repository.getAll(), USER1, USER2, ADMIN, newUser);
    }

    @Test
    void testCreateInvalid() {
        final User newUser = new User(null, "", "new@gmail.com", "newPass", Role.ROLE_USER);
        assertThrows(ConstraintViolationException.class, () -> {
            try {
                repository.save(newUser);
            } catch (Exception e) {
                throw ValidationUtil.getRootCause(e);
            }
        });
    }

    @Test
    void testCreateDuplicate() {
        assertThrows(DataAccessException.class, () ->
                repository.save(new User(null, "Duplicate", USER1.getEmail(), "newPass", Role.ROLE_USER)));
    }

    @Test
    void testUpdate() {
        User updated = new User(USER1);
        updated.setName("UpdatedName");
        updated.setRoles(Collections.singletonList(Role.ROLE_ADMIN));
        repository.save(new User(updated));
        assertMatch(repository.get(USER1_ID), updated);
    }

    @Test
    void testUpdateInvalid() {
        User updated = new User(USER1);
        updated.setName("");
        assertThrows(ConstraintViolationException.class, () -> {
            try {
                repository.save(updated);
            } catch (Exception e) {
                throw ValidationUtil.getRootCause(e);
            }
        });
    }

    @Test
    void testUpdateDuplicate() {
        User updated = new User(USER1);
        updated.setEmail(USER2.getEmail());
        assertThrows(DataAccessException.class, () -> repository.save(updated));
    }

//    @Test
//    void enable() {
//        repository.enable(USER_ID, false);
//        assertFalse(repository.get(USER_ID).isEnabled());
//        repository.enable(USER_ID, true);
//        assertTrue(repository.get(USER_ID).isEnabled());
//    }
}
