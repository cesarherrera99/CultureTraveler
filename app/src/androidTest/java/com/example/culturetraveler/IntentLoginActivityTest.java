package com.example.culturetraveler;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Context;
import android.content.Intent;
import android.widget.Button;

import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentController;
import androidx.fragment.app.FragmentManager;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;

import com.example.culturetraveler.ui.perfil.PerfilFragment;

import static androidx.core.content.ContextCompat.startActivity;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static org.junit.Assert.assertNotNull;


import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
@LargeTest

public class IntentLoginActivityTest {
    private String mStringToBetyped;

    @Rule
    public ActivityTestRule<RegistoActivity> activityRule =
            new ActivityTestRule<>(RegistoActivity.class);

    Instrumentation.ActivityMonitor monitor = getInstrumentation().addMonitor(LoginActivity.class.getName(), null, false);


    @Test
    public void IntentLoginWhenClickinTenhoumaConta() {
        Espresso.onView(ViewMatchers.withId(R.id.login_R_Button)).perform(click());

        Activity LoginActivity = getInstrumentation().waitForMonitorWithTimeout(monitor, 5000);

        assertNotNull(LoginActivity);
        LoginActivity.finish();
    }

}

