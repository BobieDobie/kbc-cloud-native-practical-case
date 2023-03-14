package com.ezgroceries.shoppinglist.entities;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "shopping_list")
public class ShoppingListEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String name;

    public ShoppingListEntity() {}

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
