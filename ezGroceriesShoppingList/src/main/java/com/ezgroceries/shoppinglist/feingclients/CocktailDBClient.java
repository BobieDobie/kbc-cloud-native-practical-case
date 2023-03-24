package com.ezgroceries.shoppinglist.feingclients;

import com.ezgroceries.shoppinglist.entities.CocktailEntity;
import com.ezgroceries.shoppinglist.models.CocktailDBResponse;
import com.ezgroceries.shoppinglist.repositories.CocktailRepository;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@FeignClient(name = "cocktailDBClient", url = "https://www.thecocktaildb.com/api/json/v1/1", fallback = CocktailDBClient.CocktailDBClientFallback.class)
public interface CocktailDBClient {

    @GetMapping(value = "search.php")
    CocktailDBResponse searchCocktails(@RequestParam("s") String search);

    @Component
    class CocktailDBClientFallback implements CocktailDBClient {
        private final CocktailRepository cocktailRepository;

        public CocktailDBClientFallback(CocktailRepository cocktailRepository) {
            this.cocktailRepository = cocktailRepository;
        }

        @Override
        public CocktailDBResponse searchCocktails(String search) {
            List<CocktailEntity> cocktailEntities = cocktailRepository.findByNameContainingIgnoreCase(search);

            CocktailDBResponse cocktailDBResponse = new CocktailDBResponse();
            cocktailDBResponse.setDrinks(cocktailEntities.stream().map(cocktailEntity -> {
                CocktailDBResponse.DrinkResource drinkResource = new CocktailDBResponse.DrinkResource();
                drinkResource.setIdDrink(cocktailEntity.getId_drink());
                drinkResource.setStrDrink(cocktailEntity.getName());
                drinkResource.setStrGlass(cocktailEntity.getGlass());
                drinkResource.setStrInstructions(cocktailEntity.getInstructions());
                drinkResource.setStrDrinkThumb(cocktailEntity.getImageLink());

                Set<String> ingredientsSet = cocktailEntity.getIngredients();
                int n = ingredientsSet.size();
                String[] ingredients = new String[n]; // maybe we could've made an array of size 15 and looped over the elements
                ingredients = ingredientsSet.toArray(ingredients);
                try {
                    drinkResource.setStrIngredient1(ingredients[0]);
                } catch (ArrayIndexOutOfBoundsException e) { /* don't need to execute anything as these exceptions are expected */}
                try {
                    drinkResource.setStrIngredient2(ingredients[1]);
                } catch (ArrayIndexOutOfBoundsException e) { }
                try {
                    drinkResource.setStrIngredient3(ingredients[2]);
                } catch (ArrayIndexOutOfBoundsException e) { }
                try {
                    drinkResource.setStrIngredient4(ingredients[3]);
                } catch (ArrayIndexOutOfBoundsException e) { }
                try {
                    drinkResource.setStrIngredient5(ingredients[4]);
                } catch (ArrayIndexOutOfBoundsException e) { }
                try {
                    drinkResource.setStrIngredient6(ingredients[5]);
                } catch (ArrayIndexOutOfBoundsException e) { }
                try {
                    drinkResource.setStrIngredient7(ingredients[6]);
                } catch (ArrayIndexOutOfBoundsException e) { }
                try {
                    drinkResource.setStrIngredient8(ingredients[7]);
                } catch (ArrayIndexOutOfBoundsException e) { }
                try {
                    drinkResource.setStrIngredient9(ingredients[8]);
                } catch (ArrayIndexOutOfBoundsException e) { }
                try {
                    drinkResource.setStrIngredient10(ingredients[9]);
                } catch (ArrayIndexOutOfBoundsException e) { }
                try {
                    drinkResource.setStrIngredient11(ingredients[10]);
                } catch (ArrayIndexOutOfBoundsException e) { }
                try {
                    drinkResource.setStrIngredient12(ingredients[11]);
                } catch (ArrayIndexOutOfBoundsException e) { }
                try {
                    drinkResource.setStrIngredient13(ingredients[12]);
                } catch (ArrayIndexOutOfBoundsException e) { }
                try {
                    drinkResource.setStrIngredient14(ingredients[13]);
                } catch (ArrayIndexOutOfBoundsException e) { }
                try {
                    drinkResource.setStrIngredient15(ingredients[14]);
                } catch (ArrayIndexOutOfBoundsException e) { }

                return drinkResource;
            }).collect(Collectors.toList()));

            return cocktailDBResponse;
        }

    }

}
