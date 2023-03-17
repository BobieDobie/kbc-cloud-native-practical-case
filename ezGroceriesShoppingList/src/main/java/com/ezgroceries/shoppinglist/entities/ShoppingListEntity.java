package com.ezgroceries.shoppinglist.entities;

import com.ezgroceries.shoppinglist.models.Cocktail;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonView;

import javax.persistence.*;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "shopping_list")
public class ShoppingListEntity {

    public interface ShoppingView{};

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonView(ShoppingView.class)
    private UUID id;

    @JsonView(ShoppingView.class)
    private String name;

    @ManyToMany
    @JoinTable(
            name = "cocktail_shopping_list",
            joinColumns = @JoinColumn(name = "shopping_list_id"),
            inverseJoinColumns = @JoinColumn(name = "cocktail_id")
    )
    private Set<CocktailEntity> cocktails;

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

    public Set<CocktailEntity> getCocktails() {
        return cocktails;
    }

    public void setCocktails(Set<CocktailEntity> cocktails) {
        this.cocktails = cocktails;
    }

    @JsonView(ShoppingView.class)
    public Set<String> getIngredients() {
        Set<String> ingredients = new HashSet<>();
        for (CocktailEntity cocktail : cocktails) {
            for (String ingredient : cocktail.getIngredients()) {
                ingredients.add(ingredient);
            }
        }
        return ingredients;
    }
}
