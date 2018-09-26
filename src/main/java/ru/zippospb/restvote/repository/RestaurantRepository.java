package ru.zippospb.restvote.repository;

import ru.zippospb.restvote.model.Restaurant;

import java.util.List;

public interface RestaurantRepository {
    List<Restaurant> getAll();

    Restaurant get(int id);

    Restaurant save(Restaurant restaurant);

    boolean delete(int id);

    Restaurant getReference(int restId);
}
