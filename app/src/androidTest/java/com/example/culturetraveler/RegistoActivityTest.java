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
import com.example.culturetraveler.RegistoActivity;
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

public class RegistoActivityTest {
    private String mStringToBetyped;

    @Rule
    public ActivityTestRule<RegistoActivity> activityRule =
            new ActivityTestRule<>(RegistoActivity.class);

    @Test
    public void showErrorWhenEmailInvalid() {
        Espresso.onView(ViewMatchers.withId(R.id.email_ETxt)).perform(click());
        onView(ViewMatchers.withId(R.id.registo_R_Button)).check(matches(isDisplayed()));

    }

    @Test
    public void showErrorWhenPasswordInvalid() {
        Espresso.onView(ViewMatchers.withId(R.id.password_R_ETxt)).perform(click());
        onView(ViewMatchers.withId(R.id.registo_R_Button)).check(matches(isDisplayed()));

    }

    @Test
    public void showErrorWhenNomeInvalid() {
        Espresso.onView(ViewMatchers.withId(R.id.nome_ETxt)).perform(click());
        onView(ViewMatchers.withId(R.id.registo_R_Button)).check(matches(isDisplayed()));

    }



}
