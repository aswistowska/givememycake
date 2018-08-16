package org.swistowski.agata.givememycake;


import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withText;


import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.swistowski.agata.givememycake.activities.RecipeListActivity;

@RunWith(AndroidJUnit4.class)
public class RecipeListActivityScreenTest {

    public static final String RECIPE_NAME = "Brownies";

    @Rule
    public ActivityTestRule<RecipeListActivity> mActivityTestRule = new ActivityTestRule<>(RecipeListActivity.class);

    @Test
    public void clickRecyclerViewItem_OpensStepListActivity() {
        onView(ViewMatchers.withId(R.id.recipe_list_recycle_view))
                .perform(RecyclerViewActions.scrollToPosition(1));
        onView(withText(RECIPE_NAME)).check(matches(isDisplayed()));
    }

}
