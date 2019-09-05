package com.raesba.tfg_coordinacionservicios;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.ViewAssertion;
import android.support.test.espresso.intent.Intents;
import android.support.test.espresso.intent.matcher.IntentMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.raesba.tfg_coordinacionservicios.ui.login.LoginActivity;
import com.raesba.tfg_coordinacionservicios.ui.proveedorperfil.ProveedorPerfilActivity;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.action.ViewActions.clearText;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.doesNotExist;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertEquals;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class LoginInstrumentedTest {

    @Rule
    public ActivityTestRule<LoginActivity> activityTestRule =
            new ActivityTestRule<>(LoginActivity.class);

    @Test
    public void checkLoginCorrecto() {
        Espresso.onView(withId(R.id.email)).perform(clearText());
        Espresso.onView(withId(R.id.password)).perform(clearText());
        Espresso.onView(withId(R.id.email)).perform(typeText("raesba@gmail.com"));
        Espresso.onView(withId(R.id.email)).perform(closeSoftKeyboard());
        Espresso.onView(withId(R.id.password)).perform(typeText("12345678"));
        Espresso.onView(withId(R.id.email)).perform(closeSoftKeyboard());
        Espresso.onView(withId(R.id.entrar)).perform(click());

        Espresso.onView(withId(R.id.progress_bar)).check(matches(isDisplayed()));
        sleep(5000);
        Espresso.onView(withId(R.id.button_calendario)).check(matches(isDisplayed()));
    }

    @Test
    public void checkLoginIncorrecto() {
        Espresso.onView(withId(R.id.email)).perform(clearText());
        Espresso.onView(withId(R.id.password)).perform(clearText());
        Espresso.onView(withId(R.id.email)).perform(typeText("falso@gmail.com"));
        Espresso.onView(withId(R.id.email)).perform(closeSoftKeyboard());
        Espresso.onView(withId(R.id.password)).perform(typeText("12345678"));
        Espresso.onView(withId(R.id.email)).perform(closeSoftKeyboard());
        Espresso.onView(withId(R.id.entrar)).perform(click());

        sleep(5000);
        Espresso.onView(withId(R.id.button_calendario)).check(doesNotExist());
    }

    private void sleep(int milis){
        try {
            Thread.sleep(milis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
