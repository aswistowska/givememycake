package org.swistowski.agata.givememycake.fragments;


import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import org.swistowski.agata.givememycake.R;
import org.swistowski.agata.givememycake.adapters.IngredientsListAdapter;
import org.swistowski.agata.givememycake.model.Recipe;


public class IngredientsFragment extends Fragment {

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_RECIPE = "recipe";

    private Recipe mRecipe;
    private IngredientsListAdapter mAdapter;


    public IngredientsFragment() {
        // Required empty public constructor
    }


    public static IngredientsFragment newInstance(Recipe recipe) {
        IngredientsFragment fragment = new IngredientsFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_RECIPE, recipe);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mRecipe = (Recipe) getArguments().getSerializable(ARG_RECIPE);

            Activity activity = this.getActivity();
            CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);
            if (appBarLayout != null) {
                appBarLayout.setTitle(mRecipe.getName());
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_ingredients, container, false);

        ListView listView = view.findViewById(R.id.ingredients_list_view);
        mAdapter = new IngredientsListAdapter (getContext(), mRecipe.getIngredients());
        listView.setAdapter(mAdapter);
        return view;
    }
}
