package com.example.controller;

import com.example.entity.Localization;
import com.example.entity.Order;
import io.quarkus.mongodb.panache.PanacheMongoEntityBase;
import io.vertx.core.Vertx;
import io.vertx.ext.bridge.PermittedOptions;
import io.vertx.ext.web.handler.sockjs.SockJSHandler;
import io.vertx.ext.web.handler.sockjs.SockJSBridgeOptions;
import io.vertx.ext.web.Router;
import io.vertx.mutiny.core.eventbus.EventBus;
import org.bson.types.ObjectId;

import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.json.bind.JsonbBuilder;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/orders")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class OrderResource {

    @Inject
    Vertx vertx;

    @Inject
    EventBus eventBus;

    void startup(@Observes Router router) {
        router.route("/localizations").handler(localizationHandler());
    }

    private SockJSHandler localizationHandler() {
        SockJSHandler handler = SockJSHandler.create(vertx);
        PermittedOptions permitted = new PermittedOptions();
        permitted.setAddress("newLocalization");
        SockJSBridgeOptions bridgeOptions = new SockJSBridgeOptions().addInboundPermitted(permitted);
        handler.bridge(bridgeOptions);
        return handler;
    }

    @GET
    public List<PanacheMongoEntityBase> searchOrders() {
        return Order.listAll();
    }

    @POST
    @Path("{idOrder}/localization")
    public Order newLocalization(@PathParam("idOrder") String idOrder, Localization localization) {
        Order order = Order.findById(new ObjectId(idOrder));

        order.localizationDeliveryMan = localization;

        order.persistOrUpdate();
        String json = JsonbBuilder.create().toJson(localization);
        eventBus.requestAndForget("newLocalization", json);
        return order;
    }
}
