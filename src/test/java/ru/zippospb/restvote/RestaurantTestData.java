package ru.zippospb.restvote;

import ru.zippospb.restvote.model.Dish;
import ru.zippospb.restvote.model.Restaurant;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.zippospb.restvote.model.AbstractBaseEntity.START_SEQ;

public class RestaurantTestData {
    public static final int REST1_ID = START_SEQ + 3;

    public static final Restaurant REST1 = new Restaurant(REST1_ID, "Братья пекари");
    public static final Restaurant REST2 = new Restaurant(REST1_ID + 1, "Столовая №1");
    public static final Restaurant REST3 = new Restaurant(REST1_ID + 2, "Mcdonalds");

    public static final List<Dish> REST1_DISHES = Arrays.asList(
            new Dish(100006,"Булочки с повидлом", REST1, 50),
            new Dish(100008,	"Салат от шеф-повара", REST1,	200),
            new Dish(100007,"Самса с курицей", REST1,	150),
            new Dish(100009,	"Чай", REST1,	90));
    public static final List<Dish> REST2_DISHES = Arrays.asList(
            new Dish(100010,	"Бефстроганов", REST2,	230),
            new Dish(100012,	"Колбасные обрезки", REST2,	500),
            new Dish(100013,"Компот", REST2,	50),
            new Dish(100014,	"Макарошки", REST2,	300),
            new Dish(100011,	"Рататуй", REST2,	195));

    public static final List<Dish> REST3_DISHES = Arrays.asList(
            new Dish(100016, "BigMag", REST3,	300),
            new Dish(100015, "Coca Cola", REST3,	150),
            new Dish(100017, "Free", REST3,	120));

    static {
        REST1.setDishes(REST1_DISHES);
        REST2.setDishes(REST2_DISHES);
        REST3.setDishes(REST3_DISHES);
    }

    public static Restaurant getNew(){
        return new Restaurant(null, "New Restaurant");
    }

    public static void assertMatch(Restaurant actual, Restaurant expected) {
        assertThat(actual).isEqualToComparingFieldByFieldRecursively(expected);
    }

    public static void assertMatch(Iterable<Restaurant> actual, Restaurant... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    public static void assertMatch(Iterable<Restaurant> actual, Iterable<Restaurant> expected) {
        assertThat(actual).usingRecursiveFieldByFieldElementComparator().isEqualTo(expected);
    }

}