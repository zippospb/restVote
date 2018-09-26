package ru.zippospb.restvote.repository.datajpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.zippospb.restvote.model.Restaurant;
import ru.zippospb.restvote.repository.RestaurantRepository;

import java.util.List;

@Repository
public class DataJpaRestaurantRepositoryImpl implements RestaurantRepository {
    private final CrudRestaurantRepository repository;

    @Autowired
    public DataJpaRestaurantRepositoryImpl(CrudRestaurantRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Restaurant> getAll() {
        return repository.findAll();
    }

    @Override
    public Restaurant get(int id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public Restaurant save(Restaurant restaurant) {
        return repository.save(restaurant);
    }

    @Override
    public Restaurant getReference(int restId) {
        return repository.getOne(restId);
    }

    @Override
    public boolean delete(int id) {
        return repository.delete(id) > 0;
    }
}
