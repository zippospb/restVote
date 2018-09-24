package ru.zippospb.restvote.repository.datajpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.zippospb.restvote.model.Dish;
import ru.zippospb.restvote.repository.DishRepository;

import java.util.List;

@Repository
public class DataJpaDishRepositoryImpl implements DishRepository {
    private final CrudDishRepository repository;

    @Autowired
    public DataJpaDishRepositoryImpl(CrudDishRepository repository) {
        this.repository = repository;
    }

    public Dish get(int restId, int dishId){
        return repository.getById(restId, dishId).orElse(null);
    }

    public List<Dish> getAll(int restId){
        return repository.getAllByRestaurant(restId);
    }

    @Transactional
    public Dish save(Dish dish) {
        return repository.save(dish);
    }

    public boolean delete(int dishId, int restId) {
        return repository.delete(dishId, restId) > 0;
    }
}
