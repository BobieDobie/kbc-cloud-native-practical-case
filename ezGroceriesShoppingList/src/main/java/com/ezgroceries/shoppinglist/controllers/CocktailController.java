package com.ezgroceries.shoppinglist.controllers;

import com.ezgroceries.shoppinglist.entities.CocktailEntity;
import com.ezgroceries.shoppinglist.services.CocktailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CocktailController {

    private final CocktailService cocktailService;
    private static final Logger log = LoggerFactory.getLogger(CocktailController.class);

    public CocktailController(CocktailService cocktailService) {
        this.cocktailService = cocktailService;
    }

    @GetMapping("/cocktails")
    public List<CocktailEntity> listCocktails(@RequestParam("search") String search) {
        return cocktailService.allCocktails(search);
    }
}
