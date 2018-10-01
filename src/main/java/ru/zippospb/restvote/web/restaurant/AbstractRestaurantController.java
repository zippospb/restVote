package ru.zippospb.restvote.web.restaurant;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import ru.zippospb.restvote.AuthorizedUser;
import ru.zippospb.restvote.model.Restaurant;
import ru.zippospb.restvote.model.Vote;
import ru.zippospb.restvote.service.RestaurantService;
import ru.zippospb.restvote.service.UserService;
import ru.zippospb.restvote.service.VoteService;
import ru.zippospb.restvote.util.exception.IllegalRequestDataException;

import java.time.LocalTime;
import java.util.List;

import static ru.zippospb.restvote.util.ValidationUtil.assureIdConsistent;
import static ru.zippospb.restvote.util.ValidationUtil.checkNew;
import static ru.zippospb.restvote.web.security.SecurityUtil.authUserId;
import static ru.zippospb.restvote.web.security.SecurityUtil.saveGet;

public abstract class AbstractRestaurantController {
    private final Logger log = LoggerFactory.getLogger(getClass());

    private final LocalTime END_TIME_OF_VOTE = LocalTime.of(11, 0, 0);

    @Autowired
    private RestaurantService restService;

    @Autowired
    private VoteService voteService;

    @Autowired
    private UserService userService;

    List<Restaurant> getAll(){
        log.info("getAll");
        return restService.getAll();
    }

    //TODO возможно лучше будет сделать ТО с добавлением поля "Количество голосов"
    Restaurant get(int id){
        log.info("getUserVote {}", id);
        return restService.get(id);
    }

    Restaurant create(Restaurant restaurant){
        log.info("create {}", restaurant);
        checkNew(restaurant);
        return restService.create(restaurant);
    }

    void update(Restaurant restaurant,int id) {
        log.info("update {} with id {}", restaurant, id);
        assureIdConsistent(restaurant, id);
        restService.update(restaurant);
    }

    void delete(int id) {
        log.info("delete {}", id);
        restService.delete(id);
    }

    @Transactional
    Vote vote(int restId) {
        AuthorizedUser user = saveGet();
        assert user != null;
        Vote vote = voteService.getUserVote(user.getId());

        if(vote == null){
            log.info("create vote by user {} for restaurant {}", user, restId);
            vote = new Vote(userService.getReference(authUserId()), restService.getReference(restId));
        } else if (LocalTime.now().isBefore(END_TIME_OF_VOTE)){
            log.info("update vote by user {} for restaurant {}", user, restId);
            vote.setRestaurant(restService.getReference(restId));
        } else {
            throw new IllegalRequestDataException("you can re-vote before 11:00");
        }

        return voteService.save(vote);
    }
}
