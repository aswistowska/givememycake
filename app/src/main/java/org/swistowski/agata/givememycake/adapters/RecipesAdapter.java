package org.swistowski.agata.givememycake.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.swistowski.agata.givememycake.R;
import org.swistowski.agata.givememycake.model.Recipe;

import java.util.List;

public class RecipesAdapter extends RecyclerView.Adapter<RecipesAdapter.RecipesAdapterViewHolder>{

    List<Recipe> mRecipes;
    private final RecipesAdapterOnClickHandler mClickHandler;

    public void setRecipes(List<Recipe> recipes){
        this.mRecipes = recipes;
        notifyDataSetChanged();
    }

    public RecipesAdapter(RecipesAdapterOnClickHandler clickHandler){
        this.mClickHandler = clickHandler;
    }

    public interface RecipesAdapterOnClickHandler {
        void onClick(Recipe clickedRecipe);
    }

    @NonNull
    @Override
    public RecipesAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.recipe_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(layoutIdForListItem, viewGroup, false);
        TextView recipeNameTextView = view.findViewById(R.id.recipe_name_text_view);
        return new RecipesAdapterViewHolder(view, recipeNameTextView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipesAdapterViewHolder holder, int position) {
        Recipe recipe = mRecipes.get(position);
        holder.mRecipeNameTextView.setText(recipe.getName());
    }

    @Override
    public int getItemCount() {
        if(mRecipes == null) return 0;
        return mRecipes.size();
    }

    public class RecipesAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public final TextView mRecipeNameTextView;

        public RecipesAdapterViewHolder(View itemView, TextView recipeNameTextView) {
            super(itemView);
            this.mRecipeNameTextView = recipeNameTextView;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int adapterPosition = getAdapterPosition();
            Recipe clickedRecipe = mRecipes.get(adapterPosition);
            mClickHandler.onClick(clickedRecipe);
        }
    }
}
