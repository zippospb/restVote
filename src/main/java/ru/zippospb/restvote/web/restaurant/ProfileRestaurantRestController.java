package ru.zippospb.restvote.web.restaurant;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.zippospb.restvote.model.Restaurant;
import ru.zippospb.restvote.model.Vote;

import java.util.List;

@RestController
@RequestMapping(value = ProfileRestaurantRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class ProfileRestaurantRestController extends  AbstractRestaurantController {
    final static String REST_URL = "/rest/profile/restaurants/";

    @Override
    @GetMapping
    public List<Restaurant> getAll(){
        return super.getAll();
    }

    @Override
    @GetMapping("{id}")
    public Restaurant get(@PathVariable("id") int id){
        return super.get(id);
    }

    @PostMapping("{rest_id}/vote")
    public ResponseEntity<Vote> vote(@PathVariable int rest_id){
        return null;
    }
}
