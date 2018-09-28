package ru.zippospb.restvote.repository;

import ru.zippospb.restvote.model.Dish;

import java.time.LocalDate;
import java.util.List;

public interface DishRepository {
    List<Dish> getAll(int restId);

    Dish get(int restId, int dishId);

    Dish save(Dish dish);

    List<Dish> getAllByDate(int restId, LocalDate date);

    boolean delete(int dishId, int restId);
}
