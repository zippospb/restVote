package ru.zippospb.restvote.web.restaurant;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import ru.zippospb.restvote.TestUtil;
import ru.zippospb.restvote.repository.RestaurantRepository;
import ru.zippospb.restvote.web.AbstractControllerTest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.zippospb.restvote.RestaurantTestData.*;
import static ru.zippospb.restvote.RestaurantTestData.REST3;
import static ru.zippospb.restvote.TestUtil.userHttpBasic;
import static ru.zippospb.restvote.UserTestData.USER1;
import static ru.zippospb.restvote.web.restaurant.ProfileRestaurantRestController.REST_URL;

class ProfileRestaurantRestControllerTest extends AbstractControllerTest {

    @Autowired
    private RestaurantRepository repository;

    @Test
    void testGetAll() throws Exception {
        mockMvc.perform(get(REST_URL)
                .with(userHttpBasic(USER1)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(TestUtil.contentJson(REST1, REST2, REST3));
    }

    @Test
    void testGet() throws Exception {
        mockMvc.perform(get(REST_URL + REST1_ID)
                .with(userHttpBasic(USER1)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(TestUtil.contentJson(REST1));
    }

    @Test
    void testGetUnAuth() throws Exception {
        mockMvc.perform(get(REST_URL))
                .andExpect(status().isUnauthorized());
    }
}