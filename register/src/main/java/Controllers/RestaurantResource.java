package Controllers;

import entities.Restaurant;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.Optional;

@Path("/restaurants")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RestaurantResource {

    @GET
    public List<Restaurant> search() {
        return Restaurant.listAll();
    }

    @POST
    @Transactional
    public void add(Restaurant dto) {
        dto.persist();
    }

    @PUT
    @Path("{id}")
    @Transactional
    public void update(@PathParam("id") Long id, Restaurant dto) {
        Optional<Restaurant> restaurantOp = Restaurant.findByIdOptional(id);
        if(restaurantOp.isEmpty()) {
            throw new NotFoundException();
        }
        Restaurant restaurant = restaurantOp.get();
        restaurant.name = dto.name;
        restaurant.persist();
    }
}
