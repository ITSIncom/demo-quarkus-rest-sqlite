package it.itsincom.persistence.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "items")
public class Item {

    @Id
    @GeneratedValue
    public Long id;

    @Column(nullable = false)
    public String name;

    public String description;
}
