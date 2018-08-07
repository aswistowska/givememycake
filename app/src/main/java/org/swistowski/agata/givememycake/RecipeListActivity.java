package org.swistowski.agata.givememycake;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import org.swistowski.agata.givememycake.adapters.RecipesAdapter;
import org.swistowski.agata.givememycake.model.Recipe;
import org.swistowski.agata.givememycake.utils.JsonUtils;

public class RecipeListActivity extends AppCompatActivity implements RecipesAdapter.RecipesAdapterOnClickHandler {

    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecipesAdapter mRecipesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_list);

        mRecyclerView = findViewById(R.id.recipe_list_recycle_view);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setHasFixedSize(true);

        mRecipesAdapter = new RecipesAdapter(this);
        mRecyclerView.setAdapter(mRecipesAdapter);

        mRecipesAdapter.setRecipes(JsonUtils.parseRecipeJson(getResources().getString(R.string.recipesJson)));
    }

    @Override
    public void onClick(Recipe clickedRecipe) {
        Context context = this;
        Toast.makeText(context, clickedRecipe.getName(), Toast.LENGTH_SHORT).show();
    }
}