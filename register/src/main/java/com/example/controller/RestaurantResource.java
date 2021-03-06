package com.example.controller;

import com.example.dto.RestaurantDTO;
import com.example.entity.Restaurant;
import com.example.infra.exception.ConstraintViolationResponse;
import com.example.mapper.RestaurantMapper;
import org.eclipse.microprofile.jwt.Claim;
import org.eclipse.microprofile.jwt.Claims;
import org.eclipse.microprofile.openapi.annotations.enums.SecuritySchemeType;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.security.OAuthFlow;
import org.eclipse.microprofile.openapi.annotations.security.OAuthFlows;
import org.eclipse.microprofile.openapi.annotations.security.SecurityRequirement;
import org.eclipse.microprofile.openapi.annotations.security.SecurityScheme;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Optional;

@Path("/restaurants")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RolesAllowed("owner")
@SecurityScheme(securitySchemeName = "delivery-oauth", type = SecuritySchemeType.OAUTH2, flows = @OAuthFlows(password = @OAuthFlow(tokenUrl = "http://localhost:8180/auth/realms/delivery/protocol/openid-connect/token")))
@SecurityRequirement(name = "delivery-oauth", scopes = {})
public class RestaurantResource {

    @Inject
    RestaurantMapper restaurantMapper;

    @Inject
    @Channel("restaurants")
    Emitter<String> emmiter;

    @Inject
    @Claim(standard = Claims.sub)
    String sub;

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
        restaurant.owner = sub;
        restaurant.persist();

        Jsonb create = JsonBuilder.create();
        String json = create.toJson(restaurant);

        emmiter.send(json);
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
        if (!restaurantOp.get().owner.equals(sub)) {
            throw new ForbiddenException();
        }
        Restaurant restaurant = restaurantOp.get();
        restaurant.owner = sub;
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
