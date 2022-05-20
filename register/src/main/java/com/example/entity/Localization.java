package com.example.entities;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

import javax.persistence.*;

@Entity
public class Localization extends PanacheEntity {

    public Double latitude;

    public Double longitude;
}
