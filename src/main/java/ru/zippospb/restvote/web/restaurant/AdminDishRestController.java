package ru.zippospb.restvote.web.restaurant;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.zippospb.restvote.model.Dish;
import ru.zippospb.restvote.model.Restaurant;
import ru.zippospb.restvote.service.DishService;
import ru.zippospb.restvote.service.RestaurantService;

import javax.validation.Valid;
import java.net.URI;
import java.time.LocalDate;
import java.util.List;

import static ru.zippospb.restvote.util.ValidationUtil.*;

@RestController
@RequestMapping(value = AdminDishRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class AdminDishRestController {
    private final Logger log = LoggerFactory.getLogger(getClass());

    final static String REST_URL = "/rest/admin/restaurants/{restId}/dishes";

    private final DishService dishService;

    private final RestaurantService restService;

    @Autowired
    public AdminDishRestController(DishService dishService, RestaurantService restService) {
        this.dishService = dishService;
        this.restService = restService;
    }

    @GetMapping
    public List<Dish> getAll(@PathVariable("restId") int restId){
        log.info("getAll for restaurant {}", restId);
        return dishService.getAll(restId);
    }

    @GetMapping("/{dishId}")
    public Dish get(@PathVariable("restId") int restId, @PathVariable("dishId") int dishId){
        log.info("getDish {} for restaurant {}", dishId, restId);
        return checkNotFoundWithId(dishService.get(restId, dishId), dishId);
    }

    @PostMapping
    @Transactional
    public ResponseEntity<Dish> createWithLocation(@PathVariable("restId") int restId,
                                                   @Valid @RequestBody Dish dish){
        log.info("create {} for restaurant {}");
        checkNew(dish);
        Restaurant restaurant = restService.get(restId);
        dish.setRestaurantId(restaurant.getId());

        Dish created = dishService.create(dish);

        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(restId, created.getId())
                .toUri();

        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping("/{dishId}")
    @Transactional
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable("restId") int restId,
                       @PathVariable("dishId") int dishId,
                       @Valid @RequestBody Dish dish){
        log.info("update {} for restaurant {}", dish, dishId);

        assureIdConsistent(dish, dishId);
        Restaurant restaurant = restService.get(restId);
        dish.setRestaurantId(restaurant.getId());

        dishService.update(dish);
    }

    @DeleteMapping("{dishId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("restId") int restId, @PathVariable("dishId") int dishId){
        log.info("delete {}", dishId);
        dishService.delete(dishId, restId);
    }

    @GetMapping("/by")
    public List<Dish> getAllByDate(@PathVariable("restId") int restId,
                                   @RequestParam("date")
                                   @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date){
        log.info("getAll for restaurant {}", restId);
        return dishService.getAllByDate(restId, date);
    }
}
