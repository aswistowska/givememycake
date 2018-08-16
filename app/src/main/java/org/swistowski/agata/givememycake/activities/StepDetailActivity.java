package org.swistowski.agata.givememycake.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import org.swistowski.agata.givememycake.R;
import org.swistowski.agata.givememycake.fragments.IngredientsFragment;
import org.swistowski.agata.givememycake.fragments.StepDetailFragment;
import org.swistowski.agata.givememycake.model.Recipe;
import org.swistowski.agata.givememycake.model.Step;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * An activity representing a single Step detail screen. This
 * activity is only used on narrow width devices. On tablet-size devices,
 * item details are presented side-by-side with a list of items
 * in a {@link StepListActivity}.
 */
public class StepDetailActivity extends AppCompatActivity {

    private Recipe mRecipe;
    private Step mStep;
    @BindView(R.id.detail_toolbar) Toolbar toolbar;
    @BindView(R.id.previous_button) Button prevButton;
    @BindView(R.id.next_button) Button nextButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_detail);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        // Show the Up button in the action bar.
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        // savedInstanceState is non-null when there is fragment state
        // saved from previous configurations of this activity
        // (e.g. when rotating the screen from portrait to landscape).
        // In this case, the fragment will automatically be re-added
        // to its container so we don't need to manually add it.
        // For more information, see the Fragments API guide at:
        //
        // http://developer.android.com/guide/components/fragments.html
        //
        int stepId = getIntent().getIntExtra(StepDetailFragment.ARG_STEP_ID, 0);
        mRecipe = (Recipe) getIntent().getSerializableExtra(StepDetailFragment.ARG_RECIPE);
        mStep = mRecipe.getStepById(stepId);

        if (savedInstanceState == null) {

            if (stepId == -1) {
                IngredientsFragment fragment = IngredientsFragment.newInstance(mRecipe);
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.step_detail_container, fragment)
                        .commit();

            } else {
                Bundle arguments = new Bundle();

                arguments.putSerializable(StepDetailFragment.ARG_RECIPE, mRecipe);
                arguments.putInt(StepDetailFragment.ARG_STEP_ID, stepId);

                StepDetailFragment fragment = new StepDetailFragment();
                fragment.setArguments(arguments);
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.step_detail_container, fragment)
                        .commit();
            }

            final int currentStep = mRecipe.getSteps().indexOf(mStep);
            if (currentStep == 0 || stepId == -1) {
                prevButton.setVisibility(View.INVISIBLE);
            }
            if (currentStep == mRecipe.getSteps().size() - 1 || stepId == -1) {
                nextButton.setVisibility(View.INVISIBLE);
            }

            prevButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showStep(currentStep - 1);
                }
            });

            nextButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showStep(currentStep + 1);
                }
            });
        }
    }

    private void showStep(int stepToShow) {
        Context context = this;
        Step step = mRecipe.getSteps().get(stepToShow);
        Intent intent = new Intent(context, StepDetailActivity.class);
        intent.putExtra(StepDetailFragment.ARG_RECIPE, mRecipe);
        intent.putExtra(StepDetailFragment.ARG_STEP_ID, step.getId());

        context.startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            // This ID represents the Home or Up button. In the case of this
            // activity, the Up button is shown. For
            // more details, see the Navigation pattern on Android Design:
            //
            // http://developer.android.com/design/patterns/navigation.html#up-vs-back
            //
            Intent intent = new Intent(this, StepListActivity.class);
            intent.putExtra("recipe", mRecipe);
            navigateUpTo(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, StepListActivity.class);
        intent.putExtra("recipe", mRecipe);
        navigateUpTo(intent);
    }
}
