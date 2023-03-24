package com.ezgroceries.shoppinglist.services;

import com.ezgroceries.shoppinglist.entities.CocktailEntity;
import com.ezgroceries.shoppinglist.feingclients.CocktailDBClient;
import com.ezgroceries.shoppinglist.models.CocktailDBResponse;
import com.ezgroceries.shoppinglist.repositories.CocktailRepository;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class CocktailService {
    private final CocktailRepository cocktailRepository;

    private final CocktailDBClient cocktailClient;

    public CocktailService(CocktailRepository cocktailRepository, CocktailDBClient cocktailClient) {
        this.cocktailRepository = cocktailRepository;
        this.cocktailClient = cocktailClient;
    }

    public List<CocktailEntity> allCocktails(String search) {
        // 1) get drinks from feign
        CocktailDBResponse dbResponse = cocktailClient.searchCocktails(search);
        List<CocktailDBResponse.DrinkResource> drinks = dbResponse.getDrinks();
        // 2) make them cocktailEntities
        List<CocktailEntity> cocktails = drinks.stream().map(drink -> changeResourceToEntity(drink)).collect(Collectors.toList());
        // 3) persist if not already present
        // 3.1) get drink ids of drinks & cocktails
        List<String> drink_ids = drinks.stream().map(drink -> drink.getIdDrink()).collect(Collectors.toList());
        List<CocktailEntity> allCocktails = (List<CocktailEntity>) cocktailRepository.findAll();
        List<String> cocktail_ids = allCocktails.stream().map(cocktail -> cocktail.getId_drink()).collect(Collectors.toList());
        // 3.2) which drink ids not in cocktaildb
        drink_ids.removeAll(cocktail_ids);
        // 3.3) save these to db
        // 3.3.1) make list of the required cocktails
        List<CocktailEntity> newCocktails = cocktails.stream().filter(cocktail -> drink_ids.contains(cocktail.getId_drink())).collect(Collectors.toList());
        // 3.3.2) save to db
        cocktailRepository.saveAll(newCocktails);
        // 4) return list
        return cocktails;

    }

    private CocktailEntity changeResourceToEntity(CocktailDBResponse.DrinkResource drink) {
        CocktailEntity cocktail = new CocktailEntity();
        cocktail.setId(UUID.randomUUID());
        cocktail.setId_drink(drink.getIdDrink());
        cocktail.setName(drink.getStrDrink());
        cocktail.setGlass(drink.getStrGlass());
        cocktail.setInstructions(drink.getStrInstructions());
        cocktail.setImageLink(drink.getStrDrinkThumb());
        // prepare ingredients
        Set<String> ingredients = new HashSet<>(); // for cocktail
        String[] strIngredients = new String[] {drink.getStrIngredient1(), drink.getStrIngredient2(), drink.getStrIngredient3(), // from drink
                drink.getStrIngredient4(), drink.getStrIngredient5(), drink.getStrIngredient6(),
                drink.getStrIngredient7(), drink.getStrIngredient8(), drink.getStrIngredient9(),
                drink.getStrIngredient10(), drink.getStrIngredient11(), drink.getStrIngredient12(),
                drink.getStrIngredient13(), drink.getStrIngredient14(), drink.getStrIngredient15()};
        for (String strIngredient : strIngredients) {
            if (strIngredient != null) { ingredients.add(strIngredient); }
        }
        cocktail.setIngredients(ingredients);

        return cocktail;
    }
}
