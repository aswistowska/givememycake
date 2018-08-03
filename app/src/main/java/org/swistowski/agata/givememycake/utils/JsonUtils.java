package org.swistowski.agata.givememycake.utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.swistowski.agata.givememycake.model.Recipe;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static List<Recipe> parseRecipeJson(String json) {

        ArrayList<Recipe> recipes = new ArrayList<>();
        try {
            JSONArray recipesArray = new JSONArray(json);
            for(int i = 0; i < recipesArray.length(); i++){
                JSONObject recipeObject = recipesArray.getJSONObject(i);
                int id = recipeObject.getInt("id");
                String name = recipeObject.getString("name");
                Recipe recipe = new Recipe(id, name);
                recipes.add(recipe);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return recipes;
    }
}
