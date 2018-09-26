package ru.zippospb.restvote.web.restaurant;

import org.hibernate.Hibernate;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.zippospb.restvote.model.Restaurant;
import ru.zippospb.restvote.model.Vote;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = ProfileRestaurantRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class ProfileRestaurantRestController extends  AbstractRestaurantController {
    final static String REST_URL = "/rest/profile/restaurants";

    @Override
    @GetMapping
    public List<Restaurant> getAll(){
        return super.getAll();
    }

    @Override
    @GetMapping("/{id}")
    public Restaurant get(@PathVariable("id") int id){
        return super.get(id);
    }

    @PostMapping("/{id}/votes")
    @Transactional
    public ResponseEntity<Vote> voting(@PathVariable("id") int id){
        Vote created = super.vote(id);

        URI uriOfNewResources = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}/votes/{voteId}")
                .buildAndExpand(id, created.getId())
                .toUri();

        created.setRestaurant((Restaurant) Hibernate.unproxy(created.getRestaurant()));
        return ResponseEntity.created(uriOfNewResources).body(created);
    }
}
