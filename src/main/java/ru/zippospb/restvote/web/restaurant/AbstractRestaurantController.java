package ru.zippospb.restvote.web.restaurant;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import ru.zippospb.restvote.model.Restaurant;
import ru.zippospb.restvote.service.RestaurantService;
import ru.zippospb.restvote.service.VoteService;
import ru.zippospb.restvote.to.RestaurantTo;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import static ru.zippospb.restvote.util.ValidationUtil.assureIdConsistent;
import static ru.zippospb.restvote.util.ValidationUtil.checkNew;

public abstract class AbstractRestaurantController {
    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private RestaurantService restService;

    @Autowired
    private VoteService voteService;

    List<Restaurant> getAll(){
        log.info("getAll");
        return restService.getAll();
    }

    @Transactional
    List<RestaurantTo> getAllTo(){
        log.info("getAllTo");
        return restService.getAll().stream()
                .map(rest -> new RestaurantTo(rest, getVoteCount(rest.getId(), LocalDate.now())))
                .collect(Collectors.toList());
    }

    @Transactional
    RestaurantTo getTo(int id){
        log.info("getTo {}", id);
        Restaurant restaurant = restService.get(id);
        int voteCount = getVoteCount(id, LocalDate.now());
        return new RestaurantTo(restaurant, voteCount);
    }

    Restaurant get(int id){
        log.info("get {}", id);
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

    private int getVoteCount(int restId, LocalDate date){
        return voteService.getVoteCount(restId, date);
    }
}
