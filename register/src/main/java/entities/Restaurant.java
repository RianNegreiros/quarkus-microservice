package entities;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table
public class Restaurant extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

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
