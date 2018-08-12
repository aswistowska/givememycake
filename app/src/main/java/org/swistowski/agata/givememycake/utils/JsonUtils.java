package org.swistowski.agata.givememycake.utils;

import android.content.Context;
import android.support.annotation.Nullable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.swistowski.agata.givememycake.R;
import org.swistowski.agata.givememycake.model.Ingredient;
import org.swistowski.agata.givememycake.model.Recipe;
import org.swistowski.agata.givememycake.model.Step;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static List<Recipe> parseRecipeJson(String json) {

        ArrayList<Recipe> recipes = new ArrayList<>();
        try {
            JSONArray recipesArray = new JSONArray(json);
            for (int i = 0; i < recipesArray.length(); i++) {
                JSONObject recipeObject = recipesArray.getJSONObject(i);
                int id = recipeObject.getInt("id");
                String name = recipeObject.getString("name");
                List<Ingredient> ingredients = new ArrayList<>();
                JSONArray ingredientsArray = recipeObject.getJSONArray("ingredients");
                for (int j = 0; j < ingredientsArray.length(); j++) {
                    JSONObject ingredientObject = ingredientsArray.getJSONObject(j);
                    double quantity = ingredientObject.getDouble("quantity");
                    String measure = ingredientObject.getString("measure");
                    String ingredientName = ingredientObject.getString("ingredient");

                    Ingredient ingredient = new Ingredient(quantity, measure, ingredientName);
                    ingredients.add(ingredient);
                }

                List<Step> steps = new ArrayList<>();
                JSONArray stepsArray = recipeObject.getJSONArray("steps");
                for (int k = 0; k < stepsArray.length(); k++) {
                    JSONObject stepObject = stepsArray.getJSONObject(k);
                    int stepId = stepObject.getInt("id");
                    String shortDescription = stepObject.getString("shortDescription");
                    String description = stepObject.getString("description");
                    URL video = null;
                    try {
                        video = new URL(stepObject.getString("videoURL"));
                    } catch (MalformedURLException e) {
                        // e.printStackTrace();
                    }
                    URL thumbnail = null;
                    try {
                        thumbnail = new URL(stepObject.getString("thumbnailURL"));
                    } catch (MalformedURLException e) {
                        // e.printStackTrace();
                    }

                    Step step = new Step(stepId, shortDescription, description, video, thumbnail);
                    steps.add(step);
                }

                Recipe recipe = new Recipe(id, name, ingredients, steps);
                recipes.add(recipe);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return recipes;
    }

    @Nullable
    static public Recipe findRecipe(Context context, int recipeId) {
        List<Recipe> recipeList = JsonUtils.parseRecipeJson(context.getResources().getString(R.string.recipesJson));
        for (int i = 0; i < recipeList.size(); i++){
            Recipe currentRecipe = recipeList.get(i);
            if(currentRecipe.getId() == recipeId) {
                return currentRecipe;
            }
        }
        return null;
    }
}
