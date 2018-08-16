package org.swistowski.agata.givememycake.widget;

import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import org.swistowski.agata.givememycake.R;
import org.swistowski.agata.givememycake.model.Ingredient;
import org.swistowski.agata.givememycake.model.Recipe;
import org.swistowski.agata.givememycake.network.GetDataService;
import org.swistowski.agata.givememycake.network.RetrofitClientInstance;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;

public class IngredientsViewService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {

        final int recipeId = intent.getIntExtra("recipe_id", -1);

        return new RemoteViewsFactory() {
            Recipe recipe;
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
                if(recipe==null) {
                    GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
                    Call<List<Recipe>> call = service.getAllRecipes();
                    List<Recipe> recipesList = null;
                    try {
                        recipesList = call.execute().body();
                    } catch (IOException e) {
                        return 0;
                    }

                    for(int i = 0; i < recipesList.size(); i++){
                        Recipe currentRecipe = recipesList.get(i);
                        if (currentRecipe.getId() == recipeId) {
                            recipe = currentRecipe;
                        }
                    }
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
}
