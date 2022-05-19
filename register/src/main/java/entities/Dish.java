package entities;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
public class Dish extends PanacheEntity {

    public String name;

    public String description;

    @ManyToOne
    public Restaurant restaurant;

    public BigDecimal price;
}
