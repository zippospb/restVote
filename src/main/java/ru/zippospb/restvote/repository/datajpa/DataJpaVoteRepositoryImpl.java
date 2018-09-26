package ru.zippospb.restvote.repository.datajpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.zippospb.restvote.model.Vote;
import ru.zippospb.restvote.repository.VoteRepository;

import java.time.LocalDate;
import java.time.LocalTime;

@Repository
public class DataJpaVoteRepositoryImpl implements VoteRepository {
    private final CrudVoteRepository repository;

    @Autowired
    public DataJpaVoteRepositoryImpl(CrudVoteRepository repository) {
        this.repository = repository;
    }

    @Override
    public Vote get(int userId, LocalDate date) {
        return repository.getByUserIdAndDate(userId, date);
    }

    @Override
    public Vote save(Vote vote) {
        return repository.save(vote);
    }
}
