package ru.zippospb.restvote.web.restaurant;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.zippospb.restvote.to.RestaurantTo;

import java.util.List;

@RestController
@RequestMapping(value = RestaurantRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class RestaurantRestController extends  AbstractRestaurantController {
    final static String REST_URL = "/rest/restaurants";

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
}
