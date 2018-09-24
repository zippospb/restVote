package ru.zippospb.restvote.web.restaurant;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import ru.zippospb.restvote.TestUtil;
import ru.zippospb.restvote.model.Restaurant;
import ru.zippospb.restvote.repository.RestaurantRepository;
import ru.zippospb.restvote.web.AbstractControllerTest;
import ru.zippospb.restvote.web.json.JsonUtil;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.zippospb.restvote.RestaurantTestData.*;
import static ru.zippospb.restvote.TestUtil.readFromJson;
import static ru.zippospb.restvote.TestUtil.userHttpBasic;
import static ru.zippospb.restvote.UserTestData.ADMIN;



class AdminRestaurantRestControllerTest extends AbstractControllerTest {

    private final static String REST_URL = AdminRestaurantRestController.REST_URL + "/";


    @Autowired
    private RestaurantRepository repository;

    @Test
    void testGetAll() throws Exception {
        mockMvc.perform(get(REST_URL)
                .with(userHttpBasic(ADMIN)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(TestUtil.contentJson(REST1, REST2, REST3));
    }

    @Test
    void testGet() throws Exception {
        mockMvc.perform(get(REST_URL + REST1_ID)
                .with(userHttpBasic(ADMIN)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(TestUtil.contentJson(REST1));
    }

    @Test
    void testCreateWithLocation() throws Exception {
        Restaurant expected = getNew();
        ResultActions action = mockMvc.perform(post(REST_URL)
                .with(userHttpBasic(ADMIN))
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(expected)))
                .andDo(print())
                .andExpect(status().isCreated());

        Restaurant returned = readFromJson(action, Restaurant.class);
        expected.setId(returned.getId());

        assertMatch(returned, expected);
        assertMatch(repository.getAll(), REST1, REST2, REST3, expected);
    }

    @Test
    void testUpdate() throws Exception {
        Restaurant updated = new Restaurant(REST1);
        updated.setName("измененный ресторан");
        updated.getDishes().remove(0);

        mockMvc.perform(put(REST_URL + REST1_ID)
                .with(userHttpBasic(ADMIN))
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated)))
                .andDo(print())
                .andExpect(status().isNoContent());

        assertMatch(repository.get(updated.getId()), updated);
    }

    @Test
    void testDelete() throws Exception {
        mockMvc.perform(delete(REST_URL + REST1_ID)
                .with(userHttpBasic(ADMIN)))
                .andDo(print())
                .andExpect(status().isNoContent());

        assertMatch(repository.getAll(), REST2, REST3);
    }

    @Test
    void testGetUnAuth() throws Exception {
        mockMvc.perform(get(REST_URL))
                .andExpect(status().isUnauthorized());
    }
}