package ru.zippospb.restvote.to;

import ru.zippospb.restvote.model.Dish;
import ru.zippospb.restvote.model.Restaurant;

import java.util.List;

public class RestaurantTo extends BaseTo {
    private String name;

    private List<Dish> dishes;

    private Integer voteCount;

    public RestaurantTo(Restaurant restaurant, Integer voteCount) {
        super(restaurant.getId());
        this.name = restaurant.getName();
        this.dishes = restaurant.getDishes();
        this.voteCount = voteCount;
    }

    public RestaurantTo() {}

    public String getName() {
        return name;
    }

    public List<Dish> getDishes() {
        return dishes;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDishes(List<Dish> dishes) {
        this.dishes = dishes;
    }

    public Integer getVoteCount() {
        return voteCount;
    }

    @Override
    public String toString() {
        return "RestaurantTo{" +
                "id=" + id +
                "name='" + name + '\'' +
                ", voteCount=" + voteCount +
                ", dishes=" + dishes +
                "} " + super.toString();
    }
}
