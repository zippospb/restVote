package ru.zippospb.restvote.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.zippospb.restvote.model.Vote;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static ru.zippospb.restvote.RestaurantTestData.REST1;
import static ru.zippospb.restvote.RestaurantTestData.REST1_ID;
import static ru.zippospb.restvote.UserTestData.USER1;
import static ru.zippospb.restvote.UserTestData.USER1_ID;
import static ru.zippospb.restvote.VoteTestData.USER1_VOTE1;
import static ru.zippospb.restvote.VoteTestData.assertMatch;

class VoteServiceImplTest extends AbstractServiceTest{

    @Autowired
    VoteService service;

    @Test
    void testGetNoCurrentDateUserVote() {
        assertNull(service.getUserVote(USER1_ID));
    }

    @Test
    void testSave() {
        Vote newVote = new Vote(USER1, REST1);
        Vote created = service.save(newVote);
        newVote.setId(created.getId());
        assertMatch(service.getUserVote(USER1_ID), newVote);
    }

    @Test
    void testGetVoteCountWithNoVotes() {
        assertEquals(service.getVoteCount(REST1_ID, LocalDate.now()), 0);
    }

    @Test
    void testGetVoteCountWithVotes() {
        assertEquals(service.getVoteCount(REST1_ID, USER1_VOTE1.getDate()), 1);
    }
}