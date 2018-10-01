package ru.zippospb.restvote.web.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import ru.zippospb.restvote.model.User;
import ru.zippospb.restvote.service.UserService;

import java.util.List;

import static ru.zippospb.restvote.util.ValidationUtil.*;

public class AbstractUserController {
    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private UserService repository;

//    private boolean modificationRestriction;

    public List<User> getAll() {
        log.info("getAll");
        return repository.getAll();
    }

    public User get(int id) {
        log.info("getUserVote {}", id);
        return repository.get(id);
    }

    public User create(User user) {
        log.info("create {}", user);
        checkNew(user);
        return repository.create(user);
    }

    public void delete(int id) {
        log.info("delete {}", id);
        repository.delete(id);
    }

    public void update(User user, int id) {
        log.info("update {} with id={}", user, id);
        assureIdConsistent(user, id);
        repository.update(user);
    }

    public User getByMail(String email) {
        log.info("getByEmail {}", email);
        return repository.getByEmail(email);
    }
}
