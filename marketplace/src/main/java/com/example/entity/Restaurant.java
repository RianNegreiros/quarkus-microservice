package com.example.entity;

import com.example.DTO.DishDTO;
import com.example.DTO.RestaurantDTO;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.Row;
import io.vertx.mutiny.sqlclient.RowSet;
import io.vertx.mutiny.sqlclient.Tuple;

import java.util.stream.StreamSupport;

public class Restaurant {

    public Long id;

    public String name;

    public Localization localization;

    public static Multi<RestaurantDTO> findAll(PgPool pgPool) {
        Uni<RowSet<Row>> preparedQuery = pgPool.query("select * from restaurant").execute();
        return unitToMulti(preparedQuery);
    }

    public static Multi<RestaurantDTO> findAll(PgPool client, Long idRestaurant) {
        Uni<RowSet<Row>> preparedQuery = client
                .preparedQuery("SELECT * FROM dish where dish.restaurant_id = $1 ORDER BY nome ASC").execute(
                        Tuple.of(idRestaurant));
        return unitToMulti(preparedQuery);
    }

    private static Multi<RestaurantDTO> unitToMulti(Uni<RowSet<Row>> queryResult) {
        return queryResult.onItem()
                .transformToMulti(set -> Multi.createFrom().items(() -> {
                    return StreamSupport.stream(set.spliterator(), false);
                }))
                .onItem().transform(RestaurantDTO::from);
    }

    public static Uni<RestaurantDTO> findById(PgPool client, Long id) {
        return client.preparedQuery("SELECT * FROM restaurant WHERE id = $1").execute(Tuple.of(id))
                .map(RowSet::iterator)
                .map(iterator -> iterator.hasNext() ? RestaurantDTO.from(iterator.next()) : null);
    }
}
