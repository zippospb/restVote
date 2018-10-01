package ru.zippospb.restvote.service;

import ru.zippospb.restvote.model.Vote;

public interface VoteService {
    Vote getUserVote(int userId);

    Vote save(Vote vote);
}
