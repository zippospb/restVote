package ru.zippospb.restvote.repository;

import ru.zippospb.restvote.model.Vote;

import java.time.LocalDate;

public interface VoteRepository {
    Vote get(int userId, LocalDate now);

    Vote save(Vote vote);
}
