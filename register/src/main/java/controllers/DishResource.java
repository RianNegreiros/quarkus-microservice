package controllers;

import entities.Dish;
import entities.Restaurant;

import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Optional;

@Path("/dishes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class DishResource {

    @GET
    public List<Dish> search() {
        return Dish.listAll();
    }

    @POST
    @Transactional
    public Response add(Dish dto) {
        dto.persist();
        return Response.status(Response.Status.CREATED).build();
    }

    @PUT
    @Path("{id}")
    @Transactional
    public void update(@PathParam("id") Long id, Dish dto) {
        Optional<Dish> dishOp = Dish.findByIdOptional(id);
        if(dishOp.isEmpty()) {
            throw new NotFoundException();
        }
        Dish dish = dishOp.get();
        dish.name = dto.name;
        dish.persist();
    }

    @DELETE
    @Path("{id}")
    @Transactional
    public void delete(@PathParam("id") Long id) {
        Optional<Dish> dishOp = Dish.findByIdOptional(id);

        dishOp.ifPresentOrElse(Dish::delete, () -> {
            throw new NotFoundException();
        });
    }
}
