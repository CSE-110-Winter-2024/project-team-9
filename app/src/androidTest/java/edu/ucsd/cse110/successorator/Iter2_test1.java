package edu.ucsd.cse110.successorator;


import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.pressImeActionButton;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.is;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.espresso.DataInteraction;
import androidx.test.espresso.ViewInteraction;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class Iter2_test1 {

    @Rule
    public ActivityScenarioRule<MainActivity> mActivityScenarioRule =
            new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void iter2_test1() {
        ViewInteraction actionMenuItemView = onView(
                allOf(withId(R.id.header_bar_add_task), withContentDescription("Add Task"),
                        childAtPosition(
                                childAtPosition(
                                        withId(com.google.android.material.R.id.action_bar),
                                        1),
                                1),
                        isDisplayed()));
        actionMenuItemView.perform(click());

        ViewInteraction appCompatEditText = onView(
                allOf(withId(R.id.edit_text_task),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.custom),
                                        0),
                                0),
                        isDisplayed()));
        appCompatEditText.perform(replaceText("h 1"), closeSoftKeyboard());

        ViewInteraction appCompatEditText2 = onView(
                allOf(withId(R.id.edit_text_task), withText("h 1"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.custom),
                                        0),
                                0),
                        isDisplayed()));
        appCompatEditText2.perform(pressImeActionButton());

        ViewInteraction materialButton = onView(
                allOf(withId(android.R.id.button1), withText("Save"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                3)));
        materialButton.perform(scrollTo(), click());

        ViewInteraction actionMenuItemView2 = onView(
                allOf(withId(R.id.header_bar_add_task), withContentDescription("Add Task"),
                        childAtPosition(
                                childAtPosition(
                                        withId(com.google.android.material.R.id.action_bar),
                                        1),
                                1),
                        isDisplayed()));
        actionMenuItemView2.perform(click());

        ViewInteraction appCompatEditText3 = onView(
                allOf(withId(R.id.edit_text_task),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.custom),
                                        0),
                                0),
                        isDisplayed()));
        appCompatEditText3.perform(replaceText("e 2"), closeSoftKeyboard());

        ViewInteraction materialRadioButton = onView(
                allOf(withId(R.id.contextErrand), withText("E"),
                        childAtPosition(
                                allOf(withId(R.id.context_options),
                                        childAtPosition(
                                                withClassName(is("androidx.constraintlayout.widget.ConstraintLayout")),
                                                1)),
                                3),
                        isDisplayed()));
        materialRadioButton.perform(click());

        ViewInteraction appCompatEditText4 = onView(
                allOf(withId(R.id.edit_text_task), withText("e 2"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.custom),
                                        0),
                                0),
                        isDisplayed()));
        appCompatEditText4.perform(pressImeActionButton());

        ViewInteraction materialRadioButton2 = onView(
                allOf(withId(R.id.weekly), withText("Weekly on Thursday"),
                        childAtPosition(
                                allOf(withId(R.id.pending_options),
                                        childAtPosition(
                                                withClassName(is("androidx.constraintlayout.widget.ConstraintLayout")),
                                                2)),
                                2),
                        isDisplayed()));
        materialRadioButton2.perform(click());

        ViewInteraction materialButton2 = onView(
                allOf(withId(android.R.id.button1), withText("Save"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                3)));
        materialButton2.perform(scrollTo(), click());

        ViewInteraction actionMenuItemView3 = onView(
                allOf(withId(R.id.header_bar_add_task), withContentDescription("Add Task"),
                        childAtPosition(
                                childAtPosition(
                                        withId(com.google.android.material.R.id.action_bar),
                                        1),
                                1),
                        isDisplayed()));
        actionMenuItemView3.perform(click());

        ViewInteraction materialRadioButton3 = onView(
                allOf(withId(R.id.contextWork), withText("W"),
                        childAtPosition(
                                allOf(withId(R.id.context_options),
                                        childAtPosition(
                                                withClassName(is("androidx.constraintlayout.widget.ConstraintLayout")),
                                                1)),
                                1),
                        isDisplayed()));
        materialRadioButton3.perform(click());

        ViewInteraction appCompatEditText5 = onView(
                allOf(withId(R.id.edit_text_task),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.custom),
                                        0),
                                0),
                        isDisplayed()));
        appCompatEditText5.perform(replaceText("work"), closeSoftKeyboard());

        ViewInteraction appCompatEditText6 = onView(
                allOf(withId(R.id.edit_text_task), withText("work"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.custom),
                                        0),
                                0),
                        isDisplayed()));
        appCompatEditText6.perform(pressImeActionButton());

        ViewInteraction materialRadioButton4 = onView(
                allOf(withId(R.id.daily), withText("Daily"),
                        childAtPosition(
                                allOf(withId(R.id.pending_options),
                                        childAtPosition(
                                                withClassName(is("androidx.constraintlayout.widget.ConstraintLayout")),
                                                2)),
                                1),
                        isDisplayed()));
        materialRadioButton4.perform(click());

        ViewInteraction materialButton3 = onView(
                allOf(withId(android.R.id.button1), withText("Save"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                3)));
        materialButton3.perform(scrollTo(), click());

        ViewInteraction actionMenuItemView4 = onView(
                allOf(withId(R.id.header_bar_dropdown), withContentDescription("Dropdown"),
                        childAtPosition(
                                childAtPosition(
                                        withId(com.google.android.material.R.id.action_bar),
                                        1),
                                0),
                        isDisplayed()));
        actionMenuItemView4.perform(click());

        ViewInteraction materialButton4 = onView(
                allOf(withId(R.id.tomorrow_button), withText("Tomorrow List"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.custom),
                                        0),
                                1),
                        isDisplayed()));
        materialButton4.perform(click());

        ViewInteraction textView = onView(
                allOf(withId(R.id.task_text), withText("work"),
                        withParent(allOf(withId(R.id.task_layout),
                                withParent(withId(R.id.card_list)))),
                        isDisplayed()));
        textView.check(matches(withText("work")));

        ViewInteraction materialButton5 = onView(
                allOf(withId(R.id.move_date_button), withText("Advance One Day"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.fragment_container),
                                        0),
                                2),
                        isDisplayed()));
        materialButton5.perform(click());

        ViewInteraction materialButton6 = onView(
                allOf(withId(R.id.move_date_button), withText("Advance One Day"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.fragment_container),
                                        0),
                                2),
                        isDisplayed()));
        materialButton6.perform(click());

        DataInteraction constraintLayout = onData(anything())
                .inAdapterView(allOf(withId(R.id.card_list),
                        childAtPosition(
                                withClassName(is("androidx.constraintlayout.widget.ConstraintLayout")),
                                0)))
                .atPosition(0);
        constraintLayout.perform(click());

        ViewInteraction materialButton7 = onView(
                allOf(withId(R.id.move_date_button), withText("Advance One Day"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.fragment_container),
                                        0),
                                2),
                        isDisplayed()));
        materialButton7.perform(click());

        ViewInteraction materialButton8 = onView(
                allOf(withId(R.id.move_date_button), withText("Advance One Day"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.fragment_container),
                                        0),
                                2),
                        isDisplayed()));
        materialButton8.perform(click());

        DataInteraction constraintLayout2 = onData(anything())
                .inAdapterView(allOf(withId(R.id.card_list),
                        childAtPosition(
                                withClassName(is("androidx.constraintlayout.widget.ConstraintLayout")),
                                0)))
                .atPosition(0);
        constraintLayout2.perform(click());

        ViewInteraction materialButton9 = onView(
                allOf(withId(R.id.move_date_button), withText("Advance One Day"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.fragment_container),
                                        0),
                                2),
                        isDisplayed()));
        materialButton9.perform(click());

        ViewInteraction materialButton10 = onView(
                allOf(withId(R.id.move_date_button), withText("Advance One Day"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.fragment_container),
                                        0),
                                2),
                        isDisplayed()));
        materialButton10.perform(click());

        ViewInteraction textView2 = onView(
                allOf(withId(R.id.task_text), withText("e 2"),
                        withParent(allOf(withId(R.id.task_layout),
                                withParent(withId(R.id.card_list)))),
                        isDisplayed()));
        textView2.check(matches(withText("e 2")));
    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}
