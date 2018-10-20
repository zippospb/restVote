package ru.zippospb.restvote.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.util.ReflectionTestUtils;
import ru.zippospb.restvote.model.Vote;
import ru.zippospb.restvote.util.exception.TooLateToVoteException;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;
import static ru.zippospb.restvote.RestaurantTestData.*;
import static ru.zippospb.restvote.UserTestData.USER1;
import static ru.zippospb.restvote.UserTestData.USER1_ID;
import static ru.zippospb.restvote.VoteTestData.USER1_VOTE1;
import static ru.zippospb.restvote.VoteTestData.assertMatch;

class VoteServiceImplTest extends AbstractServiceTest{

    @Autowired
    VoteService service;

    @Test
    void testGetNoCurrentDateUserVote() {
        assertNull(service.getByUserIdAndDate(USER1_ID, LocalDate.now()));
    }

    @Test
    void testCreateNewVote() {
        Vote newVote = new Vote(USER1, REST1);
        Vote created = service.vote(USER1, REST1_ID);
        newVote.setId(created.getId());
        assertMatch(service.getByUserIdAndDate(USER1_ID, LocalDate.now()), newVote);
    }

    @Test
    void testUpdateVote() {
        ReflectionTestUtils.setField(service, "END_TIME_OF_VOTE", LocalTime.MAX);
        service.vote(USER1, REST1_ID);
        Vote newVote = new Vote(USER1, REST2);
        Vote created = service.vote(USER1, REST2.getId());
        newVote.setId(created.getId());
        assertMatch(service.getByUserIdAndDate(USER1_ID, LocalDate.now()), newVote);
    }

    @Test
    void testTooLateToUpdateVote() {
        ReflectionTestUtils.setField(service, "END_TIME_OF_VOTE", LocalTime.MIN);
        service.vote(USER1, REST1_ID);
        assertThrows(TooLateToVoteException.class, () -> service.vote(USER1, REST2.getId()));
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