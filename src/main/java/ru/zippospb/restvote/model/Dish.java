package ru.zippospb.restvote.model;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "dishes")
public class Dish extends AbstractNamedEntity {

    private int price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Restaurant restaurant;

    public Dish() {}

    public Dish(Dish dish, Restaurant newRest) {
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
