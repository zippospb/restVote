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
import ru.zippospb.restvote.repository.datajpa.CrudVoteRepository;
import ru.zippospb.restvote.service.VoteService;
import ru.zippospb.restvote.util.exception.ErrorType;
import ru.zippospb.restvote.web.AbstractControllerTest;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.zippospb.restvote.RestaurantTestData.*;
import static ru.zippospb.restvote.TestUtil.readFromJson;
import static ru.zippospb.restvote.TestUtil.userHttpBasic;
import static ru.zippospb.restvote.UserTestData.USER1;
import static ru.zippospb.restvote.VoteTestData.USER1_VOTE1;
import static ru.zippospb.restvote.VoteTestData.contentJson;

class ProfileVoteRestControllerTest extends AbstractControllerTest {
    private final String REST_URL = ProfileVoteRestController.REST_URL;

    @Autowired
    private VoteService voteService;

    @Autowired
    private CrudVoteRepository voteRepository;

    @Test
    void testGetCurrent() throws Exception {
        Vote vote = voteRepository.save(new Vote(USER1, REST2));
        mockMvc.perform(get(REST_URL + "profile/vote")
                .with(userHttpBasic(USER1)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(vote));
    }

    @Test
    void testGetCurrentNotFound() throws Exception {
        mockMvc.perform(get(REST_URL + "profile/vote")
                .with(userHttpBasic(USER1)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(new Vote(USER1, LocalDate.now())));
    }

    @Test
    void testGetCurrentUnAuth() throws Exception {
        mockMvc.perform(get(REST_URL + "profile/vote"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void testGetByDate() throws Exception {
        mockMvc.perform(get(REST_URL + "profile/vote/by?date=" + USER1_VOTE1.getDate())
                .with(userHttpBasic(USER1)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(USER1_VOTE1));
    }

    @Test
    void testGetByDateNotFound() throws Exception {
        mockMvc.perform(get(REST_URL + "profile/vote/by?date=" + "1999-01-01")
                .with(userHttpBasic(USER1)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(new Vote(USER1, LocalDate.of(1999, 1, 1))));
    }

    @Test
    void testCreateNewVote() throws Exception {
        Vote expected = new Vote(new User(USER1), new Restaurant(REST1));

        ResultActions actions = mockMvc.perform(post(REST_URL + "restaurants/" + REST1_ID + "/votes")
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
        voteService.vote(USER1, REST2.getId());
        Vote expected = new Vote(new User(USER1), new Restaurant(REST1));
        ReflectionTestUtils.setField(voteService, "END_TIME_OF_VOTE", LocalTime.MAX);
        ResultActions actions = mockMvc.perform(post(REST_URL + "restaurants/" + REST1_ID + "/votes")
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
        voteRepository.save(new Vote(USER1, REST2));
        ReflectionTestUtils.setField(voteService, "END_TIME_OF_VOTE", LocalTime.MIN);
        mockMvc.perform(post(REST_URL + "restaurants/" + REST1_ID + "/votes")
                .with(userHttpBasic(USER1)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity())
                .andExpect(errorType(ErrorType.REVOTE_ERROR));
    }

    @Test
    void testVoteUnAuth() throws Exception {
        mockMvc.perform(post(REST_URL + "restaurants/" + REST1_ID + "/votes"))
                .andExpect(status().isUnauthorized());
    }
}