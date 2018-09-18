package ru.zippospb.restvote.web.user;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import ru.zippospb.restvote.model.User;
import ru.zippospb.restvote.repository.UserRepository;
import ru.zippospb.restvote.web.AbstractControllerTest;
import ru.zippospb.restvote.web.json.JsonUtil;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.zippospb.restvote.TestUtil.readFromJson;
import static ru.zippospb.restvote.UserTestData.*;

class AdminRestControllerTest extends AbstractControllerTest {

    private final static String REST_URL = AdminRestController.REST_URL + "/";

    @Autowired
    private UserRepository repository;

    @Test
    void testGetAll() throws Exception {
        mockMvc.perform(get(REST_URL))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(USER1, USER2, ADMIN));
    }

    @Test
    void testGet() throws Exception {
        mockMvc.perform(get(REST_URL + USER1_ID))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(USER1));
    }

    @Test
    void testCreateWithLocation() throws Exception {
        User expected = getNew();
        ResultActions action = mockMvc.perform(post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(expected)))
                .andDo(print())
                .andExpect(status().isCreated());

        User returned = readFromJson(action, User.class);
        expected.setId(returned.getId());

        assertMatch(returned, expected);
        assertMatch(repository.getAll(), USER1, USER2, ADMIN, expected);
    }

    @Test
    void testDelete() throws Exception {
        mockMvc.perform(delete(REST_URL + USER1_ID))
                .andDo(print())
                .andExpect(status().isNoContent());

        assertMatch(repository.getAll(), USER2, ADMIN);
    }

    @Test
    void testUpdate() throws Exception {
        User updated = new User(USER1);
        updated.setEmail("updated@gmail.com");

        mockMvc.perform(put(REST_URL + USER1_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated)))
                .andDo(print())
                .andExpect(status().isNoContent());

        assertMatch(repository.getAll(), updated, USER2, ADMIN);
    }

    @Test
    void testGetByMail() throws Exception {
        mockMvc.perform(get(REST_URL + "by?email=" + ADMIN_EMAIL))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(ADMIN));
    }
}