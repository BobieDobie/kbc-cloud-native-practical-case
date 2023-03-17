package com.ezgroceries.shoppinglist.entities;

import javax.persistence.*;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "cocktail")
public class CocktailEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String id_drink;

    private String name;

    @Convert(converter = StringSetConverter.class)
    private Set<String> ingredients;

    @ManyToMany(mappedBy = "cocktails")
    Set<ShoppingListEntity> shoppingLists;

    public CocktailEntity() {}

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getId_drink() {
        return id_drink;
    }

    public void setId_drink(String id_drink) {
        this.id_drink = id_drink;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<String> getIngredients() {
        return ingredients;
    }

    public void setIngredients(Set<String> ingredients) {
        this.ingredients = ingredients;
    }
}
