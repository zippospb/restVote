package ru.zippospb.restvote.web.restaurant;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.zippospb.restvote.model.Dish;
import ru.zippospb.restvote.repository.DishRepository;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;

import static ru.zippospb.restvote.util.ValidationUtil.*;

@RestController
@RequestMapping(value = AdminDishRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class AdminDishRestController {
    private final Logger log = LoggerFactory.getLogger(getClass());

    final static String REST_URL = "/rest/admin/restaurants/{restId}/dishes";

    private final DishRepository repository;

    @Autowired
    public AdminDishRestController(DishRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Dish> getAll(@PathVariable("restId") int restId){
        log.info("getAll for restaurant {}", restId);
        return repository.getAll(restId);
    }

    @GetMapping("/{dishId}")
    public Dish get(@PathVariable("restId") int restId, @PathVariable("dishId") int dishId){
        log.info("get {} for restaurant {}", dishId, restId);
        return checkNotFoundWithId(repository.get(restId, dishId), dishId);
    }

    @PostMapping
    public ResponseEntity<Dish> createWithLocation(@PathVariable("restId") int restId, @RequestBody Dish dish){
        log.info("create {} for restaurant {}");
        checkNew(dish);
        Dish created = repository.save(dish);

        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId())
                .toUri();

        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping("/{dishId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable("restId") int id, @PathVariable("dishId") int dishId,
                       @RequestBody Dish dish){
        log.info("update {} for restaurant {}", dish, dishId);

        assureIdConsistent(dish, dishId);
        checkNotFoundWithId(repository.save(dish), dishId);
    }

    @DeleteMapping("{dishId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("restId") int restId, @PathVariable("dishId") int dishId){
        checkNotFoundWithId(repository.delete(dishId, restId), dishId);
    }

    @GetMapping("/by")
    public List<Dish> getAllByDate(@PathVariable("restId") int restId,
                                   @RequestParam("date")
                                   @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date){
        log.info("getAll for restaurant {}", restId);
        return repository.getAllByDate(restId, date);
    }
}
