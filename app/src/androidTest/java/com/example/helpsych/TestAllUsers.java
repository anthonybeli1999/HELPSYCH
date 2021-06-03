package com.example.helpsych;

import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.rule.ActivityTestRule;

import com.example.helpsych.Activity.AllUserActivity;
import com.example.helpsych.R;

import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.isRoot;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

public class TestAllUsers {
    //find_friends_recycler_list
    @Rule
    public ActivityTestRule<AllUserActivity> mActivityRule = new ActivityTestRule<>(AllUserActivity.class);

    @Test
    public void mostrarListaEspecialistas() {
        onView(isRoot()).perform(waitFor(2000));
    }

    @Test
    public void mostrarDetalleEspecialista(){
        onView(isRoot()).perform(waitFor(2000));
        onView(withId(R.id.find_friends_recycler_list))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
    }
    public static ViewAction waitFor(long delay) {
        return new ViewAction() {
            @Override public Matcher<android.view.View> getConstraints() {
                return ViewMatchers.isRoot();
            }

            @Override public String getDescription() {
                return "wait for " + delay + "milliseconds";
            }

            @Override
            public void perform(UiController uiController, android.view.View view) {
                uiController.loopMainThreadForAtLeast(delay);
            }
        };
    }

}
