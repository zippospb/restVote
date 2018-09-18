package ru.zippospb.restvote;

import org.springframework.test.web.servlet.ResultMatcher;
import ru.zippospb.restvote.model.Role;
import ru.zippospb.restvote.model.User;
//import ru.zippospb.restvote.web.json.JsonUtil;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static ru.zippospb.restvote.model.AbstractBaseEntity.START_SEQ;
import static ru.zippospb.restvote.web.json.JsonUtil.writeIgnoreProps;
//import static ru.zippospb.restvote.web.json.JsonUtil.writeIgnoreProps;

public class UserTestData {
    public static final int USER1_ID = START_SEQ;
    public static final int ADMIN_ID = START_SEQ + 2;
    public static final String ADMIN_EMAIL = "admin@gmail.com";

    public static final User USER1 = new User(USER1_ID, "User1", "user@mail.ru", "qwert", Role.ROLE_USER);
    public static final User USER2 = new User(USER1_ID + 1, "User2", "user2@mail.ru", "asdfg", Role.ROLE_USER);
    public static final User ADMIN = new User(ADMIN_ID, "Admin", ADMIN_EMAIL, "lkjh33498yhnbyfgi563", Role.ROLE_ADMIN, Role.ROLE_USER);

    public static User getNew(){
        return new User(null, "newName", "newemail@ya.ru", "newPassword", Role.ROLE_USER);
    }

    public static void assertMatch(User actual, User expected) {
        assertThat(actual).isEqualToIgnoringGivenFields(expected,"votes", "password");
    }

    public static void assertMatch(Iterable<User> actual, User... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    public static void assertMatch(Iterable<User> actual, Iterable<User> expected) {
        assertThat(actual).usingElementComparatorIgnoringFields("votes", "password").isEqualTo(expected);
    }

    public static ResultMatcher contentJson(User... expected) {
        return content().json(writeIgnoreProps(Arrays.asList(expected), "votes", "password"));
    }

    public static ResultMatcher contentJson(User expected) {
        return content().json(writeIgnoreProps(expected, "password"));
    }
//
//    public static String jsonWithPassword(User user, String passw) {
//        return JsonUtil.writeAdditionProps(user, "password", passw);
//    }
}