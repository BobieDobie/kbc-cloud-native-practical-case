package com.ezgroceries.shoppinglist.controllers;

import com.ezgroceries.shoppinglist.feingclients.CocktailDBClient;
import com.ezgroceries.shoppinglist.models.Cocktail;
import com.ezgroceries.shoppinglist.models.CocktailDBResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class CocktailController {

    private final CocktailDBClient cocktailClient;
    private static final Logger log = LoggerFactory.getLogger(CocktailController.class);

    public CocktailController(CocktailDBClient cocktailClient) {
        this.cocktailClient = cocktailClient;
    }

    @GetMapping("/cocktails")
    public List<Cocktail> listCocktails(@RequestParam("search") String search) {
        // call Feign Client
        CocktailDBResponse dbResponse = cocktailClient.searchCocktails(search);
        // isolate drinks as feign responses
        List<CocktailDBResponse.DrinkResource> drinks = dbResponse.getDrinks();
        // create list of cocktails in our data model
        List<Cocktail> cocktailList = new ArrayList<>();
        for (CocktailDBResponse.DrinkResource drink : drinks) {
            // make cocktail
            Cocktail cocktail = new Cocktail();
            cocktail.setCocktailId(drink.getIdDrink());
            cocktail.setName(drink.getStrDrink());
            cocktail.setGlass(drink.getStrGlass());
            cocktail.setInstructions(drink.getStrInstructions());
            cocktail.setImage(drink.getStrDrinkThumb());
            // prepare ingredients
            List<String> ingredients = new ArrayList<>(); // for cocktail
            String[] strIngredients = new String[] {drink.getStrIngredient1(), drink.getStrIngredient2(), drink.getStrIngredient3(), // from drink
                                                    drink.getStrIngredient4(), drink.getStrIngredient5(), drink.getStrIngredient6(),
                                                    drink.getStrIngredient7(), drink.getStrIngredient8(), drink.getStrIngredient9(),
                                                    drink.getStrIngredient10(), drink.getStrIngredient11(), drink.getStrIngredient12(),
                                                    drink.getStrIngredient13(), drink.getStrIngredient14(), drink.getStrIngredient15()};
            for (String strIngredient : strIngredients) {
                if (strIngredient != null) { ingredients.add(strIngredient); }
            }
            cocktail.setIngredients(ingredients);
            cocktailList.add(cocktail);
        }
        return cocktailList;
    }
}
