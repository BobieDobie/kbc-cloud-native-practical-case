package com.ezgroceries.shoppinglist.controllers;

import com.ezgroceries.shoppinglist.models.Cocktail;
import com.ezgroceries.shoppinglist.models.ShoppingList;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
public class ShoppingListController {

    @GetMapping("/shopping-lists")
    public String getAllLists() {
        return "[\n" +
                "  {\n" +
                "    \"shoppingListId\": \"4ba92a46-1d1b-4e52-8e38-13cd56c7224c\",\n" +
                "    \"name\": \"Stephanie's birthday\",\n" +
                "    \"ingredients\": [\n" +
                "      \"Tequila\",\n" +
                "      \"Triple sec\",\n" +
                "      \"Lime juice\",\n" +
                "      \"Salt\",\n" +
                "      \"Blue Curacao\"\n" +
                "    ]\n" +
                "  },\n" +
                "  {\n" +
                "    \"shoppingListId\": \"6c7d09c2-8a25-4d54-a979-25ae779d2465\",\n" +
                "    \"name\": \"My Birthday\",\n" +
                "    \"ingredients\": [\n" +
                "      \"Tequila\",\n" +
                "      \"Triple sec\",\n" +
                "      \"Lime juice\",\n" +
                "      \"Salt\",\n" +
                "      \"Blue Curacao\"\n" +
                "    ]\n" +
                "  }\n" +
                "]";
    }

    @PostMapping("/shopping-lists")
    public ResponseEntity<Void> addList(@RequestBody ShoppingList shoppingList) {

        URI location = ServletUriComponentsBuilder.fromCurrentRequestUri()
                .path("/{name}")
                .buildAndExpand(shoppingList.getName())
                .toUri();

        return ResponseEntity
                .created(location)
                .build();
    }

    @GetMapping("/shopping-lists/{shoppingListId}")
    public String getShoppingList(@PathVariable String shoppingListId) {
        return "{\n" +
                "  \"shoppingListId\": \"90689338-499a-4c49-af90-f1e73068ad4f\",\n" +
                "  \"name\": \"Stephanie's birthday\",\n" +
                "  \"ingredients\": [\n" +
                "    \"Tequila\",\n" +
                "    \"Triple sec\",\n" +
                "    \"Lime juice\",\n" +
                "    \"Salt\",\n" +
                "    \"Blue Curacao\"\n" +
                "  ]\n" +
                "}";
    }

    @PostMapping("/shopping-lists/{shoppingListId}/cocktails")
    public ResponseEntity<Void> addCocktailToList(@PathVariable String shoppingListId, @RequestBody Cocktail cocktail) {

        URI loc = ServletUriComponentsBuilder.fromCurrentRequestUri()
                .path("/{id}")
                .buildAndExpand(cocktail.getCocktailId())
                .toUri();

        return ResponseEntity
                .created(loc)
                .build();
    }
}
