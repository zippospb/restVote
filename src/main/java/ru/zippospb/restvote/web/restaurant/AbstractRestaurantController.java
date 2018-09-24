package ru.zippospb.restvote.web.restaurant;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import ru.zippospb.restvote.model.Restaurant;
import ru.zippospb.restvote.repository.RestaurantRepository;

import java.util.List;

import static ru.zippospb.restvote.util.ValidationUtil.*;

public abstract class AbstractRestaurantController {
    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private RestaurantRepository repository;

    List<Restaurant> getAll(){
        log.info("getAll");
        return repository.getAll();
    }

    //TODO возможно лучше будет сделать ТО с добавлением поля "Количество голосов"
    Restaurant get(int id){
        log.info("get {}", id);
        return checkNotFoundWithId(repository.get(id), id);
    }

    Restaurant create(Restaurant restaurant){
        log.info("create {}", restaurant);
        checkNew(restaurant);
        return repository.save(restaurant);
    }

    void update(Restaurant restaurant,int id) {
        log.info("update {} with id {}", restaurant, id);
        assureIdConsistent(restaurant, id);
        checkNotFoundWithId(repository.save(restaurant), restaurant.getId());
    }

    public void delete(int id) {
        log.info("delete {}", id);
        checkNotFoundWithId(repository.delete(id), id);
    }
}
