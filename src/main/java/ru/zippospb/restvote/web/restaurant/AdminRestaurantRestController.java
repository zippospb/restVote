package ru.zippospb.restvote.web.restaurant;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.zippospb.restvote.model.Restaurant;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = AdminRestaurantRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class AdminRestaurantRestController extends AbstractRestaurantController {
    final static String REST_URL = "/rest/admin/restaurants";

    @Override
    @GetMapping
    public List<Restaurant> getAll() {
        return super.getAll();
    }

    @Override
    @GetMapping("/{id}")
    public Restaurant get(@PathVariable("id") int id) {
        return super.get(id);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Restaurant> createWithLocation(@RequestBody Restaurant restaurant) {
        Restaurant created = super.create(restaurant);

        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "{id}")
                .buildAndExpand(created.getId())
                .toUri();

        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@RequestBody Restaurant restaurant, @PathVariable("id") int id) {
        super.update(restaurant,id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") int id) {
        super.delete(id);
    }
}
