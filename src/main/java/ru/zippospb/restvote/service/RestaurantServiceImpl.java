package ru.zippospb.restvote.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
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
    @Cacheable("restaurants")
    public List<Restaurant> getAll() {
        return repository.findAll();
    }

    @Override
    public Restaurant get(int id) {
        return checkNotFoundWithId(repository.findById(id), id);
    }

    @Override
    @CacheEvict(value = "restaurants", allEntries = true)
    @Transactional
    public Restaurant create(Restaurant restaurant) {
        Assert.notNull(restaurant, "restaurant must not be null");
        return repository.save(restaurant);
    }

    @Override
    @CacheEvict(value = "restaurants", allEntries = true)
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
    @CacheEvict(value = "restaurants", allEntries = true)
    public void delete(int id) {
        checkNotFoundWithId(repository.delete(id) != 0, id);
    }
}
