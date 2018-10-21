package ru.zippospb.restvote.service;

import ru.zippospb.restvote.model.Dish;

import java.time.LocalDate;
import java.util.List;

public interface DishService {
    List<Dish> getAll(int restId);

    Dish get(int restId, int dishId);

    Dish create(Dish dish);

    void update(Dish dish, int dishId, int restId);

    List<Dish> getAllByDate(int restId, LocalDate date);

    void delete(int dishId, int restId);
}
