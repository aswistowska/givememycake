package org.swistowski.agata.givememycake.activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.widget.Toast;

import org.swistowski.agata.givememycake.R;
import org.swistowski.agata.givememycake.adapters.RecipesAdapter;
import org.swistowski.agata.givememycake.helpers.GridSpacingItemDecoration;
import org.swistowski.agata.givememycake.model.Recipe;
import org.swistowski.agata.givememycake.network.GetDataService;
import org.swistowski.agata.givememycake.network.RetrofitClientInstance;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecipeListActivity extends AppCompatActivity implements RecipesAdapter.RecipesAdapterOnClickHandler {

    @BindView(R.id.recipe_list_recycle_view) RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecipesAdapter mRecipesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_list);
        ButterKnife.bind(this);

        int spanCount = calculateNoOfColumns(this);
        mLayoutManager = new GridLayoutManager(this, spanCount);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.addItemDecoration(new GridSpacingItemDecoration(spanCount, 50, true));

        mRecyclerView.setHasFixedSize(true);

        mRecipesAdapter = new RecipesAdapter(this);
        mRecyclerView.setAdapter(mRecipesAdapter);

        //mRecipesAdapter.setRecipes(JsonUtils.parseRecipeJson(getResources().getString(R.string.recipesJson)));
        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Call<List<Recipe>> call = service.getAllRecipes();
        call.enqueue(new Callback<List<Recipe>>() {
            @Override
            public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
                List<Recipe> recipesList = response.body();
                mRecipesAdapter.setRecipes(recipesList);
            }

            @Override
            public void onFailure(Call<List<Recipe>> call, Throwable t) {
                Toast.makeText(RecipeListActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public static int calculateNoOfColumns(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        int scalingFactor = 300;
        int noOfColumns = (int) (dpWidth / scalingFactor);
        return noOfColumns;
    }

    @Override
    public void onClick(Recipe clickedRecipe) {
        Context context = this;
        Intent intent = new Intent(this, StepListActivity.class);
        intent.putExtra("recipe", clickedRecipe);
        startActivity(intent);
    }
}
