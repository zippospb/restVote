package model;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalTime;

@Email
@Entity
@Table(name = "votes", uniqueConstraints = @UniqueConstraint(name = "vote_unique_user_idx", columnNames = {"user_id", "date"}))
public class Vote extends AbstractBaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @NotNull
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @NotNull
    private Restaurant restaurant;

    @Column(name = "date", columnDefinition = "date default now()")
    @NotNull
    private LocalDate date;

    @Column(name = "time", columnDefinition = "time default now()")
    @NotNull
    private LocalTime time;
}
