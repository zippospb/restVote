package ru.zippospb.restvote;

import ru.zippospb.restvote.model.Vote;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.zippospb.restvote.RestaurantTestData.*;
import static ru.zippospb.restvote.UserTestData.*;
import static ru.zippospb.restvote.model.AbstractBaseEntity.START_SEQ;

public class VoteTestData {
    public final static int U1R1_VOTE_ID = START_SEQ + 18;

    public final static Vote USER1_VOTE1 = new Vote(U1R1_VOTE_ID, USER1, REST1, LocalDate.of(2018, 7, 20), LocalTime.of(10, 0));
    public final static Vote USER2_VOTE1 = new Vote(U1R1_VOTE_ID + 1, USER2, REST2, LocalDate.of(2018, 7, 20), LocalTime.of(11, 0));
    public final static Vote ADMIN_VOTE1 = new Vote(U1R1_VOTE_ID + 2, ADMIN, REST3, LocalDate.of(2018, 7, 20), LocalTime.of(12, 0));
    public final static Vote USER1_VOTE2 = new Vote(U1R1_VOTE_ID + 3, USER1, REST1, LocalDate.of(2018, 7, 21), LocalTime.of(10, 0));
    public final static Vote USER2_VOTE2 = new Vote(U1R1_VOTE_ID + 4, USER2, REST2, LocalDate.of(2018, 7, 21), LocalTime.of(11, 0));
    public final static Vote ADMIN_VOTE2 = new Vote(U1R1_VOTE_ID + 5, ADMIN, REST3, LocalDate.of(2018, 7, 21), LocalTime.of(12, 0));
    public final static Vote USER1_VOTE3 = new Vote(U1R1_VOTE_ID + 6, USER1, REST1, LocalDate.of(2018, 7, 22), LocalTime.of(10, 0));
    public final static Vote USER2_VOTE3 = new Vote(U1R1_VOTE_ID + 7, USER2, REST2, LocalDate.of(2018, 7, 22), LocalTime.of(11, 0));
    public final static Vote ADMIN_VOTE3 = new Vote(U1R1_VOTE_ID + 8, ADMIN, REST3, LocalDate.of(2018, 7, 22), LocalTime.of(12, 0));

    static {
        Arrays.asList(USER1_VOTE1, USER1_VOTE2, USER1_VOTE3).forEach(v -> {
            v.setUser(USER1);
            v.setRestaurant(REST1);
        });
        Arrays.asList(USER2_VOTE1, USER2_VOTE2, USER2_VOTE3).forEach(v -> {
            v.setUser(USER2);
            v.setRestaurant(REST2);
        });
        Arrays.asList(ADMIN_VOTE1, ADMIN_VOTE2, ADMIN_VOTE3).forEach(v -> {
            v.setUser(ADMIN);
            v.setRestaurant(REST3);
        });
    }

    public static void assertMatch(Vote actual, Vote expected) {
        assertThat(actual).isEqualToIgnoringGivenFields(expected, "restaurant", "user");
        assertThat(actual.getUser().getId()).isEqualTo(expected.getUser().getId());
        assertThat(actual.getRestaurant().getId()).isEqualTo(expected.getRestaurant().getId());
    }
}
