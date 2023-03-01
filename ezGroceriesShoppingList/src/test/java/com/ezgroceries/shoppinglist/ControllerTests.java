package com.ezgroceries.shoppinglist;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

//We could refactor these tests into a test class for each controller bv: @WebMvcTest(controllers=CocktailController.class)

@WebMvcTest
public class ControllerTests {

    @Autowired
    MockMvc mockMvc;

    @Test
    public void testGetList() throws Exception {

        String list = "{\n" +
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

        mockMvc.perform(MockMvcRequestBuilders.get("/shopping-lists/{shoppingListId}", 12345).accept("application/json"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().json(list));

    }

    @Test
    public void testPostList() throws Exception {
        String shoppingList = "{\n" +
                "  \"name\": \"Stephanie's birthday\"\n" +
                "}";

        mockMvc.perform(post("/shopping-lists").content(shoppingList).contentType("application/json"))
                .andExpect(status().isCreated())
                .andExpect(content().string(""))
                .andExpect(header().string("Location", "http://localhost/shopping-lists/Stephanie's%20birthday"));
    }

    @Test
    public void testPostCocktailToList() throws Exception {

        String cocktail = "{\n" +
                "  \"cocktailId\": \"23b3d85a-3928-41c0-a533-6538a71e17c4\"\n" +
                "}";

        mockMvc.perform(post("/shopping-lists/{shoppingListId}/cocktails", 12345)
                        .content(cocktail).contentType("application/json"))
                .andExpect(status().isCreated())
                .andExpect(content().string(""))
                .andExpect(header().string("Location", "http://localhost/shopping-lists/12345/cocktails/23b3d85a-3928-41c0-a533-6538a71e17c4"));
    }

    @Test
    public void testGetAllLists() throws Exception {

        String allLists = "[\n" +
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
        String cocktails = "[\n" +
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

        mockMvc.perform(get("/cocktails").param("search", "Russian").accept("application/json"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(content().json(cocktails));
    }

}
