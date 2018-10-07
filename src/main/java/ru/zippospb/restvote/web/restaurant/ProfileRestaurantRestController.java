package ru.zippospb.restvote.web.restaurant;

import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.zippospb.restvote.model.Vote;
import ru.zippospb.restvote.to.RestaurantTo;

import java.util.List;

@RestController
@RequestMapping(value = ProfileRestaurantRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class ProfileRestaurantRestController extends  AbstractRestaurantController {
    final static String REST_URL = "/rest/profile/restaurants";

    @Override
    @GetMapping
    public List<RestaurantTo> getAllTo(){
        return super.getAllTo();
    }

    @Override
    @GetMapping("/{id}")
    public RestaurantTo getTo(@PathVariable("id") int id){
        return super.getTo(id);
    }

    @GetMapping("/{id}/votes")
    @Transactional
    public Vote voting(@PathVariable("id") int id){
        return super.vote(id);
    }
}
