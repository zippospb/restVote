package ru.zippospb.restvote.web.restaurant;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import ru.zippospb.restvote.TestUtil;
import ru.zippospb.restvote.model.Dish;
import ru.zippospb.restvote.service.DishService;
import ru.zippospb.restvote.service.RestaurantService;
import ru.zippospb.restvote.util.exception.ErrorType;
import ru.zippospb.restvote.web.AbstractControllerTest;
import ru.zippospb.restvote.web.json.JsonUtil;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.zippospb.restvote.DishTestData.*;
import static ru.zippospb.restvote.RestaurantTestData.REST1_ID;
import static ru.zippospb.restvote.TestUtil.readFromJson;
import static ru.zippospb.restvote.TestUtil.userHttpBasic;
import static ru.zippospb.restvote.UserTestData.ADMIN;
import static ru.zippospb.restvote.UserTestData.USER1;
import static ru.zippospb.restvote.web.restaurant.AdminDishRestController.REST_URL;

class AdminDishRestControllerTest extends AbstractControllerTest {
    private final static String REST1_REST_URL = REST_URL.replaceAll("\\{restId\\}", String.valueOf(REST1_ID)) + "/";

    @Autowired
    private DishService dishService;

    @Autowired
    private RestaurantService restService;

    @Test
    void testGetAll() throws Exception {
        mockMvc.perform(get(REST1_REST_URL)
                .with(userHttpBasic(ADMIN)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(TestUtil.contentJson(REST1_DISH1, REST1_DISH2, REST1_DISH3, REST1_DISH4, REST1_OLD_DISH));
    }

    @Test
    void testGetAllByDate() throws Exception {
        mockMvc.perform(get(REST1_REST_URL + "by?date=" + LocalDate.now().toString())
                .with(userHttpBasic(ADMIN)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(TestUtil.contentJson(REST1_DISH1, REST1_DISH2, REST1_DISH3, REST1_DISH4));
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
    void testGetNotFound() throws Exception {
        mockMvc.perform(get(REST1_REST_URL + 1)
                .with(userHttpBasic(ADMIN)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity())
                .andExpect(errorType(ErrorType.DATA_NOT_FOUND));
    }

    @Test
    void testGetForbidden() throws Exception {
        mockMvc.perform(get(REST1_REST_URL + REST1_DISH1_ID)
                .with(userHttpBasic(USER1)))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    void testDelete() throws Exception {
        mockMvc.perform(delete(REST1_REST_URL + REST1_DISH1_ID)
                .with(userHttpBasic(ADMIN)))
                .andDo(print())
                .andExpect(status().isNoContent());

        assertMatch(dishService.getAll(REST1_ID), REST1_DISH2, REST1_DISH3, REST1_DISH4, REST1_OLD_DISH);
    }

    @Test
    void testDeleteNotFound() throws Exception {
        mockMvc.perform(delete(REST1_REST_URL + 1)
                .with(userHttpBasic(ADMIN)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity())
                .andExpect(errorType(ErrorType.DATA_NOT_FOUND));
    }

    @Test
    void testCreate() throws Exception {
        Dish expected = getNewDish();

        ResultActions action = mockMvc.perform(post(REST1_REST_URL)
                .with(userHttpBasic(ADMIN))
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(expected)))
                .andDo(print())
                .andExpect(status().isCreated());

        Dish returned = readFromJson(action, Dish.class);
        expected.setId(returned.getId());
        expected.setRestaurantId(returned.getRestaurantId());

        assertMatch(returned, expected);
        assertMatch(dishService.getAllByDate(REST1_ID, LocalDate.now()),
                REST1_DISH1, REST1_DISH2, REST1_DISH3, REST1_DISH4, expected);
    }

    @Test
    void testCreateInvalid() throws Exception {
        Dish expected = getNewDish();
        expected.setName("");
        expected.setPrice(5);

        mockMvc.perform(post(REST1_REST_URL)
                .with(userHttpBasic(ADMIN))
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(expected)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity())
                .andExpect(errorType(ErrorType.VALIDATION_ERROR));
    }

    @Test
    void testUpdate() throws Exception {
        Dish updated = new Dish(REST1_DISH1);
        updated.setName("новая еда");

        mockMvc.perform(put(REST1_REST_URL + REST1_DISH1_ID)
                .with(userHttpBasic(ADMIN))
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated)))
                .andDo(print())
                .andExpect(status().isNoContent());

        assertMatch(dishService.get(REST1_ID, REST1_DISH1_ID), updated);
    }

    @Test
    void testUpdateInvalid() throws Exception {
        Dish updated = new Dish(REST1_DISH1);
        updated.setName("");

        mockMvc.perform(put(REST1_REST_URL + REST1_DISH1_ID)
                .with(userHttpBasic(ADMIN))
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity())
                .andExpect(errorType(ErrorType.VALIDATION_ERROR));
    }
}