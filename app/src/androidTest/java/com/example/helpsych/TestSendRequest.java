package com.example.helpsych;

import android.content.Intent;
import android.view.View;

import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.rule.ActivityTestRule;

import com.example.helpsych.Activity.PopupDetailUser;
import com.example.helpsych.R;

import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

public class TestSendRequest {

    @Rule
    public ActivityTestRule<PopupDetailUser> mActivityRule =
            new ActivityTestRule<PopupDetailUser>(PopupDetailUser.class){

                @Override
                protected Intent getActivityIntent() {
                    Intent intent = new Intent();
                    intent.putExtra("visit_user_id", "MHoxV5CsFbckZdgE1ZMtZ3yxEab2");
                    return intent;
                }
            };

    @Test
    public void enviarSolicitudaEspecialista(){
        //SendMessageRequestButton = (Button) findViewById(R.id.send_message_request_button);
        onView(withId(R.id.send_message_request_button)).perform(click());
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
