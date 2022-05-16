package Controllers;

import entities.Restaurant;

import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

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

}
