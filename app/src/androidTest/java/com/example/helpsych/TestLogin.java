package com.example.helpsych;

import androidx.test.rule.ActivityTestRule;

import com.example.helpsych.Activity.LoginActivity;
import com.example.helpsych.R;

import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

public class TestLogin {
    @Rule
    public ActivityTestRule<LoginActivity> mActivityRule = new ActivityTestRule<>(LoginActivity.class);

    @Test
    public void accesoAutorizado(){
        onView(withId(R.id.txtEmail))
                .perform(typeText("edwartbalcon56@gmail.com"), closeSoftKeyboard());
        onView(withId(R.id.txtPassword))
                .perform(typeText("Computo.123"), closeSoftKeyboard());
        onView(withId(R.id.btnLogin)).perform(click());
    }

    @Test
    public void datosIncorrectos(){
        onView(withId(R.id.txtEmail))
                .perform(typeText("edwartbalcon2@gmail.com"), closeSoftKeyboard());
        onView(withId(R.id.txtPassword))
                .perform(typeText("Computo.1234"), closeSoftKeyboard());
        onView(withId(R.id.btnLogin)).perform(click());
    }

    @Test
    public void datosFaltantes()
    {
        onView(withId(R.id.txtEmail))
                .perform(typeText(""), closeSoftKeyboard());
        onView(withId(R.id.txtPassword))
                .perform(typeText(""), closeSoftKeyboard());
        onView(withId(R.id.btnLogin)).perform(click());
    }

}
