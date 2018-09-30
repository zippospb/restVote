package ru.zippospb.restvote.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;


@Entity
@Table(name = "dishes")
public class Dish extends AbstractNamedEntity {

    @NotNull
    @Range(min = 10)
    @Column(name = "price", nullable = false)
    private Integer price;

    //TODO надо его убрать - возможно оставить только ID
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "restaurant_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonBackReference
    private Restaurant restaurant;

    @Column(name = "date")
    private LocalDate date = LocalDate.now();

    public Dish() {}

    Dish(Dish dish, Restaurant newRest) {
        this(dish.id, dish.name, newRest, dish.price);
    }

    public Dish(Integer id, String name, Restaurant restaurant, int price) {
        super(id, name);
        this.restaurant = restaurant;
        this.price = price;
    }

    public Dish(Integer id, String name, Restaurant restaurant, Integer price, LocalDate date) {
        super(id, name);
        this.restaurant = restaurant;
        this.price = price;
        this.date = date;
    }

    public Dish(Dish dish) {
        this(dish.id, dish.name, dish.restaurant, dish.price, dish.date);
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
                ", date=" + date +
                "} ";
    }
}
