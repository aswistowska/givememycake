package org.swistowski.agata.givememycake.widget;

import android.app.Activity;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import org.swistowski.agata.givememycake.R;
import org.swistowski.agata.givememycake.activities.RecipeListActivity;
import org.swistowski.agata.givememycake.adapters.RecipeSelectAdapter;
import org.swistowski.agata.givememycake.model.Recipe;
import org.swistowski.agata.givememycake.network.GetDataService;
import org.swistowski.agata.givememycake.network.RetrofitClientInstance;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * The configuration screen for the {@link IngredientsWidgetProvider IngredientsWidgetProvider} AppWidget.
 */
public class IngredientsWidgetProviderConfigureActivity extends Activity {

    private static final String PREFS_NAME = "org.swistowski.agata.givememycake.widget.IngredientsWidgetProvider";
    private static final String PREF_PREFIX_KEY = "appwidget_";
    private Spinner mSpiner;
    ArrayAdapter<Recipe> mAdapter;
    int mAppWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID;
    View.OnClickListener mOnClickListener = new View.OnClickListener() {
        public void onClick(View v) {
            final Context context = IngredientsWidgetProviderConfigureActivity.this;

            Recipe recipe = (Recipe) mSpiner.getSelectedItem();
            saveRecipePref(context, mAppWidgetId, recipe.getId());

            // It is the responsibility of the configuration activity to update the app widget
            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
            IngredientsWidgetProvider.updateAppWidget(context, appWidgetManager, mAppWidgetId);

            // Make sure we pass back the original appWidgetId
            Intent resultValue = new Intent();
            resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, mAppWidgetId);
            setResult(RESULT_OK, resultValue);
            finish();
        }
    };

    public IngredientsWidgetProviderConfigureActivity() {
        super();
    }

    // Write the prefix to the SharedPreferences object for this widget
    static void saveRecipePref(Context context, int appWidgetId, int recipeId) {
        SharedPreferences.Editor prefs = context.getSharedPreferences(PREFS_NAME, 0).edit();
        prefs.putInt(PREF_PREFIX_KEY + appWidgetId, recipeId);
        prefs.apply();
    }

    // Read the prefix from the SharedPreferences object for this widget.
    // If there is no preference saved, get the default from a resource
    static int loadRecipePref(Context context, int appWidgetId) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, 0);
        int recipeId = prefs.getInt(PREF_PREFIX_KEY + appWidgetId, -1);
        return recipeId;
    }

    static void deleteRecipePref(Context context, int appWidgetId) {
        SharedPreferences.Editor prefs = context.getSharedPreferences(PREFS_NAME, 0).edit();
        prefs.remove(PREF_PREFIX_KEY + appWidgetId);
        prefs.apply();
    }

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);

        // Set the result to CANCELED.  This will cause the widget host to cancel
        // out of the widget placement if the user presses the back button.
        setResult(RESULT_CANCELED);

        setContentView(R.layout.ingredients_widget_provider_configure);
        findViewById(R.id.add_button).setOnClickListener(mOnClickListener);

        // Find the widget id from the intent.
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            mAppWidgetId = extras.getInt(
                    AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
        }

        // If this activity was started with an intent without an app widget ID, finish with an error.
        if (mAppWidgetId == AppWidgetManager.INVALID_APPWIDGET_ID) {
            finish();
            return;
        }

        mSpiner = findViewById(R.id.recipe_spinner);

        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Call<List<Recipe>> call = service.getAllRecipes();
        call.enqueue(new Callback<List<Recipe>>() {
            @Override
            public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
                List<Recipe> recipesList = response.body();
                mAdapter = new RecipeSelectAdapter(getApplicationContext(), recipesList);
                mAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                mSpiner.setAdapter(mAdapter);
            }

            @Override
            public void onFailure(Call<List<Recipe>> call, Throwable t) {
            }
        });

        //mSpiner.setSelection(adapter.getPosition( ))
        //mAppWidgetText.setText(loadTitlePref(IngredientsWidgetProviderConfigureActivity.this, mAppWidgetId));
    }
}

