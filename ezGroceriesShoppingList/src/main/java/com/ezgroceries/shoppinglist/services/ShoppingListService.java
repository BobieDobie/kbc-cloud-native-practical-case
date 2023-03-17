package com.ezgroceries.shoppinglist.services;

import com.ezgroceries.shoppinglist.entities.CocktailEntity;
import com.ezgroceries.shoppinglist.entities.ShoppingListEntity;
import com.ezgroceries.shoppinglist.repositories.ShoppingListRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Service
public class ShoppingListService {
    private final ShoppingListRepository shoppingListRepository;

    public ShoppingListService(ShoppingListRepository shoppingListRepository) {
        this.shoppingListRepository = shoppingListRepository;
    }

    public void create(ShoppingListEntity shoppingList) {
        shoppingListRepository.save(shoppingList);
    }

    public void addCocktailToList(UUID shoppingListId, CocktailEntity cocktail) {
        Optional<ShoppingListEntity> shoppingList = shoppingListRepository.findById(shoppingListId);

        shoppingList.ifPresent(listEntity -> {
            Set<CocktailEntity> cocktails = listEntity.getCocktails();
            cocktails.add(cocktail);
            listEntity.setCocktails(cocktails);
            shoppingListRepository.save(listEntity);
        });
    }

    public ShoppingListEntity getShoppingList(UUID shoppingListId) {
        Optional<ShoppingListEntity> shoppingListOptional = shoppingListRepository.findById(shoppingListId);
        ShoppingListEntity shoppingList = shoppingListOptional.get();
        return shoppingList;
    }

    public List<ShoppingListEntity> getAllLists() {
        return (List<ShoppingListEntity>) shoppingListRepository.findAll();
    }
}
