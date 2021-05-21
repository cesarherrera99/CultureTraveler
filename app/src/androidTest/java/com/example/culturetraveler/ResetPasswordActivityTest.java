package com.example.culturetraveler;

import androidx.annotation.ContentView;
import androidx.test.espresso.DataInteraction;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.espresso.matcher.ViewMatchers.Visibility;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import com.example.culturetraveler.ResetPasswordActivity;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;


import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
@LargeTest

public class ResetPasswordActivityTest {
    private String mStringToBetyped;

    @Rule
    public ActivityTestRule<ResetPasswordActivity> activityRule =
            new ActivityTestRule<>(ResetPasswordActivity.class);

    @Test
    public void showErrorWhenEmailisInvalid() {
        Espresso.onView(ViewMatchers.withId(R.id.emailresetpassword)).perform(click());
        onView(ViewMatchers.withId(R.id.resetpassword)).check(matches(isDisplayed()));
    }




}
