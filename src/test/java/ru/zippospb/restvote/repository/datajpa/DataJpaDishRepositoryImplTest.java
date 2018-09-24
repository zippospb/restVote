package ru.zippospb.restvote.repository.datajpa;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.zippospb.restvote.model.Dish;
import ru.zippospb.restvote.repository.AbstractRepositoryTest;
import ru.zippospb.restvote.repository.DishRepository;

import static ru.zippospb.restvote.DishTestData.*;
import static ru.zippospb.restvote.RestaurantTestData.REST1;
import static ru.zippospb.restvote.RestaurantTestData.REST1_ID;

class DataJpaDishRepositoryImplTest extends AbstractRepositoryTest {

    @Autowired
    DishRepository repository;

    @Test
    void testGet() {
        Dish dish = repository.get(REST1_ID, REST1_DISH1_ID);
        assertMatch(dish, REST1_DISH1);
    }

    @Test
    void testGetAll() {
        assertMatch(repository.getAll(REST1_ID), REST1_DISHES);
    }

    @Test
    void testSave() {
        Dish created = getNewDish();
        created.setRestaurant(REST1);
        repository.save(created);
        assertMatch(repository.getAll(REST1_ID), REST1_DISH1, REST1_DISH2, REST1_DISH3, REST1_DISH4, created);
    }

    @Test
    void testDelete() {
        repository.delete(REST1_DISH1_ID, REST1_ID);
        assertMatch(repository.getAll(REST1_ID), REST1_DISH2, REST1_DISH3, REST1_DISH4);
    }
}