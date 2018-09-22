package ru.zippospb.restvote.web.restaurant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.zippospb.restvote.model.Restaurant;
import ru.zippospb.restvote.repository.RestaurantRepository;

import java.net.URI;
import java.util.List;

import static ru.zippospb.restvote.util.ValidationUtil.*;

@RestController
@RequestMapping(value = "/rest", produces = MediaType.APPLICATION_JSON_VALUE)
public class RestaurantController {
    final static String ADMIN_REST_URL = "/admin/restaurants/";
    final static String PROFILE_REST_URL = "/profile/restaurants/";

    private final RestaurantRepository repository;

    @Autowired
    public RestaurantController(RestaurantRepository repository) {
        this.repository = repository;
    }

    @GetMapping(PROFILE_REST_URL)
    public List<Restaurant> getAll(){
        return repository.getAll();
    }

    @GetMapping(PROFILE_REST_URL + "{id}")
    public Restaurant get(@PathVariable("id") int id){
        return checkNotFoundWithId(repository.get(id), id);
    }

    @PostMapping(value = ADMIN_REST_URL, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Restaurant> createWithLocation(@RequestBody Restaurant restaurant){
        checkNew(restaurant);
         Restaurant created = repository.save(restaurant);

        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(PROFILE_REST_URL + "{id}")
                .buildAndExpand(created.getId())
                .toUri();

        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping(value = ADMIN_REST_URL + "{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@RequestBody Restaurant restaurant, @PathVariable("id") int id){
        assureIdConsistent(restaurant, id);
        checkNotFoundWithId(repository.save(restaurant), restaurant.getId());
    }

    @DeleteMapping(ADMIN_REST_URL + "{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") int id){
        checkNotFoundWithId(repository.delete(id), id);
    }
}
