package com.ezgroceries.shoppinglist.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CocktailController {

    private static final Logger log = LoggerFactory.getLogger(CocktailController.class);

    @GetMapping("/cocktails")
    public String listCocktails(@RequestParam("search") String search) {
        return "[\n" +
                "    {\n" +
                "        \"cocktailId\": \"23b3d85a-3928-41c0-a533-6538a71e17c4\",\n" +
                "        \"name\": \"Margerita\",\n" +
                "        \"glass\": \"Cocktail glass\",\n" +
                "        \"instructions\": \"Rub the rim of the glass with the lime slice to make the salt stick to it. Take care to moisten..\",\n" +
                "        \"image\": \"https://www.thecocktaildb.com/images/media/drink/wpxpvu1439905379.jpg\",\n" +
                "        \"ingredients\": [\n" +
                "            \"Tequila\",\n" +
                "            \"Triple sec\",\n" +
                "            \"Lime juice\",\n" +
                "            \"Salt\"\n" +
                "        ]\n" +
                "    },\n" +
                "    {\n" +
                "        \"cocktailId\": \"d615ec78-fe93-467b-8d26-5d26d8eab073\",\n" +
                "        \"name\": \"Blue Margerita\",\n" +
                "        \"glass\": \"Cocktail glass\",\n" +
                "        \"instructions\": \"Rub rim of cocktail glass with lime juice. Dip rim in coarse salt..\",\n" +
                "        \"image\": \"https://www.thecocktaildb.com/images/media/drink/qtvvyq1439905913.jpg\",\n" +
                "        \"ingredients\": [\n" +
                "            \"Tequila\",\n" +
                "            \"Blue Curacao\",\n" +
                "            \"Lime juice\",\n" +
                "            \"Salt\"\n" +
                "        ]\n" +
                "    }\n" +
                "]";
    }
}
