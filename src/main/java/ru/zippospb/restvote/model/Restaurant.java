package ru.zippospb.restvote.model;

import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Table(name = "restaurants")
public class Restaurant extends AbstractNamedEntity {
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "restaurant_id")
    @OrderBy("name ASC")
    @Where(clause = "date=CURRENT_DATE")
    private List<Dish> dishes;

    public Restaurant() {}

    public Restaurant(Integer id, String name, Dish... dishes) {
        super(id, name);
        this.dishes = Arrays.asList(dishes);
    }

    public Restaurant(Restaurant rest) {
        this(rest.id, rest.name);
        dishes = rest.dishes.stream()
                .map(d -> new Dish(d, id))
                .collect(Collectors.toList());
    }

    @Override
    public String toString() {
        return "Restaurant{" +
                super.toString() +
                (dishes != null ? dishes.toString() : "dishes=[]") +
                "} ";
    }

    public List<Dish> getDishes() {
        return dishes;
    }

    public void setDishes(List<Dish> dishes) {
        this.dishes = dishes;
    }
}
