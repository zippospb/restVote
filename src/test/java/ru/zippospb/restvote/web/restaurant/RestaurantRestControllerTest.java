package ru.zippospb.restvote.web.restaurant;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import ru.zippospb.restvote.TestUtil;
import ru.zippospb.restvote.web.AbstractControllerTest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.zippospb.restvote.RestaurantTestData.*;

class RestaurantRestControllerTest extends AbstractControllerTest {
    private final String REST_URL = RestaurantRestController.REST_URL + "/";

    @Test
    void testGetAll() throws Exception {
        mockMvc.perform(get(REST_URL))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(TestUtil.contentJson(REST1, REST2, REST3));
    }

    @Test
    void testGet() throws Exception {
        mockMvc.perform(get(REST_URL + REST1_ID))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(TestUtil.contentJson(REST1));
    }
}