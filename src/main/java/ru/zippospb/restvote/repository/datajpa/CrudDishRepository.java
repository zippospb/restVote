package ru.zippospb.restvote.repository.datajpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import ru.zippospb.restvote.model.Dish;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface CrudDishRepository extends JpaRepository<Dish, Integer> {
    @Query("SELECT d FROM Dish d WHERE d.restaurantId=?1")
    List<Dish> getAllByRestaurantId(int restId);

    @Transactional
    @Modifying
    @Query("DELETE FROM Dish d WHERE d.id=?1 AND d.restaurantId=?2")
    int delete(int dishId, int restId);

    @Query("SELECT d FROM Dish d WHERE d.restaurantId=?1 AND d.id=?2")
    Optional<Dish> getById(int restId, int dishId);

    List<Dish> getAllByRestaurantIdAndDate(int restId,LocalDate date);
}
