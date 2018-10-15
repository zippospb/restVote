package ru.zippospb.restvote.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;
import ru.zippospb.restvote.model.Vote;
import ru.zippospb.restvote.repository.datajpa.CrudVoteRepository;

import java.time.LocalDate;

@Repository
public class VoteServiceImpl implements VoteService {
    private final CrudVoteRepository repository;

    @Autowired
    public VoteServiceImpl(CrudVoteRepository repository) {
        this.repository = repository;
    }

    @Override
    public Vote getByUser(int userId) {
        return repository.getByUserIdAndDate(userId, LocalDate.now());
    }

    @Override
    public Vote save(Vote vote) {
        Assert.notNull(vote, "vote must not be null");
        return repository.save(vote);
    }

    @Override
    public int getVoteCount(int restId, LocalDate date) {
        return repository.getVoteCount(restId, date);
    }
}
