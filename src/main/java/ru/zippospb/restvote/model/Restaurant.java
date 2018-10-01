package ru.zippospb.restvote.model;

import org.hibernate.annotations.Formula;
import org.hibernate.annotations.Where;

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
    @Where(clause = "date=CURRENT_DATE")
    private List<Dish> dishes;

    @Formula("(SELECT COUNT(v.restaurant_id) FROM votes v WHERE v.restaurant_id=id AND v.date=CURRENT_DATE GROUP BY v.restaurant_id)")
    private Integer voteCount;

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

    public int getVoteCount() {
        return voteCount;
    }

    public List<Dish> getDishes() {
        return dishes;
    }

    public void setVoteCount(Integer voteCount) {
        this.voteCount = voteCount;
    }

    public void setDishes(List<Dish> dishes) {
        this.dishes = dishes;
    }
}
