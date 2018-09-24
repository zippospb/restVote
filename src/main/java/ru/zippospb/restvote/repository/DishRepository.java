package ru.zippospb.restvote.repository;

import ru.zippospb.restvote.model.Dish;

import java.util.List;

public interface DishRepository {
    List<Dish> getAll(int restId);

    Dish get(int restId, int dishId);

    Dish save(Dish dish);

    boolean delete(int dishId, int restId);
}
