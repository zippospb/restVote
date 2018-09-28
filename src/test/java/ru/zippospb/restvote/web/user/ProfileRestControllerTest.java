package ru.zippospb.restvote.web.user;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import ru.zippospb.restvote.model.User;
import ru.zippospb.restvote.repository.UserRepository;
import ru.zippospb.restvote.web.AbstractControllerTest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.zippospb.restvote.TestUtil.readFromJson;
import static ru.zippospb.restvote.TestUtil.userHttpBasic;
import static ru.zippospb.restvote.UserTestData.*;
import static ru.zippospb.restvote.web.user.ProfileRestController.REST_URL;

class ProfileRestControllerTest extends AbstractControllerTest {
    @Autowired
    UserRepository repository;

    @Test
    void testGet() throws Exception {
        mockMvc.perform(get(REST_URL)
                .with(userHttpBasic(USER1)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(USER1));
    }

    @Test
    void testDelete() throws Exception {
        mockMvc.perform(delete(REST_URL)
                .with(userHttpBasic(USER1)))
                .andDo(print())
                .andExpect(status().isNoContent());

        assertMatch(repository.getAll(), USER2, ADMIN);
    }

    @Test
    void testRegister() throws Exception {
        User created = getNew();

        ResultActions action = mockMvc.perform(post(REST_URL + "/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonWithPassword(created, created.getPassword())))
                .andDo(print())
                .andExpect(status().isCreated());

        User returned = readFromJson(action, User.class);
        created.setId(returned.getId());

        assertMatch(returned, created);
        assertMatch(repository.getByEmail(created.getEmail()), created);
    }

    @Test
    void testUpdate() throws Exception {
        User updated = new User(USER1);
        updated.setEmail("updated@gmail.com");

        ResultActions action = mockMvc.perform(put(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonWithPassword(updated, updated.getPassword()))
                .with(userHttpBasic(USER1)))
                .andDo(print())
                .andExpect(status().isNoContent());

        assertMatch(repository.get(USER1_ID), updated);
    }

    @Test
    void testGetUnAuth() throws Exception {
        mockMvc.perform(get(REST_URL))
                .andExpect(status().isUnauthorized());
    }
}