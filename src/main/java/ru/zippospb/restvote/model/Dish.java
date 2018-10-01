package ru.zippospb.restvote.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.Range;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;


@Entity
@Table(name = "dishes")
public class Dish extends AbstractNamedEntity {

    @NotNull
    @Range(min = 10)
    @Column(name = "price", nullable = false)
    private Integer price;

    @Column(name = "restaurant_id", nullable = false)
    private Integer restaurantId;

    @Column(name = "date")
    @NotNull
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDate date = LocalDate.now();

    public Dish() {}

    Dish(Dish dish, Integer restaurantId) {
        this(dish.id, dish.name, restaurantId, dish.price);
    }

    public Dish(Integer id, String name, Integer restaurantId, Integer price) {
        super(id, name);
        this.restaurantId = restaurantId;
        this.price = price;
    }

    public Dish(Integer id, String name, Integer restaurantId, Integer price, LocalDate date) {
        super(id, name);
        this.restaurantId = restaurantId;
        this.price = price;
        this.date = date;
    }

    public Dish(Dish dish) {
        this(dish.id, dish.name, dish.restaurantId, dish.price, dish.date);
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setRestaurantId(Integer restaurantId) {
        this.restaurantId = restaurantId;
    }

    public Integer getRestaurantId() {
        return restaurantId;
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
