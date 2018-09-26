package ru.zippospb.restvote.web.restaurant;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import ru.zippospb.restvote.model.Restaurant;
import ru.zippospb.restvote.model.Vote;
import ru.zippospb.restvote.repository.RestaurantRepository;
import ru.zippospb.restvote.repository.UserRepository;
import ru.zippospb.restvote.repository.VoteRepository;
import ru.zippospb.restvote.util.exception.IllegalRequestDataException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static ru.zippospb.restvote.util.ValidationUtil.*;
import static ru.zippospb.restvote.web.security.SecurityUtil.authUserId;

public abstract class AbstractRestaurantController {
    private final Logger log = LoggerFactory.getLogger(getClass());

    private final LocalTime END_TIME_OF_VOTE = LocalTime.of(11, 0, 0);

    @Autowired
    private RestaurantRepository restRepository;

    @Autowired
    private VoteRepository voteRepository;

    @Autowired
    private UserRepository userRepository;

    List<Restaurant> getAll(){
        log.info("getAll");
        return restRepository.getAll();
    }

    //TODO возможно лучше будет сделать ТО с добавлением поля "Количество голосов"
    Restaurant get(int id){
        log.info("get {}", id);
        return checkNotFoundWithId(restRepository.get(id), id);
    }

    Restaurant create(Restaurant restaurant){
        log.info("create {}", restaurant);
        checkNew(restaurant);
        return restRepository.save(restaurant);
    }

    void update(Restaurant restaurant,int id) {
        log.info("update {} with id {}", restaurant, id);
        assureIdConsistent(restaurant, id);
        checkNotFoundWithId(restRepository.save(restaurant), restaurant.getId());
    }

    void delete(int id) {
        log.info("delete {}", id);
        checkNotFoundWithId(restRepository.delete(id), id);
    }

    @Transactional
    Vote vote(int restId) {
        int userId = authUserId();
        Vote vote = voteRepository.get(userId, LocalDate.now());

        if(vote == null){
            log.info("create vote by user {} for restaurant {}", userId, restId);
            vote = new Vote(userRepository.getReference(authUserId()), restRepository.getReference(restId));
        } else if (LocalTime.now().isBefore(END_TIME_OF_VOTE)){
            log.info("update vote by user {} for restaurant {}", userId, restId);
            vote.setRestaurant(restRepository.getReference(restId));
        } else {
            throw new IllegalRequestDataException("you can revote before 11:00");
        }

        return voteRepository.save(vote);
    }
}
