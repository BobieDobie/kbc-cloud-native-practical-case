package com.ezgroceries.shoppinglist.entities;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.*;

public class ShoppingListConverter extends AbstractHttpMessageConverter<ShoppingListEntity> {

    private final ObjectMapper objectMapper;

    public ShoppingListConverter(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    protected boolean supports(Class<?> clazz) {
        return ShoppingListEntity.class.isAssignableFrom(clazz);
    }

    @Override
    protected ShoppingListEntity readInternal(Class<? extends ShoppingListEntity> clazz, HttpInputMessage inputMessage) throws IOException, HttpMessageNotReadableException {
        String requestBody = toString(inputMessage.getBody());

        JsonNode jsonNode =  objectMapper.readTree(requestBody);
        String name = jsonNode.get("name").asText();

        ShoppingListEntity shoppingList = new ShoppingListEntity();
        shoppingList.setName(name);
        return shoppingList;
    }

    private static String toString(InputStream inputStream) {
        Scanner scanner = new Scanner(inputStream, "UTF-8");
        return scanner.useDelimiter("\\A").next();
    }

    @Override
    protected void writeInternal(ShoppingListEntity shoppingList, HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException {
        try {
            OutputStream outputStream = outputMessage.getBody();

            // create body: can't force order to be id, name, ingredients
            /*
            Map<String, Object> params = new HashMap<>();
            params.put("id", shoppingList.getId());
            params.put("name", shoppingList.getName());
            params.put("ingredients", shoppingList.getIngredients());
            String body = objectMapper.writeValueAsString(params);
             */
            Set<String> ingredients = shoppingList.getIngredients();
            Iterator<String> iterator = ingredients.iterator();
            String body = "{\n" +
                            "  \"id\":\"" + shoppingList.getId() + "\",\n" +
                            "  \"name\":\"" + shoppingList.getName() + "\",\n" +
                            "  \"ingredients\": [\n"; // would require [] if no cocktails in the list yet
            while (iterator.hasNext()) {
                String ingredient = iterator.next();
                if (iterator.hasNext()) {
                    body = body +
                            "    \"" + ingredient + "\",\n";
                } else {
                    body = body +
                            "    \"" + ingredient + "\"\n";
                }
            }
            body = body +
                            "  ]\n" +
                            "}";

            // output
            outputStream.write(body.getBytes());
            outputStream.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
