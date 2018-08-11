package org.swistowski.agata.givememycake.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

import org.swistowski.agata.givememycake.R;
import org.swistowski.agata.givememycake.model.Ingredient;

import java.util.List;

public class IngredientsListAdapter extends ArrayAdapter<Ingredient> {

    public IngredientsListAdapter(Context context, List<Ingredient> ingredients) {
        super(context, 0, ingredients);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View listItemView = convertView;
        if (listItemView == null){
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.ingredients_detail, parent, false);
        }

        Ingredient currentIngredient = getItem(position);

        TextView quantityTextView = listItemView.findViewById(R.id.ingredient_quantity_text_view);
        quantityTextView.setText(Double.toString(currentIngredient.getQuantity()));
        TextView measureTextView = listItemView.findViewById(R.id.ingredient_measure_text_view);
        measureTextView.setText(currentIngredient.getMeasure());
        TextView ingredientNameTextView = listItemView.findViewById(R.id.ingredient_name_text_view);
        ingredientNameTextView.setText(currentIngredient.getName());

        return listItemView;
    }

    @Override
    public int getCount() {
        return super.getCount();
    }
}
