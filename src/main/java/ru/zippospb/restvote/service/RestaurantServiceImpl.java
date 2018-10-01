package ru.zippospb.restvote.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import ru.zippospb.restvote.model.Restaurant;
import ru.zippospb.restvote.repository.datajpa.CrudRestaurantRepository;

import java.util.List;

import static ru.zippospb.restvote.util.ValidationUtil.checkNotFoundWithId;

@Service
public class RestaurantServiceImpl implements RestaurantService {
    private final CrudRestaurantRepository repository;

    @Autowired
    public RestaurantServiceImpl(CrudRestaurantRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Restaurant> getAll() {
        return repository.findAll();
    }

    @Override
    public Restaurant get(int id) {
        return checkNotFoundWithId(repository.findById(id), id);
    }

    @Override
    @Transactional
    public Restaurant create(Restaurant restaurant) {
        Assert.notNull(restaurant, "restaurant must not be null");
        return repository.save(restaurant);
    }

    @Override
    public void update(Restaurant restaurant) {
        Assert.notNull(restaurant, "restaurant must not be null");
        Restaurant updated = get(restaurant.getId());
        if(restaurant.getDishes() != null){
            updated.setDishes(restaurant.getDishes());
        }
        updated.setName(restaurant.getName());
        checkNotFoundWithId(repository.save(updated), restaurant.getId());
    }

    @Override
    public Restaurant getReference(int restId) {
        return repository.getOne(restId);
    }

    @Override
    public void delete(int id) {
        checkNotFoundWithId(repository.delete(id) != 0, id);
    }
}
