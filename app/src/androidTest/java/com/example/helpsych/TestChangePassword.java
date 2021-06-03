package com.example.helpsych;

import android.content.Intent;

import androidx.test.rule.ActivityTestRule;

import com.example.helpsych.Activity.PasswordChangeActivity;
import com.example.helpsych.R;

import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

public class TestChangePassword {
    @Rule
    public ActivityTestRule<PasswordChangeActivity> mActivityRule =
            new ActivityTestRule<PasswordChangeActivity>(PasswordChangeActivity.class){

                @Override
                protected Intent getActivityIntent() {
                    Intent intent = new Intent();
                    intent.putExtra("visit_user_id", "yTOD4xC35fggyM65hKe7ESqmxFP2");
                    return intent;
                }
            };

    @Test
    public void cambioExitoso(){
        onView(withId(R.id.txvOldPassword))
                .perform(typeText("Computo.123"), closeSoftKeyboard());
        onView(withId(R.id.txvNewPassword1))
                .perform(typeText("Computo.1234"), closeSoftKeyboard());
        onView(withId(R.id.txvNewPassword2))
                .perform(typeText("Computo.1234"), closeSoftKeyboard());
        onView(withId(R.id.btnChangePassword)).perform(click());
    }

    @Test
    public void contrasenaAntiguaErronea()
    {
        onView(withId(R.id.txvOldPassword))
                .perform(typeText("Computo123"), closeSoftKeyboard());
        onView(withId(R.id.txvNewPassword1))
                .perform(typeText("Computo.1234"), closeSoftKeyboard());
        onView(withId(R.id.txvNewPassword2))
                .perform(typeText("Computo.1234"), closeSoftKeyboard());
        onView(withId(R.id.btnChangePassword)).perform(click());
    }

    @Test
    public void contrasenasNoCoinciden()
    {
        onView(withId(R.id.txvOldPassword))
                .perform(typeText("Computo.123"), closeSoftKeyboard());
        onView(withId(R.id.txvNewPassword1))
                .perform(typeText("Computo.1234"), closeSoftKeyboard());
        onView(withId(R.id.txvNewPassword2))
                .perform(typeText("Computo.12345"), closeSoftKeyboard());
        onView(withId(R.id.btnChangePassword)).perform(click());
    }

    @Test
    public void camposFaltantes()
    {
        onView(withId(R.id.txvOldPassword))
                .perform(typeText(""), closeSoftKeyboard());
        onView(withId(R.id.txvNewPassword1))
                .perform(typeText(""), closeSoftKeyboard());
        onView(withId(R.id.txvNewPassword2))
                .perform(typeText(""), closeSoftKeyboard());
        onView(withId(R.id.btnChangePassword)).perform(click());
    }
}
