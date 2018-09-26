package ru.zippospb.restvote.repository.datajpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import ru.zippospb.restvote.model.Vote;

import java.time.LocalDate;
import java.time.LocalTime;

@Transactional(readOnly = true)
public interface CrudVoteRepository extends JpaRepository<Vote, Integer> {
    Vote getByRestaurantIdAndUserIdAndDate(int restId, int userId, LocalDate date);

    @Transactional
    @Modifying
    @Query("UPDATE Vote v SET v.restaurant=?1, v.user=?2, v.date=?3, v.time=?4")
    Vote saveNew(int restId, int userId, LocalDate date, LocalTime time);

    Vote getByUserIdAndDate(int userId, LocalDate date);
}
