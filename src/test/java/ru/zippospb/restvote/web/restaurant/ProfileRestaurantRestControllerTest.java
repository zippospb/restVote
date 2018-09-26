package ru.zippospb.restvote.web.restaurant;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import ru.zippospb.restvote.DishTestData;
import ru.zippospb.restvote.TestUtil;
import ru.zippospb.restvote.VoteTestData;
import ru.zippospb.restvote.model.Restaurant;
import ru.zippospb.restvote.model.User;
import ru.zippospb.restvote.model.Vote;
import ru.zippospb.restvote.repository.RestaurantRepository;
import ru.zippospb.restvote.web.AbstractControllerTest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.zippospb.restvote.RestaurantTestData.*;
import static ru.zippospb.restvote.TestUtil.readFromJson;
import static ru.zippospb.restvote.TestUtil.userHttpBasic;
import static ru.zippospb.restvote.UserTestData.USER1;

class ProfileRestaurantRestControllerTest extends AbstractControllerTest {
    private final String REST_URL = ProfileRestaurantRestController.REST_URL + "/";

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

    @Test
    void testCreateNewVote() throws Exception {
        Vote expected = new Vote(new User(USER1), new Restaurant(REST1));

        ResultActions actions = mockMvc.perform(post(REST_URL + REST1_ID + "/votes")
                .with(userHttpBasic(USER1)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));

        Vote returned = readFromJson(actions, Vote.class);
        expected.setId(returned.getId());
        VoteTestData.assertMatch(expected, returned);
    }
}