package ru.zippospb.restvote.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "restaurants")
public class Restaurant extends AbstractNamedEntity {
    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "restaurant_id")
    @OrderBy("name ASC")
    @JsonManagedReference
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
