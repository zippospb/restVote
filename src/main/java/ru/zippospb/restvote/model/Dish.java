package ru.zippospb.restvote.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.validation.constraints.NotNull;


//TODO возможно сделаю историю меню ресторанов
@Entity
@Table(name = "dishes")
public class Dish extends AbstractNamedEntity {

    @NotNull
    @Range(min = 10)
    private Integer price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonBackReference
    private Restaurant restaurant;

    public Dish() {}

    Dish(Dish dish, Restaurant newRest) {
        this(dish.id, dish.name, newRest, dish.price);
    }

    public Dish(Integer id, String name, Restaurant restaurant, int price) {
        super(id, name);
        this.restaurant = restaurant;
        this.price = price;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    @Override
    public String toString() {
        return "Dish{" +
                super.toString() +
                ", price=" + price +
                ", restaurant=" + restaurant.getId() +
                "} ";
    }
}
