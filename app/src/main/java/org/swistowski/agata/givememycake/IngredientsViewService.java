package org.swistowski.agata.givememycake;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import org.swistowski.agata.givememycake.model.Ingredient;
import org.swistowski.agata.givememycake.model.Recipe;
import org.swistowski.agata.givememycake.utils.JsonUtils;

import java.util.List;

public class IngredientsViewService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {

        int recipeId = intent.getIntExtra("recipe_id", -1);
        final Recipe recipe = findRecipe(recipeId);

        return new RemoteViewsFactory() {
            @Override
            public void onCreate() {

            }

            @Override
            public void onDataSetChanged() {

            }

            @Override
            public void onDestroy() {

            }

            @Override
            public int getCount() {
                if(recipe!=null) {
                    return recipe.getIngredients().size();
                }
                return 0;
            }

            @Override
            public RemoteViews getViewAt(int i) {
                RemoteViews views = new RemoteViews(getPackageName(), R.layout.ingredients_detail);
                Ingredient ingredient = recipe.getIngredients().get(i);
                views.setTextViewText(R.id.ingredient_quantity_text_view, Double.toString(ingredient.getQuantity()));
                views.setTextViewText(R.id.ingredient_measure_text_view, ingredient.getMeasure());
                views.setTextViewText(R.id.ingredient_name_text_view, ingredient.getName());

                return views;
            }

            @Override
            public RemoteViews getLoadingView() {
                return null;
            }

            @Override
            public int getViewTypeCount() {
                return 1;
            }

            @Override
            public long getItemId(int i) {
                return i;
            }

            @Override
            public boolean hasStableIds() {
                return true;
            }
        };
    }

    @Nullable
    private Recipe findRecipe(int recipeId) {
        List<Recipe> recipeList = JsonUtils.parseRecipeJson(getApplicationContext().getResources().getString(R.string.recipesJson));
        for (int i = 0; i < recipeList.size(); i++){
            Recipe currentRecipe = recipeList.get(i);
            if(currentRecipe.getId() == recipeId) {
                return currentRecipe;
            }
        }
        return null;
    }
}
