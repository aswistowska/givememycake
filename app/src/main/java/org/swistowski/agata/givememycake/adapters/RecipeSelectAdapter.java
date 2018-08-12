package org.swistowski.agata.givememycake.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.swistowski.agata.givememycake.IngredientsWidgetProviderConfigureActivity;
import org.swistowski.agata.givememycake.model.Recipe;

import java.util.ArrayList;
import java.util.List;

public class RecipeSelectAdapter extends ArrayAdapter<Recipe> {

    public RecipeSelectAdapter(Context context, List<Recipe> recipes) {
        super(context, android.R.layout.simple_spinner_item , recipes);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // I created a dynamic TextView here, but you can reference your own  custom layout for each spinner item
        TextView label = (TextView) super.getView(position, convertView, parent);
        label.setTextColor(Color.BLACK);
        // Then you can get the current item using the values array (Users array) and the current position
        // You can NOW reference each method you has created in your bean object (User class)
        label.setText(getItem(position).getName());

        // And finally return your dynamic (or custom) view for each spinner item
        return label;
    }

    @Override
    public View getDropDownView(int position, View convertView,
                                ViewGroup parent) {
        TextView label = (TextView) super.getDropDownView(position, convertView, parent);
        label.setTextColor(Color.BLACK);
        label.setText(getItem(position).getName());

        return label;
    }
}
