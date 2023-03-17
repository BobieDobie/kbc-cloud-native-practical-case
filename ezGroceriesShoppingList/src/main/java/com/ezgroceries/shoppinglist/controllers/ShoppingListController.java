package com.ezgroceries.shoppinglist.controllers;

import com.ezgroceries.shoppinglist.entities.CocktailEntity;
import com.ezgroceries.shoppinglist.entities.ShoppingListEntity;
import com.ezgroceries.shoppinglist.models.Cocktail;
import com.ezgroceries.shoppinglist.services.ShoppingListService;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
public class ShoppingListController {
    private final ShoppingListService shoppingListService;

    public ShoppingListController(ShoppingListService shoppingListService) {
        this.shoppingListService = shoppingListService;
    }

    @GetMapping("/shopping-lists")
    @JsonView(ShoppingListEntity.ShoppingView.class)
    public List<ShoppingListEntity> getAllLists() {
        return shoppingListService.getAllLists();
    }

    @PostMapping("/shopping-lists")
    public ResponseEntity<Void> addList(@RequestBody ShoppingListEntity shoppingList) {

        shoppingListService.create(shoppingList);

        // location of created object
        URI location = ServletUriComponentsBuilder.fromCurrentRequestUri()
                .path("/{name}")
                .buildAndExpand(shoppingList.getName())
                .toUri();

        return ResponseEntity
                .created(location)
                .build();
    }

    @GetMapping("/shopping-lists/{shoppingListId}")
    public ShoppingListEntity getShoppingList(@PathVariable UUID shoppingListId) {
        return shoppingListService.getShoppingList(shoppingListId);
    }

    @PostMapping("/shopping-lists/{shoppingListId}/cocktails")
    public ResponseEntity<Void> addCocktailToList(@PathVariable UUID shoppingListId, @RequestBody CocktailEntity cocktail) {

        shoppingListService.addCocktailToList(shoppingListId, cocktail);

        // location of new resource
        URI loc = ServletUriComponentsBuilder.fromCurrentRequestUri()
                .path("/{id}")
                .buildAndExpand(cocktail.getId())
                .toUri();

        return ResponseEntity
                .created(loc)
                .build();
    }
}
