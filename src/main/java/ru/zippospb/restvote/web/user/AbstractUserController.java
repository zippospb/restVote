package ru.zippospb.restvote.web.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import ru.zippospb.restvote.model.User;
import ru.zippospb.restvote.repository.UserRepository;

import java.util.List;

import static ru.zippospb.restvote.util.ValidationUtil.*;

public class AbstractUserController {
    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private UserRepository repository;

//    private boolean modificationRestriction;

    public List<User> getAll() {
        log.info("getAll");
        return repository.getAll();
    }

    public User get(int id) {
        log.info("get {}", id);
        return checkNotFoundWithId(repository.get(id), id);
    }

    public User create(User user) {
        log.info("create {}", user);
        checkNew(user);
        return repository.save(user);
    }

    public void delete(int id) {
        log.info("delete {}", id);
//        checkModificationAllowed(id);
        checkNotFoundWithId(repository.delete(id), id);
    }

    public void update(User user, int id) {
        log.info("update {} with id={}", user, id);
        assureIdConsistent(user, id);
//        checkModificationAllowed(id);
        checkNotFoundWithId(repository.save(user), user.getId());
    }

    public User getByMail(String email) {
        log.info("getByEmail {}", email);
        return checkNotFound(repository.getByEmail(email), "email=" + email);
    }

//    public void enable(int id, boolean enabled) {
//        log.info(enabled ? "enable {}" : "disable {}", id);
////        checkModificationAllowed(id);
//        repository.enable(id, enabled);
//    }

//    private void checkModificationAllowed(int id) {
//        if (modificationRestriction && id < AbstractBaseEntity.START_SEQ + 2) {
//            throw new ModificationRestrictionException();
//        }
//    }
}
