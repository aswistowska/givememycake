package org.swistowski.agata.givememycake.widget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import org.swistowski.agata.givememycake.R;
import org.swistowski.agata.givememycake.model.Recipe;
import org.swistowski.agata.givememycake.network.GetDataService;
import org.swistowski.agata.givememycake.network.RetrofitClientInstance;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Implementation of App Widget functionality.
 * App Widget Configuration implemented in {@link IngredientsWidgetProviderConfigureActivity IngredientsWidgetProviderConfigureActivity}
 */
public class IngredientsWidgetProvider extends AppWidgetProvider {

    static void updateAppWidget(Context context, final AppWidgetManager appWidgetManager,
                                final int appWidgetId) {

        final int recipeId = IngredientsWidgetProviderConfigureActivity.loadRecipePref(context, appWidgetId);
        // Recipe recipe = JsonUtils.findRecipe(context, recipeId);

        // Construct the RemoteViews object
        final RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.ingredients_widget_provider);
        //views.setTextViewText(R.id.appwidget_text, Integer.toString(recipeId));
        Intent intent = new Intent(context, IngredientsViewService.class);
        intent.putExtra("recipe_id", recipeId);
        views.setRemoteAdapter(R.id.ingredients_widget_list_view, intent);

        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Call<List<Recipe>> call = service.getAllRecipes();
        call.enqueue(new Callback<List<Recipe>>() {
            @Override
            public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
                List<Recipe> recipes = response.body();
                for(int i=0;i<recipes.size();i++){
                    if(recipes.get(i).getId() == recipeId) {
                        views.setTextViewText(R.id.widget_recipe_name_text_view, recipes.get(i).getName());
                        appWidgetManager.updateAppWidget(appWidgetId, views);
                    }
                }

            }

            @Override
            public void onFailure(Call<List<Recipe>> call, Throwable t) {

            }
        });

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        // When the user deletes the widget, delete the preference associated with it.
        for (int appWidgetId : appWidgetIds) {
            IngredientsWidgetProviderConfigureActivity.deleteRecipePref(context, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

}

