package com.ezgroceries.shoppinglist;

import com.ezgroceries.shoppinglist.entities.CocktailEntity;
import com.ezgroceries.shoppinglist.entities.ShoppingListEntity;
import com.ezgroceries.shoppinglist.repositories.ShoppingListRepository;
import com.ezgroceries.shoppinglist.services.CocktailService;
import com.ezgroceries.shoppinglist.services.ShoppingListService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.servlet.ConditionalOnMissingFilterBean;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class ShoppingListServiceTest {
    @Mock
    ShoppingListRepository shoppingListRepository;
    @InjectMocks
    ShoppingListService service;

    CocktailEntity cocktail1;
    ShoppingListEntity shoppingList1;

    public ShoppingListServiceTest() {
        CocktailEntity cocktail1 = new CocktailEntity();
        cocktail1.setId(UUID.fromString("06198326-3649-4ceb-8a64-f61db9452459"));
        cocktail1.setId_drink("12345");
        cocktail1.setName("test Russian");
        Set<String> ingredients = new HashSet<>();
        ingredients.add("Tequila");
        ingredients.add("Triple sec");
        ingredients.add("Lime juice");
        ingredients.add("Salt");
        ingredients.add("Blue Curacao");
        cocktail1.setIngredients(ingredients);

        this.cocktail1 = cocktail1;

        ShoppingListEntity shoppingList1 = new ShoppingListEntity();
        UUID id = UUID.fromString("2dc8027b-b408-4766-b092-657a1cf513bf");
        shoppingList1.setId(id);
        shoppingList1.setName("Stephanie's birthday");
        shoppingList1.setCocktails(Collections.singleton(cocktail1));

        this.shoppingList1 = shoppingList1;

    }

    @Test
    public void testGetShoppingList() {
        when(shoppingListRepository.findById(shoppingList1.getId()))
                .thenReturn(Optional.of(shoppingList1));

        assertEquals(service.getShoppingList(shoppingList1.getId()), shoppingList1);
    }

    @Test
    public void testGetAllLists() {
        when(shoppingListRepository.findAll())
                .thenReturn(Collections.singletonList(shoppingList1));

        assertEquals(service.getAllLists(), Collections.singletonList(shoppingList1));
    }
}
