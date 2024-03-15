package edu.ucsd.cse110.successorator;


import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.longClick;
import static androidx.test.espresso.action.ViewActions.pressImeActionButton;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.assertion.ViewAssertions.doesNotExist;
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
import org.hamcrest.core.IsInstanceOf;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.time.LocalDate;

import edu.ucsd.cse110.successorator.util.DateManager;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class ScenarioBasedSystemTest1 {

    @Rule
    public ActivityScenarioRule<MainActivity> mActivityScenarioRule =
            new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void scenarioBasedSystemTest1() {

        LocalDate date = DateManager.getGlobalDate().getDate();
        String todayTitle = "Today, " + DateManager.getFormattedDate();

        ViewInteraction textView = onView(
                allOf(withText(todayTitle),
                        withParent(allOf(withId(com.google.android.material.R.id.action_bar),
                                withParent(withId(com.google.android.material.R.id.action_bar_container)))),
                        isDisplayed()));
        textView.check(matches(withText(todayTitle)));

        ViewInteraction actionMenuItemView = onView(
                allOf(withId(R.id.header_bar_add_task), withContentDescription("Add Task"),
                        childAtPosition(
                                childAtPosition(
                                        withId(com.google.android.material.R.id.action_bar),
                                        1),
                                1),
                        isDisplayed()));
        actionMenuItemView.perform(click());

        ViewInteraction radioButton = onView(
                allOf(withId(R.id.singleTime), withText("One Time"),
                        withParent(allOf(withId(R.id.pending_options),
                                withParent(IsInstanceOf.<View>instanceOf(android.view.ViewGroup.class)))),
                        isDisplayed()));
        radioButton.check(matches(isDisplayed()));

        ViewInteraction radioButton2 = onView(
                allOf(withId(R.id.daily), withText("Daily"),
                        withParent(allOf(withId(R.id.pending_options),
                                withParent(IsInstanceOf.<View>instanceOf(android.view.ViewGroup.class)))),
                        isDisplayed()));
        radioButton2.check(matches(isDisplayed()));

        String dayOfTheWeek = "Weekly on " + DateManager.getDayOfWeek(date);

        ViewInteraction radioButton3 = onView(
                allOf(withId(R.id.weekly), withText(dayOfTheWeek),
                        withParent(allOf(withId(R.id.pending_options),
                                withParent(IsInstanceOf.<View>instanceOf(android.view.ViewGroup.class)))),
                        isDisplayed()));
        radioButton3.check(matches(isDisplayed()));

        String dayOfTheMonth = "Monthly on " + DateManager.getDayOfMonth(date);

        ViewInteraction radioButton4 = onView(
                allOf(withId(R.id.monthly), withText(dayOfTheMonth),
                        withParent(allOf(withId(R.id.pending_options),
                                withParent(IsInstanceOf.<View>instanceOf(android.view.ViewGroup.class)))),
                        isDisplayed()));
        radioButton4.check(matches(isDisplayed()));

        String dayOfTheYear = "Yearly on " + DateManager.getDateNoYear(date);

        ViewInteraction radioButton5 = onView(
                allOf(withId(R.id.yearly), withText(dayOfTheYear),
                        withParent(allOf(withId(R.id.pending_options),
                                withParent(IsInstanceOf.<View>instanceOf(android.view.ViewGroup.class)))),
                        isDisplayed()));
        radioButton5.check(matches(isDisplayed()));

        ViewInteraction appCompatEditText = onView(
                allOf(withId(R.id.edit_text_task),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.custom),
                                        0),
                                0),
                        isDisplayed()));
        appCompatEditText.perform(replaceText("Make Breakfast"), closeSoftKeyboard());

        ViewInteraction appCompatEditText2 = onView(
                allOf(withId(R.id.edit_text_task), withText("Make Breakfast"),
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

        ViewInteraction textView2 = onView(
                allOf(withId(R.id.task_text), withText("Make Breakfast"),
                        withParent(allOf(withId(R.id.task_layout),
                                withParent(withId(R.id.card_list)))),
                        isDisplayed()));
        textView2.check(matches(withText("Make Breakfast")));

        ViewInteraction actionMenuItemView2 = onView(
                allOf(withId(R.id.header_bar_dropdown), withContentDescription("Dropdown"),
                        childAtPosition(
                                childAtPosition(
                                        withId(com.google.android.material.R.id.action_bar),
                                        1),
                                0),
                        isDisplayed()));
        actionMenuItemView2.perform(click());

        ViewInteraction button = onView(
                allOf(withId(R.id.today_button), withText("TODAY LIST"),
                        withParent(withParent(withId(android.R.id.custom))),
                        isDisplayed()));
        button.check(matches(isDisplayed()));

        ViewInteraction button2 = onView(
                allOf(withId(R.id.tomorrow_button), withText("TOMORROW LIST"),
                        withParent(withParent(withId(android.R.id.custom))),
                        isDisplayed()));
        button2.check(matches(isDisplayed()));

        ViewInteraction button3 = onView(
                allOf(withId(R.id.pending_button), withText("PENDING LIST"),
                        withParent(withParent(withId(android.R.id.custom))),
                        isDisplayed()));
        button3.check(matches(isDisplayed()));

        ViewInteraction button4 = onView(
                allOf(withId(R.id.recurring_button), withText("RECURRING LIST"),
                        withParent(withParent(withId(android.R.id.custom))),
                        isDisplayed()));
        button4.check(matches(isDisplayed()));

        ViewInteraction materialButton2 = onView(
                allOf(withId(R.id.tomorrow_button), withText("Tomorrow List"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.custom),
                                        0),
                                1),
                        isDisplayed()));
        materialButton2.perform(click());

        String tomorrowTitle = "Tomorrow, " + DateManager.getTomorrowFormattedDate();

        ViewInteraction textView3 = onView(
                allOf(withText(tomorrowTitle),
                        withParent(allOf(withId(com.google.android.material.R.id.action_bar),
                                withParent(withId(com.google.android.material.R.id.action_bar_container)))),
                        isDisplayed()));
        textView3.check(matches(withText(tomorrowTitle)));

        ViewInteraction actionMenuItemView3 = onView(
                allOf(withId(R.id.header_bar_add_task), withContentDescription("Add Task"),
                        childAtPosition(
                                childAtPosition(
                                        withId(com.google.android.material.R.id.action_bar),
                                        1),
                                1),
                        isDisplayed()));
        actionMenuItemView3.perform(click());

        ViewInteraction radioButton6 = onView(
                allOf(withId(R.id.singleTime), withText("One Time"),
                        withParent(allOf(withId(R.id.pending_options),
                                withParent(IsInstanceOf.<View>instanceOf(android.view.ViewGroup.class)))),
                        isDisplayed()));
        radioButton6.check(matches(isDisplayed()));

        ViewInteraction radioButton7 = onView(
                allOf(withId(R.id.daily), withText("Daily"),
                        withParent(allOf(withId(R.id.pending_options),
                                withParent(IsInstanceOf.<View>instanceOf(android.view.ViewGroup.class)))),
                        isDisplayed()));
        radioButton7.check(matches(isDisplayed()));

        date = date.plusDays(1);

        dayOfTheWeek = "Weekly on " + DateManager.getDayOfWeek(date);

        ViewInteraction radioButton8 = onView(
                allOf(withId(R.id.weekly), withText(dayOfTheWeek),
                        withParent(allOf(withId(R.id.pending_options),
                                withParent(IsInstanceOf.<View>instanceOf(android.view.ViewGroup.class)))),
                        isDisplayed()));
        radioButton8.check(matches(isDisplayed()));

        dayOfTheMonth = "Monthly on " + DateManager.getDayOfMonth(date);

        ViewInteraction radioButton9 = onView(
                allOf(withId(R.id.monthly), withText(dayOfTheMonth),
                        withParent(allOf(withId(R.id.pending_options),
                                withParent(IsInstanceOf.<View>instanceOf(android.view.ViewGroup.class)))),
                        isDisplayed()));
        radioButton9.check(matches(isDisplayed()));

        dayOfTheYear = "Yearly on " + DateManager.getDateNoYear(date);

        ViewInteraction radioButton10 = onView(
                allOf(withId(R.id.yearly), withText(dayOfTheYear),
                        withParent(allOf(withId(R.id.pending_options),
                                withParent(IsInstanceOf.<View>instanceOf(android.view.ViewGroup.class)))),
                        isDisplayed()));
        radioButton10.check(matches(isDisplayed()));

        ViewInteraction appCompatEditText3 = onView(
                allOf(withId(R.id.edit_text_task),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.custom),
                                        0),
                                0),
                        isDisplayed()));
        appCompatEditText3.perform(replaceText("Make Lunch"), closeSoftKeyboard());

        ViewInteraction appCompatEditText4 = onView(
                allOf(withId(R.id.edit_text_task), withText("Make Lunch"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.custom),
                                        0),
                                0),
                        isDisplayed()));
        appCompatEditText4.perform(pressImeActionButton());

        ViewInteraction materialButton3 = onView(
                allOf(withId(android.R.id.button1), withText("Save"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                3)));
        materialButton3.perform(scrollTo(), click());

        ViewInteraction textView4 = onView(
                allOf(withId(R.id.task_text), withText("Make Lunch"),
                        withParent(allOf(withId(R.id.task_layout),
                                withParent(withId(R.id.card_list)))),
                        isDisplayed()));
        textView4.check(matches(withText("Make Lunch")));

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
                allOf(withId(R.id.pending_button), withText("Pending List"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.custom),
                                        0),
                                2),
                        isDisplayed()));
        materialButton4.perform(click());

        ViewInteraction actionMenuItemView5 = onView(
                allOf(withId(R.id.header_bar_add_task), withContentDescription("Add Task"),
                        childAtPosition(
                                childAtPosition(
                                        withId(com.google.android.material.R.id.action_bar),
                                        1),
                                1),
                        isDisplayed()));
        actionMenuItemView5.perform(click());

        ViewInteraction linearLayout = onView(
                allOf(withId(R.id.singleTime), withText("One Time"),
                        withParent(allOf(withId(R.id.pending_options),
                                withParent(IsInstanceOf.<View>instanceOf(android.view.ViewGroup.class)))),
                        isDisplayed()));
        linearLayout.check(doesNotExist());

        ViewInteraction appCompatEditText5 = onView(
                allOf(withId(R.id.edit_text_task),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.custom),
                                        0),
                                0),
                        isDisplayed()));
        appCompatEditText5.perform(replaceText("Make Dinner"), closeSoftKeyboard());

        ViewInteraction appCompatEditText6 = onView(
                allOf(withId(R.id.edit_text_task), withText("Make Dinner"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.custom),
                                        0),
                                0),
                        isDisplayed()));
        appCompatEditText6.perform(pressImeActionButton());

        ViewInteraction materialButton5 = onView(
                allOf(withId(android.R.id.button1), withText("Save"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                3)));
        materialButton5.perform(scrollTo(), click());

        ViewInteraction textView5 = onView(
                allOf(withId(R.id.task_text), withText("Make Dinner"),
                        withParent(allOf(withId(R.id.task_layout),
                                withParent(withId(R.id.card_list)))),
                        isDisplayed()));
        textView5.check(matches(withText("Make Dinner")));

        DataInteraction constraintLayout = onData(anything())
                .inAdapterView(allOf(withId(R.id.card_list),
                        childAtPosition(
                                withClassName(is("androidx.constraintlayout.widget.ConstraintLayout")),
                                0)))
                .atPosition(0);
        constraintLayout.perform(longClick());

        ViewInteraction button5 = onView(
                allOf(withId(R.id.move_to_today), withText("MOVE TO TODAY"),
                        withParent(withParent(withId(android.R.id.custom))),
                        isDisplayed()));
        button5.check(matches(isDisplayed()));

        ViewInteraction button6 = onView(
                allOf(withId(R.id.move_to_tomorrow), withText("MOVE TO TOMORROW"),
                        withParent(withParent(withId(android.R.id.custom))),
                        isDisplayed()));
        button6.check(matches(isDisplayed()));

        ViewInteraction button7 = onView(
                allOf(withId(R.id.move_to_finish), withText("FINISH"),
                        withParent(withParent(withId(android.R.id.custom))),
                        isDisplayed()));
        button7.check(matches(isDisplayed()));

        ViewInteraction button8 = onView(
                allOf(withId(R.id.move_to_delete), withText("DELETE"),
                        withParent(withParent(withId(android.R.id.custom))),
                        isDisplayed()));
        button8.check(matches(isDisplayed()));

        ViewInteraction materialButton6 = onView(
                allOf(withId(R.id.move_to_today), withText("Move to Today"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.custom),
                                        0),
                                0),
                        isDisplayed()));
        materialButton6.perform(click());

        ViewInteraction listView2 = onView(
                allOf(withId(R.id.task_text), withText("Make Dinner"),
                        withParent(allOf(withId(R.id.task_layout),
                                withParent(withId(R.id.card_list)))),
                        isDisplayed()));
        listView2.check(doesNotExist());

        ViewInteraction actionMenuItemView6 = onView(
                allOf(withId(R.id.header_bar_dropdown), withContentDescription("Dropdown"),
                        childAtPosition(
                                childAtPosition(
                                        withId(com.google.android.material.R.id.action_bar),
                                        1),
                                0),
                        isDisplayed()));
        actionMenuItemView6.perform(click());

        ViewInteraction materialButton7 = onView(
                allOf(withId(R.id.today_button), withText("Today List"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.custom),
                                        0),
                                0),
                        isDisplayed()));
        materialButton7.perform(click());

        ViewInteraction textView6 = onView(
                allOf(withId(R.id.task_text), withText("Make Breakfast"),
                        withParent(allOf(withId(R.id.task_layout),
                                withParent(withId(R.id.card_list)))),
                        isDisplayed()));
        textView6.check(matches(withText("Make Breakfast")));

        ViewInteraction textView7 = onView(
                allOf(withId(R.id.task_text), withText("Make Dinner"),
                        withParent(allOf(withId(R.id.task_layout),
                                withParent(withId(R.id.card_list)))),
                        isDisplayed()));
        textView7.check(matches(withText("Make Dinner")));

        ViewInteraction actionMenuItemView7 = onView(
                allOf(withId(R.id.header_bar_dropdown), withContentDescription("Dropdown"),
                        childAtPosition(
                                childAtPosition(
                                        withId(com.google.android.material.R.id.action_bar),
                                        1),
                                0),
                        isDisplayed()));
        actionMenuItemView7.perform(click());

        ViewInteraction materialButton8 = onView(
                allOf(withId(R.id.recurring_button), withText("Recurring List"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.custom),
                                        0),
                                3),
                        isDisplayed()));
        materialButton8.perform(click());

        ViewInteraction actionMenuItemView8 = onView(
                allOf(withId(R.id.header_bar_add_task), withContentDescription("Add Task"),
                        childAtPosition(
                                childAtPosition(
                                        withId(com.google.android.material.R.id.action_bar),
                                        1),
                                1),
                        isDisplayed()));
        actionMenuItemView8.perform(click());

        ViewInteraction radioButton11 = onView(
                allOf(withId(R.id.daily), withText("Daily"),
                        withParent(allOf(withId(R.id.pending_options),
                                withParent(IsInstanceOf.<View>instanceOf(android.view.ViewGroup.class)))),
                        isDisplayed()));
        radioButton11.check(matches(isDisplayed()));

        ViewInteraction radioButton12 = onView(
                allOf(withId(R.id.weekly), withText("Weekly"),
                        withParent(allOf(withId(R.id.pending_options),
                                withParent(IsInstanceOf.<View>instanceOf(android.view.ViewGroup.class)))),
                        isDisplayed()));
        radioButton12.check(matches(isDisplayed()));

        ViewInteraction radioButton13 = onView(
                allOf(withId(R.id.monthly), withText("Monthly"),
                        withParent(allOf(withId(R.id.pending_options),
                                withParent(IsInstanceOf.<View>instanceOf(android.view.ViewGroup.class)))),
                        isDisplayed()));
        radioButton13.check(matches(isDisplayed()));

        ViewInteraction radioButton14 = onView(
                allOf(withId(R.id.yearly), withText("Yearly"),
                        withParent(allOf(withId(R.id.pending_options),
                                withParent(IsInstanceOf.<View>instanceOf(android.view.ViewGroup.class)))),
                        isDisplayed()));
        radioButton14.check(matches(isDisplayed()));


        date = DateManager.getGlobalDate().getDate();
        String datePickerText = date.getMonthValue() + "/" + date.getDayOfMonth() + "/" + date.getYear();

        ViewInteraction button9 = onView(
                allOf(withId(R.id.start_date_button), withText(datePickerText),
                        withParent(withParent(withId(android.R.id.custom))),
                        isDisplayed()));
        button9.check(matches(isDisplayed()));

        ViewInteraction appCompatEditText7 = onView(
                allOf(withId(R.id.edit_text_task),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.custom),
                                        0),
                                0),
                        isDisplayed()));
        appCompatEditText7.perform(replaceText("Eat Lunch"), closeSoftKeyboard());

        ViewInteraction appCompatEditText8 = onView(
                allOf(withId(R.id.edit_text_task), withText("Eat Lunch"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.custom),
                                        0),
                                0),
                        isDisplayed()));
        appCompatEditText8.perform(pressImeActionButton());

        ViewInteraction materialButton9 = onView(
                allOf(withId(android.R.id.button1), withText("Save"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                3)));
        materialButton9.perform(scrollTo(), click());

        String weeklyText = "Eat Lunch, weekly on " + DateManager.getDayOfWeek(date);

        ViewInteraction textView8 = onView(
                allOf(withId(R.id.task_text), withText(weeklyText),
                        withParent(allOf(withId(R.id.task_layout),
                                withParent(withId(R.id.card_list)))),
                        isDisplayed()));
        textView8.check(matches(withText(weeklyText)));

        ViewInteraction actionMenuItemView9 = onView(
                allOf(withId(R.id.header_bar_dropdown), withContentDescription("Dropdown"),
                        childAtPosition(
                                childAtPosition(
                                        withId(com.google.android.material.R.id.action_bar),
                                        1),
                                0),
                        isDisplayed()));
        actionMenuItemView9.perform(click());

        ViewInteraction materialButton10 = onView(
                allOf(withId(R.id.today_button), withText("Today List"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.custom),
                                        0),
                                0),
                        isDisplayed()));
        materialButton10.perform(click());

        ViewInteraction textView9 = onView(
                allOf(withId(R.id.task_text), withText("Eat Lunch"),
                        withParent(allOf(withId(R.id.task_layout),
                                withParent(withId(R.id.card_list)))),
                        isDisplayed()));
        textView9.check(matches(withText("Eat Lunch")));
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
