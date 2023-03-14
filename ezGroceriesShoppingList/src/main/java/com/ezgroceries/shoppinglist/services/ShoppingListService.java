package com.ezgroceries.shoppinglist.services;

import com.ezgroceries.shoppinglist.repositories.ShoppingListRepository;
import org.springframework.stereotype.Service;

@Service
public class ShoppingListService {
    private final ShoppingListRepository shoppingListRepository;

    public ShoppingListService(ShoppingListRepository shoppingListRepository) {
        this.shoppingListRepository = shoppingListRepository;
    }

}
