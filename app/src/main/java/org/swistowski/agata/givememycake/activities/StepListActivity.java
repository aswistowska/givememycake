package org.swistowski.agata.givememycake.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;

import org.swistowski.agata.givememycake.fragments.IngredientsFragment;
import org.swistowski.agata.givememycake.R;
import org.swistowski.agata.givememycake.fragments.StepDetailFragment;
import org.swistowski.agata.givememycake.model.Recipe;
import org.swistowski.agata.givememycake.model.RecipeItem;
import org.swistowski.agata.givememycake.model.Step;

import java.util.ArrayList;
import java.util.List;

/**
 * An activity representing a list of Steps. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link StepDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class StepListActivity extends AppCompatActivity {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;
    private Recipe mRecipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_list);

        Intent intent = getIntent();
        if(intent.hasExtra("recipe")) {
            mRecipe = (Recipe) intent.getSerializableExtra("recipe");
            setTitle(mRecipe.getName());
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

        // Show the Up button in the action bar.
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        if (findViewById(R.id.step_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;
        }

        View recyclerView = findViewById(R.id.step_list);
        assert recyclerView != null;
        setupRecyclerView((RecyclerView) recyclerView);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            // This ID represents the Home or Up button. In the case of this
            // activity, the Up button is shown. Use NavUtils to allow users
            // to navigate up one level in the application structure. For
            // more details, see the Navigation pattern on Android Design:
            //
            // http://developer.android.com/design/patterns/navigation.html#up-vs-back
            //
            NavUtils.navigateUpFromSameTask(this);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        List<RecipeItem> recipeItemList = new ArrayList<>();
        recipeItemList.add(new RecipeItem("Recipe Ingredients", false, -1));
        for(int i = 0; i < mRecipe.getSteps().size(); i++) {
            Step step = mRecipe.getSteps().get(i);
            recipeItemList.add(new RecipeItem(step.getShortDescription(),true, step.getId()));
        }
        recyclerView.setAdapter(new SimpleItemRecyclerViewAdapter(this, recipeItemList, mTwoPane, mRecipe));
    }

    public static class SimpleItemRecyclerViewAdapter
            extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> {

        private final StepListActivity mParentActivity;
        private final List<RecipeItem> mValues;
        private final boolean mTwoPane;
        private final Recipe mRecipe;
        private final View.OnClickListener mOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RecipeItem item = (RecipeItem) view.getTag();
                if (mTwoPane) {
                    if(item.getStepId() == -1) {
                        IngredientsFragment fragment = IngredientsFragment.newInstance(mRecipe);
                        mParentActivity.getSupportFragmentManager().beginTransaction()
                                .replace(R.id.step_detail_container, fragment)
                                .commit();
                    } else {
                        Bundle arguments = new Bundle();
                        arguments.putSerializable(StepDetailFragment.ARG_RECIPE, mRecipe);
                        arguments.putInt(StepDetailFragment.ARG_STEP_ID, item.getStepId());
                        StepDetailFragment fragment = new StepDetailFragment();
                        fragment.setArguments(arguments);
                        mParentActivity.getSupportFragmentManager().beginTransaction()
                                .replace(R.id.step_detail_container, fragment)
                                .commit();
                    }
                } else {
                    Context context = view.getContext();
                    Intent intent = new Intent(context, StepDetailActivity.class);
                    intent.putExtra(StepDetailFragment.ARG_RECIPE, mRecipe);
                    intent.putExtra(StepDetailFragment.ARG_STEP_ID, item.getStepId());

                    context.startActivity(intent);
                }
            }
        };

        SimpleItemRecyclerViewAdapter(StepListActivity parent,
                                      List<RecipeItem> items,
                                      boolean twoPane, Recipe mRecipe) {
            mValues = items;
            mParentActivity = parent;
            mTwoPane = twoPane;
            this.mRecipe = mRecipe;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.step_list_content, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            holder.mContentView.setText(mValues.get(position).getLabel());

            holder.itemView.setTag(mValues.get(position));
            holder.itemView.setOnClickListener(mOnClickListener);
        }

        @Override
        public int getItemCount() {
            return mValues.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            final TextView mContentView;

            ViewHolder(View view) {
                super(view);
                mContentView = (TextView) view.findViewById(R.id.content);
            }
        }
    }
}
