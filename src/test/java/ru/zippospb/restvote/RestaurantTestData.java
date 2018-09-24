package ru.zippospb.restvote;

import ru.zippospb.restvote.model.Restaurant;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.zippospb.restvote.DishTestData.*;
import static ru.zippospb.restvote.model.AbstractBaseEntity.START_SEQ;

public class RestaurantTestData {
    public static final int REST1_ID = START_SEQ + 3;

    public static final Restaurant REST1 = new Restaurant(REST1_ID, "Братья пекари");
    public static final Restaurant REST2 = new Restaurant(REST1_ID + 1, "Столовая №1");
    public static final Restaurant REST3 = new Restaurant(REST1_ID + 2, "Mcdonalds");

    static {
        REST1.setDishes(REST1_DISHES);
        REST2.setDishes(REST2_DISHES);
        REST3.setDishes(REST3_DISHES);
    }

    public static Restaurant getNew(){
        return new Restaurant(null, "New Restaurant");
    }

    public static void assertMatch(Iterable<Restaurant> actual, Iterable<Restaurant> expected) {
        assertThat(actual).usingRecursiveFieldByFieldElementComparator().isEqualTo(expected);
    }

    public static void assertMatch(Iterable<Restaurant> actual, Restaurant... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    public static void assertMatch(Restaurant actual, Restaurant expected) {
        assertThat(actual).isEqualToComparingFieldByFieldRecursively(expected);
    }
}