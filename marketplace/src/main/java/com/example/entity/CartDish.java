package com.example.entity;

import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.Row;
import io.vertx.mutiny.sqlclient.Tuple;

import java.util.ArrayList;
import java.util.List;

public class CartDish {

    public String client;

    public Long dish;

    public static Uni<Long> save(PgPool pgPool, String client, Long dish) {
        return pgPool.preparedQuery("INSERT INTO dish_client (client, dish) VALUES ($1, $2) RETURNING (client)").execute(
                        Tuple.of(client, dish))

                .map(pgRowSet -> pgRowSet.iterator().next().getLong("client"));
    }

    public static Uni<List<CartDish>> findCart(PgPool pgPool, String client) {
        return pgPool.preparedQuery("select * from dish_client where client = $1 ").execute(Tuple.of(client))
                .map(pgRowSet -> {
                    List<CartDish> list = new ArrayList<>(pgRowSet.size());
                    for (Row row : pgRowSet) {
                        list.add(toDishCart(row));
                    }
                    return list;
                });
    }

    private static CartDish toDishCart(Row row) {
        CartDish pc = new CartDish();
        pc.client = row.getString("client");
        pc.dish = row.getLong("dish");
        return pc;
    }

    public static Uni<Boolean> delete(PgPool pgPool, String client) {
        return pgPool.preparedQuery("DELETE FROM dish_client WHERE client = $1").execute(Tuple.of(client))
                .map(pgRowSet -> pgRowSet.rowCount() == 1);

    }
}
