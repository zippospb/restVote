package ru.zippospb.restvote.to;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.zippospb.restvote.model.Dish;
import ru.zippospb.restvote.model.Restaurant;

import java.util.List;

@Getter
@NoArgsConstructor
public class RestaurantTo extends BaseTo {
    @Setter
    private String name;

    @Setter
    private List<Dish> dishes;

    private Integer voteCount;

    public RestaurantTo(Restaurant restaurant, Integer voteCount) {
        super(restaurant.getId());
        this.name = restaurant.getName();
        this.dishes = restaurant.getDishes();
        this.voteCount = voteCount;
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
