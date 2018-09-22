package ru.zippospb.restvote.model;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "restaurants")
public class Restaurant extends AbstractNamedEntity {
    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "restaurant_id")
    @OrderBy("name ASC")
//    @Size(min = 3, max = 5)
    private List<Dish> dishes;

    public Restaurant() {}

    public Restaurant(Integer id, String name, Dish... dishes) {
        super(id, name);
        this.dishes = Arrays.asList(dishes);
    }

    public Restaurant(Restaurant rest) {
        this(rest.id, rest.name);
        dishes = rest.dishes.stream()
                .map(d -> new Dish(d, this))
                .collect(Collectors.toList());
    }

    @Override
    public String toString() {
        return "Restaurant{" +
                super.toString() +
                dishes.toString() +
                "} ";
    }

    public List<Dish> getDishes() {
        return dishes;
    }

    public void setDishes(List<Dish> dishes) {
        this.dishes = dishes;
    }
}
