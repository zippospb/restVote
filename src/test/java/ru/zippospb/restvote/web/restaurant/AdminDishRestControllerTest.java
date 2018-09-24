package ru.zippospb.restvote.web.restaurant;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import ru.zippospb.restvote.TestUtil;
import ru.zippospb.restvote.repository.DishRepository;
import ru.zippospb.restvote.web.AbstractControllerTest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.zippospb.restvote.DishTestData.*;
import static ru.zippospb.restvote.RestaurantTestData.REST1_ID;
import static ru.zippospb.restvote.TestUtil.userHttpBasic;
import static ru.zippospb.restvote.UserTestData.ADMIN;
import static ru.zippospb.restvote.web.restaurant.AdminDishRestController.REST_URL;

class AdminDishRestControllerTest extends AbstractControllerTest {
    private final static String REST1_REST_URL = REST_URL.replaceAll("\\{restId\\}", String.valueOf(REST1_ID)) + "/";

    @Autowired
    private DishRepository repository;

    @Test
    void testGetAll() throws Exception {
        mockMvc.perform(get(REST1_REST_URL)
                .with(userHttpBasic(ADMIN)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(TestUtil.contentJson(REST1_DISHES));
    }

    @Test
    void testGet() throws Exception {
        mockMvc.perform(get(REST1_REST_URL + REST1_DISH1_ID)
                .with(userHttpBasic(ADMIN)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(TestUtil.contentJson(REST1_DISHES.get(0)));
    }

    @Test
    void testCreateWithLocation() {
    }

    @Test
    void testUpdate() {
    }

    @Test
    void testDelete() throws Exception {
        mockMvc.perform(delete(REST1_REST_URL + REST1_DISH1_ID)
                .with(userHttpBasic(ADMIN)))
                .andDo(print())
                .andExpect(status().isNoContent());


    }
}