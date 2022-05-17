package entities;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Restaurant extends PanacheEntity {
    public String owner;

    public String cnpj;

    public String name;

    @ManyToOne
    public Localization localization;

    @CreationTimestamp
    public Date creationDate;

    @UpdateTimestamp
    public Date updateDate;
}
