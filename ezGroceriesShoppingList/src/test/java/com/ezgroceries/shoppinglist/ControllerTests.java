package com.ezgroceries.shoppinglist;

import com.ezgroceries.shoppinglist.entities.CocktailEntity;
import com.ezgroceries.shoppinglist.entities.ShoppingListEntity;
import com.ezgroceries.shoppinglist.feingclients.CocktailDBClient;
import com.ezgroceries.shoppinglist.services.CocktailService;
import com.ezgroceries.shoppinglist.services.ShoppingListService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.*;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

//We could refactor these tests into a test class for each controller bv: @WebMvcTest(controllers=CocktailController.class)
// Should find a way to not constantly create cocktail & shoppinglist entities for each test
//    -> BeforeEach? or just with a constructor?: would have to get objectmapper with our shoppinglistconverter

@WebMvcTest
public class ControllerTests {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    CocktailDBClient cocktailDBClient;

    @MockBean
    ShoppingListService shoppingListService;

    @MockBean
    CocktailService cocktailService;

    CocktailEntity cocktail1;

    ShoppingListEntity shoppingList1;

    ShoppingListEntity shoppingList2;


    public ControllerTests() {
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

        ShoppingListEntity shoppingList2 = new ShoppingListEntity();
        shoppingList2.setId(UUID.fromString("6c7d09c2-8a25-4d54-a979-25ae779d2465"));
        shoppingList2.setName("My Birthday");
        shoppingList2.setCocktails(Collections.singleton(cocktail1));

        this.shoppingList2 = shoppingList2;
    }

    @Test
    public void testGetList() throws Exception {


        String list = "{\n" +
                "  \"id\": \"2dc8027b-b408-4766-b092-657a1cf513bf\",\n" +
                "  \"name\": \"Stephanie's birthday\",\n" +
                "  \"ingredients\": [\n" +
                "    \"Tequila\",\n" +
                "    \"Triple sec\",\n" +
                "    \"Lime juice\",\n" +
                "    \"Salt\",\n" +
                "    \"Blue Curacao\"\n" +
                "  ]\n" +
                "}";


        when(shoppingListService.getShoppingList(shoppingList1.getId()))
                .thenReturn(shoppingList1);

        mockMvc.perform(MockMvcRequestBuilders.get("/shopping-lists/{shoppingListId}", shoppingList1.getId()).accept("application/json"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().json(list));

    }

    @Test
    public void testPostList() throws Exception {
        String list = "{\n" +
                "  \"name\": \"Stephanie's birthday\"\n" +
                "}";

        mockMvc.perform(post("/shopping-lists").content(list).contentType("application/json"))
                .andExpect(status().isCreated())
                .andExpect(content().string(""))
                .andExpect(header().string("Location", "http://localhost/shopping-lists/Stephanie's%20birthday"));
    }

    @Test
    public void testPostCocktailToList() throws Exception {

        String cocktail = "{\n" +
                "  \"id\": \"23b3d85a-3928-41c0-a533-6538a71e17c4\"\n" +
                "}";

        mockMvc.perform(post("/shopping-lists/{shoppingListId}/cocktails", "27dd27e0-b051-4b4e-ab73-5028585d6691")
                        .content(cocktail).contentType("application/json"))
                .andExpect(status().isCreated())
                .andExpect(content().string(""))
                .andExpect(header().string("Location", "http://localhost/shopping-lists/27dd27e0-b051-4b4e-ab73-5028585d6691/cocktails/23b3d85a-3928-41c0-a533-6538a71e17c4"));
    }

    @Test
    public void testGetAllLists() throws Exception {

        String allLists = "[\n" +
                "  {\n" +
                "    \"id\": \"2dc8027b-b408-4766-b092-657a1cf513bf\",\n" +
                "    \"name\": \"Stephanie's birthday\",\n" +
                "    \"ingredients\": [\n" +
                "      \"Tequila\",\n" +
                "      \"Triple sec\",\n" +
                "      \"Lime juice\",\n" +
                "      \"Salt\",\n" +
                "      \"Blue Curacao\"\n" +
                "    ]\n" +
                "  }\n" +
                "]";

        when(shoppingListService.getAllLists()).thenReturn(Collections.singletonList(shoppingList1));

        mockMvc.perform(MockMvcRequestBuilders.get("/shopping-lists").accept("application/json"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().json(allLists));
    }

    //**********************
    //CocktailController tests
    //**********************

    @Test
    public void testGetAllCocktails() throws Exception {

        // expected return; could've made variables for the drink parameters to avoid copy pasting them twice
        String cocktails = "[\n" +
                "  {\n" +
                "    \"id\": \"06198326-3649-4ceb-8a64-f61db9452459\",\n" +
                "    \"id_drink\": \"12345\"," +
                "    \"name\": \"test Russian\",\n" +
                "    \"ingredients\": [\n" +
                "      \"Tequila\",\n" +
                "      \"Triple sec\",\n" +
                "      \"Lime juice\",\n" +
                "      \"Salt\",\n" +
                "      \"Blue Curacao\"\n" +
                "    ]\n" +
                "  }\n" +
                "]";

        when(cocktailService.allCocktails("test")).thenReturn(Collections.singletonList(cocktail1));

        // perform test
        mockMvc.perform(get("/cocktails").param("search", "test").accept("application/json"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(content().json(cocktails));

    }

}
