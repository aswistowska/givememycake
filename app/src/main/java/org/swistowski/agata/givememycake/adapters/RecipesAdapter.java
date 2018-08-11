package org.swistowski.agata.givememycake.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.swistowski.agata.givememycake.R;
import org.swistowski.agata.givememycake.model.Recipe;

import java.util.ArrayList;
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
        ImageView recipeImageView = view.findViewById(R.id.recipe_image_view);
        return new RecipesAdapterViewHolder(view, recipeNameTextView, recipeImageView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipesAdapterViewHolder holder, int position) {
        Recipe recipe = mRecipes.get(position);
        holder.mRecipeNameTextView.setText(recipe.getName());
        holder.mRecipeImageView.setImageResource(getPlaceholderImage(position));
    }

    @Override
    public int getItemCount() {
        if(mRecipes == null) return 0;
        return mRecipes.size();
    }

    public int getPlaceholderImage(int position){
        List<Integer> placeholderImages = new ArrayList<>();
        placeholderImages.add(R.drawable.cake1);
        placeholderImages.add(R.drawable.cake2);
        placeholderImages.add(R.drawable.cake3);
        placeholderImages.add(R.drawable.cake4);

        return placeholderImages.get(position%placeholderImages.size());
    }

    public class RecipesAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public final TextView mRecipeNameTextView;
        public final ImageView mRecipeImageView;

        public RecipesAdapterViewHolder(View itemView, TextView recipeNameTextView, ImageView mRecipeImageView) {
            super(itemView);
            this.mRecipeNameTextView = recipeNameTextView;
            this.mRecipeImageView = mRecipeImageView;
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
