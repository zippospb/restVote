package ru.zippospb.restvote.service;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import ru.zippospb.restvote.model.Restaurant;
import ru.zippospb.restvote.util.exception.NotFoundException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static ru.zippospb.restvote.RestaurantTestData.*;

class RestaurantServiceImplTest extends AbstractServiceTest {

    @Autowired
    private RestaurantService service;

    @BeforeEach
    private void setup(){
        cacheManager.getCache("restaurants").clear();
    }

    @Test
    void testGetAll() {
        List<Restaurant> actual = service.getAll();
        assertMatch(actual, REST1, REST2, REST3);
    }

    @Test
    void testGet() {
        assertMatch(service.get(REST1_ID), REST1);
    }

    @Test
    void testGetNotFound() {
        assertThrows(NotFoundException.class, () -> service.get(1));
    }

    @Test
    void testCreate() {
        Restaurant newRest = getNew();
        Restaurant created = service.create(getNew());
        newRest.setId(created.getId());
        assertMatch(service.getAll(), REST1, REST2, REST3, newRest);
    }

    @Test
    void testUpdate() {
        Restaurant updated = new Restaurant(REST1);
        updated.setName("новый ресторан");
        service.create(updated);
        assertMatch(service.get(updated.getId()), updated);
    }

    @Test
    void testDelete() {
        service.delete(REST1_ID);
        assertMatch(service.getAll(), REST2, REST3);
    }

    @Test
    void testDeleteNotFound() {
        assertThrows(NotFoundException.class, () -> service.delete(1));
    }

    @Test
    void testValidation() {
        validateRootCause(() -> service.create(new Restaurant(null, "  ")));
    }
}