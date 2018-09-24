package ru.zippospb.restvote.repository.datajpa;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.zippospb.restvote.model.Restaurant;
import ru.zippospb.restvote.repository.AbstractRepositoryTest;
import ru.zippospb.restvote.repository.RestaurantRepository;

import java.util.List;

import static ru.zippospb.restvote.RestaurantTestData.*;

class DataJpaRestaurantRepositoryImplTest extends AbstractRepositoryTest {

    @Autowired
    private RestaurantRepository repository;

    @Test
    void testGetAll() {
        List<Restaurant> actual = repository.getAll();
        assertMatch(actual, REST1, REST2, REST3);
    }

    @Test
    void testGet() {
        assertMatch(repository.get(REST1_ID), REST1);
    }

    @Test
    void testCreate() {
        Restaurant newRest = getNew();
        Restaurant created = repository.save(getNew());
        newRest.setId(created.getId());
        assertMatch(repository.getAll(), REST1, REST2, REST3, newRest);
    }

    @Test
    void testUpdate() {
        Restaurant updated = new Restaurant(REST1);
        updated.setName("новый ресторан");
        repository.save(updated);
        assertMatch(repository.get(updated.getId()), updated);
    }

    @Test
    void testDelete() {
    }
}