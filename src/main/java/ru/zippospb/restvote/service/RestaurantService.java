package ru.zippospb.restvote.service;

import ru.zippospb.restvote.model.Restaurant;

import java.util.List;

public interface RestaurantService {
    List<Restaurant> getAll();

    Restaurant get(int id);

    Restaurant create(Restaurant restaurant);

    void update(Restaurant restaurant);

    void delete(int id);
}
