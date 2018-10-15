package ru.zippospb.restvote.web.vote;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.ResultActions;
import ru.zippospb.restvote.VoteTestData;
import ru.zippospb.restvote.model.Restaurant;
import ru.zippospb.restvote.model.User;
import ru.zippospb.restvote.model.Vote;
import ru.zippospb.restvote.service.VoteService;
import ru.zippospb.restvote.web.AbstractControllerTest;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.zippospb.restvote.RestaurantTestData.*;
import static ru.zippospb.restvote.TestUtil.*;
import static ru.zippospb.restvote.UserTestData.USER1;

class ProfileVoteRestControllerTest extends AbstractControllerTest {
    private final String REST_URL = ProfileVoteRestController.REST_URL;

    @Autowired
    private VoteService voteService;

    @Autowired
    private ProfileVoteRestController controller;

    @Test
    void testCreateNewVote() throws Exception {
        Vote expected = new Vote(new User(USER1), new Restaurant(REST1));

        ResultActions actions = mockMvc.perform(get(REST_URL + "restaurants/" + REST1_ID + "/votes")
                .with(userHttpBasic(USER1)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));

        Vote returned = readFromJson(actions, Vote.class);
        expected.setId(returned.getId());
        VoteTestData.assertMatch(expected, returned);
        Assertions.assertEquals(voteService.getVoteCount(REST1_ID, LocalDate.now()), 1);
    }

    @Test
    void testUpdateVote() throws Exception {
        voteService.save(new Vote(USER1, REST2));
        Vote expected = new Vote(new User(USER1), new Restaurant(REST1));
        ReflectionTestUtils.setField(controller, "END_TIME_OF_VOTE", LocalTime.MAX);
        ResultActions actions = mockMvc.perform(get(REST_URL + "restaurants/" + REST1_ID + "/votes")
                .with(userHttpBasic(USER1)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));

        Vote returned = readFromJson(actions, Vote.class);
        expected.setId(returned.getId());
        VoteTestData.assertMatch(expected, returned);
        Assertions.assertEquals(voteService.getVoteCount(REST1_ID, LocalDate.now()), 1);

    }

    @Test
    void testTooLateToUpdateVote() throws Exception {
        voteService.save(new Vote(USER1, REST2));
        ReflectionTestUtils.setField(controller, "END_TIME_OF_VOTE", LocalTime.MIN);
        mockMvc.perform(get(REST_URL + "restaurants/" + REST1_ID + "/votes")
                .with(userHttpBasic(USER1)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void testGet() throws Exception {
        Vote vote = voteService.save(new Vote(USER1, REST2));
        mockMvc.perform(get(REST_URL + "/vote")
                .with(userHttpBasic(USER1)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(vote));
    }

    @Test
    void testGetNotFound() throws Exception {
        mockMvc.perform(get(REST_URL + "/vote")
                .with(userHttpBasic(USER1)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }
}