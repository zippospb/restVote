package ru.zippospb.restvote.service;

import ru.zippospb.restvote.model.Vote;

import java.time.LocalDate;

public interface VoteService {
    Vote getUserVote(int userId);

    Vote save(Vote vote);

    int getVoteCount(int restId, LocalDate date);
}
