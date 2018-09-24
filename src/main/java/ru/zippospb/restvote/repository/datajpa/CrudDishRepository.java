package ru.zippospb.restvote.repository.datajpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import ru.zippospb.restvote.model.Dish;

import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface CrudDishRepository extends JpaRepository<Dish, Integer> {
    @Query("SELECT d FROM Dish d WHERE d.restaurant.id=?1")
    List<Dish> getAllByRestaurant(int restId);

    @Transactional
    @Modifying
    @Query("DELETE FROM Dish d WHERE d.id=?1 AND d.restaurant.id=?2")
    int delete(int dishId, int restId);

    @Query("SELECT d FROM Dish d WHERE d.restaurant.id=?1 AND d.id=?2")
    Optional<Dish> getById(int restId, int dishId);
}
