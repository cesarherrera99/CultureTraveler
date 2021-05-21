package com.example.culturetraveler;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.widget.Button;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;

import static androidx.core.content.ContextCompat.startActivity;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;


import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
@LargeTest

public class LoginActivityTest {
    private String mStringToBetyped;

    @Rule
    public ActivityTestRule<LoginActivity> activityRule =
            new ActivityTestRule<>(LoginActivity.class);


    @Test
    public void showErrorEmailInvalid() {
        Espresso.onView(ViewMatchers.withId(R.id.email_L_ETxt)).perform(click());
        onView(ViewMatchers.withId(R.id.login_L_Button)).check(matches(isDisplayed()));

    }

    @Test
    public void showErrorPasswordInvalid() {
        Espresso.onView(ViewMatchers.withId(R.id.password_L_ETxt)).perform(click());
        onView(ViewMatchers.withId(R.id.login_L_Button)).check(matches(isDisplayed()));

    }



}

