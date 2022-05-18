package controllers;

import dto.RestaurantDTO;
import entities.Restaurant;
import infra.exception.ConstraintViolationResponse;
import mapper.RestaurantMapper;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Optional;

@Path("/restaurants")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RestaurantResource {

    @Inject
    RestaurantMapper restaurantMapper;

    @GET
    public List<Restaurant> search() {
        return Restaurant.listAll();
    }

    @POST
    @Transactional
    @APIResponse(responseCode = "200", description = "Restaurant successfully created")
    @APIResponse(responseCode = "400", content = @Content(schema = @Schema(allOf = ConstraintViolationResponse.class)))
    public Response add(RestaurantDTO dto) {
        Restaurant restaurant = restaurantMapper.toRestaurant(dto);
        restaurant.persist();
        return Response.status(Response.Status.CREATED).build();
    }

    @PUT
    @Path("{id}")
    @Transactional
    public void update(@PathParam("id") Long id, RestaurantDTO dto) {
        Optional<Restaurant> restaurantOp = Restaurant.findByIdOptional(id);
        if(restaurantOp.isEmpty()) {
            throw new NotFoundException();
        }
        Restaurant restaurant = restaurantOp.get();
        restaurant.owner = dto.owner;
        restaurant.cnpj = dto.cnpj;
        restaurant.name = dto.name;
        restaurant.persist();
    }

    @DELETE
    @Path("{id}")
    @Transactional
    public void delete(@PathParam("id") Long id) {
        Optional<Restaurant> restaurantOp = Restaurant.findByIdOptional(id);

        restaurantOp.ifPresentOrElse(Restaurant::delete, () -> {
            throw new NotFoundException();
        });
    }
}
