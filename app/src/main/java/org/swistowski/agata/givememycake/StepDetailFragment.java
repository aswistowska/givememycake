package org.swistowski.agata.givememycake;

import android.app.Activity;
import android.support.design.widget.CollapsingToolbarLayout;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.swistowski.agata.givememycake.dummy.DummyContent;
import org.swistowski.agata.givememycake.model.Recipe;
import org.swistowski.agata.givememycake.model.Step;

/**
 * A fragment representing a single Step detail screen.
 * This fragment is either contained in a {@link StepListActivity}
 * in two-pane mode (on tablets) or a {@link StepDetailActivity}
 * on handsets.
 */
public class StepDetailFragment extends Fragment {


    public static final String ARG_RECIPE = "recipe";
    public static final String ARG_STEP_ID = "stepId";


    private Recipe mRecipe;
    private Step mStep;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public StepDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_RECIPE) && getArguments().containsKey(ARG_STEP_ID)) {

            mRecipe = (Recipe) getArguments().getSerializable(ARG_RECIPE);
            int stepId = getArguments().getInt(ARG_STEP_ID);
            mStep = mRecipe.getStepById(stepId);

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
        View rootView = inflater.inflate(R.layout.step_detail, container, false);

        if (mStep != null) {
            ((TextView) rootView.findViewById(R.id.step_detail)).setText(mStep.getDescription());
        }

        return rootView;
    }
}
