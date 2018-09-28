package ru.zippospb.restvote;

import ru.zippospb.restvote.model.Dish;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.zippospb.restvote.RestaurantTestData.*;
import static ru.zippospb.restvote.model.AbstractBaseEntity.START_SEQ;

public class DishTestData {
    public static final int REST1_DISH1_ID = START_SEQ + 6;

    public static final Dish REST1_DISH1 = new Dish(100006,"Булочки с повидлом", null, 50);
    public static final Dish REST1_DISH2 = new Dish(100007,	"Салат от шеф-повара", null,	200);
    public static final Dish REST1_DISH3 = new Dish(100008,"Самса с курицей", null,	150);
    public static final Dish REST1_DISH4 = new Dish(100009,	"Чай", null,	90);
    public static final Dish REST1_OLD_DISH = new Dish(100018, "Блюдо с другой датой", null, 10, LocalDate.of(2015, 1, 1));

    public static final Dish REST2_DISH1 = new Dish(100010,	"Бефстроганов", null,	230);
    public static final Dish REST2_DISH2 = new Dish(100011,	"Колбасные обрезки", null,	500);
    public static final Dish REST2_DISH3 = new Dish(100012,"Компот", null,	50);
    public static final Dish REST2_DISH4 = new Dish(100013,	"Макарошки", null,	300);
    public static final Dish REST2_DISH5 = new Dish(100014,	"Рататуй", null,	195);

    public static final Dish REST3_DISH1 = new Dish(100015, "BigMag", null,	300);
    public static final Dish REST3_DISH2 = new Dish(100016, "Coca Cola", null,	150);
    public static final Dish REST3_DISH3 = new Dish(100017, "Free", null,	120);

    public static final List<Dish> REST1_DISHES;
    public static final List<Dish> REST2_DISHES;
    public static final List<Dish> REST3_DISHES;

    static {
        REST1_DISHES = Arrays.asList(REST1_DISH1, REST1_DISH2, REST1_DISH3, REST1_DISH4);
        REST2_DISHES = Arrays.asList(REST2_DISH1, REST2_DISH2, REST2_DISH3, REST2_DISH4, REST2_DISH5);
        REST3_DISHES = Arrays.asList(REST3_DISH1, REST3_DISH2, REST3_DISH3);

        REST1_DISHES.forEach(d -> d.setRestaurant(REST1));
        REST2_DISHES.forEach(d -> d.setRestaurant(REST2));
        REST3_DISHES.forEach(d -> d.setRestaurant(REST3));
        REST1_OLD_DISH.setRestaurant(REST1);
    }

    public static Dish getNewDish() {
        Dish newDish = new Dish();
        newDish.setPrice(500);
        newDish.setName("Я новая еда");
        return newDish;
    }

    public static void assertMatch(Dish actual, Dish expected) {
        assertThat(actual).isEqualToIgnoringGivenFields(expected,"restaurant");
        assertThat(actual.getRestaurant().getId()).isEqualTo(expected.getRestaurant().getId());
    }

    public static void assertMatch(Iterable<Dish> actual, Dish... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    public static void assertMatch(Iterable<Dish> actual, Iterable<Dish> expected) {
        assertThat(actual).usingElementComparatorIgnoringFields("restaurant").isEqualTo(expected);
    }
}
