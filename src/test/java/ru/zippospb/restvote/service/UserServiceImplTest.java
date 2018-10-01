package ru.zippospb.restvote.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import ru.zippospb.restvote.model.Role;
import ru.zippospb.restvote.model.User;
import ru.zippospb.restvote.util.ValidationUtil;
import ru.zippospb.restvote.util.exception.NotFoundException;

import javax.validation.ConstraintViolationException;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static ru.zippospb.restvote.UserTestData.*;

class UserServiceImplTest extends AbstractServiceTest {

    @Autowired
    private UserService service;

    @Test
    void testGetAll() {
        List<User> all = service.getAll();
        assertMatch(all, USER1, USER2, ADMIN);
    }

    @Test
    void testGet() {
        User user = service.get(ADMIN_ID);
        assertMatch(user, ADMIN);
    }

    @Test
    void getNotFound() {
        assertThrows(NotFoundException.class, () -> service.get(1));
    }

    @Test
    void testGetByEmail() {
        User user = service.getByEmail("admin@gmail.com");
        assertMatch(user, ADMIN);
    }

    @Test
    void deleteNotFound() {
        assertThrows(NotFoundException.class, () -> service.delete(1));
    }

    @Test
    void testDelete() {
        service.delete(USER1_ID);
        assertMatch(service.getAll(), USER2, ADMIN);
    }

    @Test
    void testCreate() {
        User newUser = new User(null, "New", "new@gmail.com", "newPass", Role.ROLE_USER);
        User created = service.create(new User(newUser));
        newUser.setId(created.getId());
        assertMatch(service.getAll(), USER1, USER2, ADMIN, newUser);
    }

    @Test
    void testCreateDuplicate() {
        assertThrows(DataAccessException.class, () ->
                service.update(new User(null, "Duplicate", USER1.getEmail(), "newPass", Role.ROLE_USER)));
    }

    @Test
    void testUpdate() {
        User updated = new User(USER1);
        updated.setName("UpdatedName");
        updated.setRoles(Collections.singletonList(Role.ROLE_ADMIN));
        service.update(new User(updated));
        assertMatch(service.get(USER1_ID), updated);
    }

    @Test
    void testUpdateInvalid() {
        User updated = new User(USER1);
        updated.setName("");
        assertThrows(ConstraintViolationException.class, () -> {
            try {
                service.update(updated);
            } catch (Exception e) {
                throw ValidationUtil.getRootCause(e);
            }
        });
    }

    @Test
    void testUpdateDuplicate() {
        User updated = new User(USER1);
        updated.setEmail(USER2.getEmail());
        assertThrows(DataAccessException.class, () -> service.update(updated));
    }

    @Test
    void testValidation() {
        validateRootCause(() -> service.create(new User(null, "  ", "invalid@yandex.ru", "password", Role.ROLE_USER)), ConstraintViolationException.class);
        validateRootCause(() -> service.create(new User(null, "User", "  ", "password", Role.ROLE_USER)), ConstraintViolationException.class);
        validateRootCause(() -> service.create(new User(null, "User", "invalid@yandex.ru", "", Role.ROLE_USER)), ConstraintViolationException.class);
    }

//    @Test
//    void enable() {
//        service.enable(USER_ID, false);
//        assertFalse(service.getUserVote(USER_ID).isEnabled());
//        service.enable(USER_ID, true);
//        assertTrue(service.getUserVote(USER_ID).isEnabled());
//    }
}
