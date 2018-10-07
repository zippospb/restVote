package ru.zippospb.restvote.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.zippospb.restvote.model.Dish;
import ru.zippospb.restvote.util.exception.NotFoundException;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static ru.zippospb.restvote.DishTestData.*;
import static ru.zippospb.restvote.RestaurantTestData.REST1_ID;

class DishServiceImplTest extends AbstractServiceTest {

    @Autowired
    private DishService service;

    @Test
    void testGet() {
        Dish dish = service.get(REST1_ID, REST1_DISH1_ID);
        assertMatch(dish, REST1_DISH1);
    }

    @Test
    void testGetNotFound() {
        assertThrows(NotFoundException.class, () -> service.get(100004, 100006));
    }

    @Test
    void testGetAll() {
        assertMatch(service.getAll(REST1_ID), REST1_DISH1, REST1_DISH2, REST1_DISH3, REST1_DISH4, REST1_OLD_DISH);
    }

    @Test
    void testSave() {
        Dish created = getNewDish();
        created.setRestaurantId(REST1_ID);
        service.create(created);
        assertMatch(service.getAll(REST1_ID), REST1_DISH1, REST1_DISH2, REST1_DISH3, REST1_DISH4, REST1_OLD_DISH, created);
    }

    @Test
    void testDelete() {
        service.delete(REST1_DISH1_ID, REST1_ID);
        assertMatch(service.getAll(REST1_ID), REST1_DISH2, REST1_DISH3, REST1_DISH4, REST1_OLD_DISH);
    }

    @Test
    void testDeleteNotFound() {
        assertThrows(NotFoundException.class, () -> service.delete(100006, 100004));
    }

    @Test
    void testGetAllByDate() {
        assertMatch(service.getAllByDate(REST1_ID, LocalDate.now()), REST1_DISHES);
    }

    @Test
    void testValidation() {
        validateRootCause(() -> service.create(new Dish(null, "  ", REST1_ID, 100)));
        validateRootCause(() -> service.create(new Dish(null, "New Restaurant", REST1_ID, null)));
        validateRootCause(() -> service.create(new Dish(null, "New Restaurant", REST1_ID, 9)));
    }
}