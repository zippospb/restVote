package ru.zippospb.restvote.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;
import ru.zippospb.restvote.model.Dish;
import ru.zippospb.restvote.repository.datajpa.CrudDishRepository;

import java.time.LocalDate;
import java.util.List;

import static ru.zippospb.restvote.util.ValidationUtil.*;

@Repository
public class DishServiceImpl implements DishService {
    private final CrudDishRepository repository;

    @Autowired
    public DishServiceImpl(CrudDishRepository repository) {
        this.repository = repository;
    }

    public List<Dish> getAll(int restId){
        return repository.getAllByRestaurant(restId);
    }

    @Override
    public List<Dish> getAllByDate(int restId, LocalDate date){
        Assert.notNull(date, "date must not be null");
        return repository.getAllByRestaurantIdAndDate(restId, date);
    }

    public Dish get(int restId, int dishId){
        return checkNotFoundWithId(repository.getById(restId, dishId),
                String.format("restId=%s, dishId=%s", restId, dishId));
    }

    @Override
    public Dish create(Dish dish) {
        Assert.notNull(dish, "dish must not be null");
        return repository.save(dish);
    }

    @Override
    public void update(Dish dish) {
        Assert.notNull(dish, "dish must not be null");
        checkNotFoundWithId(repository.save(dish), dish.getId());
    }

    public void delete(int dishId, int restId) {
        checkNotFound(repository.delete(dishId, restId) > 0,
                String.format("restId=%s, dishId=%s", restId, dishId));
    }
}
