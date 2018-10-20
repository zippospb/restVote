package ru.zippospb.restvote.service;

import ru.zippospb.restvote.model.User;
import ru.zippospb.restvote.model.Vote;

import java.time.LocalDate;

public interface VoteService {
    Vote getByUserIdAndDate(int userId, LocalDate date);

    int getVoteCount(int restId, LocalDate date);

    Vote vote(User user, int restId);
}
