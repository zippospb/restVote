package ru.zippospb.restvote.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name = "votes", uniqueConstraints = @UniqueConstraint(name = "vote_unique_user_date_idx", columnNames = {"user_id", "date"}))
public class Vote extends AbstractBaseEntity {

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @NotNull
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "restaurant_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @NotNull
    private Restaurant restaurant;

    @Column(name = "date", columnDefinition = "date default now()")
    @NotNull
    private LocalDate date = LocalDate.now();

    @Column(name = "time", columnDefinition = "time default now()")
    @NotNull
    private LocalTime time = LocalTime.now().truncatedTo(ChronoUnit.MINUTES);

    public Vote(User user, Restaurant restaurant) {
        this.user = user;
        this.restaurant = restaurant;
    }

    public Vote(Integer id, User user, Restaurant restaurant, LocalDate date, LocalTime time) {
        super(id);
        this.user = user;
        this.restaurant = restaurant;
        this.date = date;
        this.time = time;
    }

    @Override
    public String toString() {
        return "Vote{" +
                super.toString() +
                ", user=" + user.getId() +
                ", restaurant=" + restaurant.getId() +
                ", date=" + date +
                ", time=" + time +
                "} ";
    }
}
