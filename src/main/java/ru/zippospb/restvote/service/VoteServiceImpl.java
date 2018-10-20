package ru.zippospb.restvote.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import ru.zippospb.restvote.model.User;
import ru.zippospb.restvote.model.Vote;
import ru.zippospb.restvote.repository.datajpa.CrudVoteRepository;
import ru.zippospb.restvote.util.exception.TooLateToVoteException;

import java.time.LocalDate;
import java.time.LocalTime;

@Repository
public class VoteServiceImpl implements VoteService {
    private final Logger log = LoggerFactory.getLogger(getClass());

    private final LocalTime END_TIME_OF_VOTE = LocalTime.of(11, 0, 0);

    private final CrudVoteRepository repository;

    private final RestaurantService restService;

    @Autowired
    public VoteServiceImpl(CrudVoteRepository repository, RestaurantService restService) {
        this.repository = repository;
        this.restService = restService;
    }

    @Override
    public Vote getByUserIdAndDate(int userId, LocalDate date) {
        log.info("get vote for user {} by date {}", userId, date);
        return repository.getByUserIdAndDate(userId, date);
    }

    @Override
    public int getVoteCount(int restId, LocalDate date) {
        return repository.getVoteCount(restId, date);
    }

    @Override
    @Transactional
    public Vote vote(User user, int restId){
        Assert.notNull(user, "user must not be null");
        Vote vote = repository.getByUserIdAndDate(user.getId(), LocalDate.now());

        if(vote == null){
            log.info("create vote by user {} for restaurant {}", user, restId);
            vote = new Vote(user, restService.get(restId));
        } else if (LocalTime.now().isBefore(END_TIME_OF_VOTE)){
            log.info("update vote by user {} for restaurant {}", user, restId);
            vote.setRestaurant(restService.get(restId));
        } else {
            throw new TooLateToVoteException("you can`t to re-vote after " + END_TIME_OF_VOTE.toString());
        }

        return repository.save(vote);
    }
}
