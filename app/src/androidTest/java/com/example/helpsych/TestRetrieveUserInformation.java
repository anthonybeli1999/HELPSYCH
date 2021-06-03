package com.example.helpsych;

import android.content.Intent;
import android.view.View;

import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.rule.ActivityTestRule;

import com.example.helpsych.Activity.MainActivity;

import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.isRoot;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

public class TestRetrieveUserInformation {
    @Rule
    public ActivityTestRule<MainActivity> mActivityRule =
            new ActivityTestRule<MainActivity>(MainActivity.class){

                @Override
                protected Intent getActivityIntent() {
                    Intent intent = new Intent();
                    intent.putExtra("currentUserID", "yTOD4xC35fggyM65hKe7ESqmxFP2");
                    return intent;
                }
            };

    @Test
    public void obtenerDatosuUsuario()
    {
        onView(withId(R.id.ProfileFragment)).perform(click());
        onView(isRoot()).perform(waitFor(2000));
    }

    public static ViewAction waitFor(long delay) {
        return new ViewAction() {
            @Override public Matcher<View> getConstraints() {
                return ViewMatchers.isRoot();
            }

            @Override public String getDescription() {
                return "wait for " + delay + "milliseconds";
            }

            @Override
            public void perform(UiController uiController, View view) {
                uiController.loopMainThreadForAtLeast(delay);
            }
        };
    }
}
