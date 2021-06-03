package com.example.helpsych;

import android.content.Intent;

import androidx.test.rule.ActivityTestRule;

import com.example.helpsych.Activity.EditProfileActivity;
import com.example.helpsych.R;

import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

public class TestEditProfile {
    @Rule
    public ActivityTestRule<EditProfileActivity> mActivityRule =
            new ActivityTestRule<EditProfileActivity>(EditProfileActivity.class){

                @Override
                protected Intent getActivityIntent() {
                    Intent intent = new Intent();
                    intent.putExtra("visit_user_id", "yTOD4xC35fggyM65hKe7ESqmxFP2");
                    return intent;
                }
            };



    @Test
    public void actualizacionExitosa(){

        onView(withId(R.id.edt_name))
                .perform(typeText("Edawarto"), closeSoftKeyboard());
        onView(withId(R.id.edt_lastname))
                .perform(typeText("Coahila"), closeSoftKeyboard());
        onView(withId(R.id.edt_sex))
                .perform(typeText("Femenino"), closeSoftKeyboard());
        onView(withId(R.id.edt_date))
                .perform(typeText("12/12/1996"), closeSoftKeyboard());
        onView(withId(R.id.edt_description))
                .perform(typeText("De pocas palabras."), closeSoftKeyboard());

        onView(withId(R.id.btn_upload)).perform(click());
    }

    @Test
    public void datosNecesariosFaltantes(){

        onView(withId(R.id.edt_name))
                .perform(typeText(""), closeSoftKeyboard());
        onView(withId(R.id.edt_lastname))
                .perform(typeText(""), closeSoftKeyboard());
        onView(withId(R.id.edt_sex))
                .perform(typeText("Femenino"), closeSoftKeyboard());
        onView(withId(R.id.edt_date))
                .perform(typeText("12/12/1996"), closeSoftKeyboard());
        onView(withId(R.id.edt_description))
                .perform(typeText("De pocas palabras."), closeSoftKeyboard());

        onView(withId(R.id.btn_upload)).perform(click());
    }

    @Test
    public void datosPrescindiblesFaltantes(){

        onView(withId(R.id.edt_name))
                .perform(typeText("Edwarto"), closeSoftKeyboard());
        onView(withId(R.id.edt_lastname))
                .perform(typeText("Coahila"), closeSoftKeyboard());
        onView(withId(R.id.edt_sex))
                .perform(typeText("Masculino"), closeSoftKeyboard());
        onView(withId(R.id.edt_date))
                .perform(typeText("12/12/1996"), closeSoftKeyboard());
        onView(withId(R.id.edt_description))
                .perform(typeText(""), closeSoftKeyboard());

        onView(withId(R.id.btn_upload)).perform(click());
    }
}
