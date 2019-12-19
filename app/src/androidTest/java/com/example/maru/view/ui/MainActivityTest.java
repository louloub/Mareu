package com.example.maru.view.ui;


import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.TextView;

import androidx.test.espresso.DataInteraction;
import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.ViewInteraction;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import com.example.maru.AndroidTestUtil;
import com.example.maru.R;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.hamcrest.core.AllOf;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.is;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);
    String participant1 = "participant1@google.fr";
    String participant2 = "participant2@google.fr";
    String participant3 = "participant3@google.fr";

    @Test
    public void addOneMeeting() {
        // Click on FAB button for create new Meeting
        ViewInteraction floatingActionButton = onView(
                allOf(withId(R.id.fab),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                2),
                        isDisplayed()));
        floatingActionButton.perform(click());

        // Set Subject of Meeting
        ViewInteraction textInputEditText = onView(
                allOf(withId(R.id.create_meeting_tiet_subject),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.create_meeting_til_subject),
                                        0),
                                0),
                        isDisplayed()));
        textInputEditText.perform(replaceText("sujet 1"), closeSoftKeyboard());

        // Set Participant of Meeting
        ViewInteraction textInputEditText2 = onView(
                allOf(withId(R.id.create_meeting_teit_listOfParticipant),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.horizontal_scroll_view),
                                        0),
                                1)));
        textInputEditText2.perform(scrollTo(), replaceText(participant1 + "," ), closeSoftKeyboard());

        // Set Room of Meeting
        ViewInteraction appCompatSpinner = onView(
                allOf(withId(R.id.create_meeting_spi_room),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                4),
                        isDisplayed()));
        appCompatSpinner.perform(click());

        DataInteraction appCompatCheckedTextView = onData(anything())
                .inAdapterView(childAtPosition(
                        withClassName(is("android.widget.PopupWindow$PopupBackgroundView")),
                        0))
                .atPosition(1);
        appCompatCheckedTextView.perform(click());

        // Set Date of Meeting
        ViewInteraction materialButton = onView(
                allOf(withId(R.id.create_meeting_bt_date), withText("Choisir la date"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                7),
                        isDisplayed()));
        materialButton.perform(click());

        ViewInteraction materialButton2 = onView(
                allOf(withId(android.R.id.button1), withText("OK"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                3)));
        materialButton2.perform(scrollTo(), click());

        // Set Hour of Meeting
        ViewInteraction materialButton3 = onView(
                allOf(withId(R.id.create_meeting_bt_hour), withText("Choisir lheure"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                5),
                        isDisplayed()));
        materialButton3.perform(click());

        ViewInteraction materialButton4 = onView(
                allOf(withId(android.R.id.button1), withText("OK"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                3)));
        materialButton4.perform(scrollTo(), click());

        // Valid meeting
        ViewInteraction materialButton5 = onView(
                allOf(withId(R.id.create_meeting_bt_valid_meeting), withText("Valider ma réunion"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                9),
                        isDisplayed()));
        materialButton5.perform(click());

        /*ViewInteraction recyclerView = onView(
                allOf(withId(R.id.main_rv),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                1),
                        isDisplayed()));
        recyclerView.check(matches(isDisplayed()));*/

        onView(withId(R.id.main_rv)).check(new AndroidTestUtil.RecyclerViewItemCountAssertion(1));
    }

    @Test
    public void addTwoMeeting() {
        // Click on FAB button for create new Meeting 1
        ViewInteraction floatingActionButton = onView(
                allOf(withId(R.id.fab),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                2),
                        isDisplayed()));
        floatingActionButton.perform(click());

        // Set Subject of Meeting 1
        ViewInteraction textInputEditText = onView(
                allOf(withId(R.id.create_meeting_tiet_subject),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.create_meeting_til_subject),
                                        0),
                                0),
                        isDisplayed()));
        textInputEditText.perform(replaceText("sujet 1"), closeSoftKeyboard());

        // Set Participant of Meeting 1
        ViewInteraction textInputEditText2 = onView(
                allOf(withId(R.id.create_meeting_teit_listOfParticipant),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.horizontal_scroll_view),
                                        0),
                                1)));
        textInputEditText2.perform(scrollTo(), replaceText(participant1 + "," ), closeSoftKeyboard());

        // Set Room of Meeting 1
        ViewInteraction appCompatSpinner = onView(
                allOf(withId(R.id.create_meeting_spi_room),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                4),
                        isDisplayed()));
        appCompatSpinner.perform(click());

        DataInteraction appCompatCheckedTextView = onData(anything())
                .inAdapterView(childAtPosition(
                        withClassName(is("android.widget.PopupWindow$PopupBackgroundView")),
                        0))
                .atPosition(1);
        appCompatCheckedTextView.perform(click());

        // Set Date of Meeting 1
        ViewInteraction materialButton = onView(
                allOf(withId(R.id.create_meeting_bt_date), withText("Choisir la date"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                7),
                        isDisplayed()));
        materialButton.perform(click());

        ViewInteraction materialButton2 = onView(
                allOf(withId(android.R.id.button1), withText("OK"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                3)));
        materialButton2.perform(scrollTo(), click());

        // Set Hour of Meeting 1
        ViewInteraction materialButton3 = onView(
                allOf(withId(R.id.create_meeting_bt_hour), withText("Choisir lheure"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                5),
                        isDisplayed()));
        materialButton3.perform(click());

        ViewInteraction materialButton4 = onView(
                allOf(withId(android.R.id.button1), withText("OK"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                3)));
        materialButton4.perform(scrollTo(), click());

        // Valid meeting 1
        ViewInteraction materialButton5 = onView(
                allOf(withId(R.id.create_meeting_bt_valid_meeting), withText("Valider ma réunion"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                9),
                        isDisplayed()));
        materialButton5.perform(click());

        // Click on FAB button for create new Meeting 2
        ViewInteraction floatingActionButton2 = onView(
                allOf(withId(R.id.fab),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                2),
                        isDisplayed()));
        floatingActionButton.perform(click());

        // Set Subject of Meeting 2
        ViewInteraction textInputEditText3 = onView(
                allOf(withId(R.id.create_meeting_tiet_subject),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.create_meeting_til_subject),
                                        0),
                                0),
                        isDisplayed()));
        textInputEditText.perform(replaceText("sujet 1"), closeSoftKeyboard());

        // Set Participant of Meeting 2
        ViewInteraction textInputEditText4 = onView(
                allOf(withId(R.id.create_meeting_teit_listOfParticipant),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.horizontal_scroll_view),
                                        0),
                                1)));
        textInputEditText2.perform(scrollTo(), replaceText(participant1 + "," ), closeSoftKeyboard());

        // Set Room of Meeting 2
        ViewInteraction appCompatSpinner2 = onView(
                allOf(withId(R.id.create_meeting_spi_room),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                4),
                        isDisplayed()));
        appCompatSpinner.perform(click());

        DataInteraction appCompatCheckedTextView2 = onData(anything())
                .inAdapterView(childAtPosition(
                        withClassName(is("android.widget.PopupWindow$PopupBackgroundView")),
                        0))
                .atPosition(1);
        appCompatCheckedTextView.perform(click());

        // Set Date of Meeting 2
        ViewInteraction materialButton6 = onView(
                allOf(withId(R.id.create_meeting_bt_date), withText("Choisir la date"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                7),
                        isDisplayed()));
        materialButton.perform(click());

        ViewInteraction materialButton7 = onView(
                allOf(withId(android.R.id.button1), withText("OK"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                3)));
        materialButton2.perform(scrollTo(), click());

        // Set Hour of Meeting 2
        ViewInteraction materialButton8 = onView(
                allOf(withId(R.id.create_meeting_bt_hour), withText("Choisir lheure"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                5),
                        isDisplayed()));
        materialButton3.perform(click());

        ViewInteraction materialButton9 = onView(
                allOf(withId(android.R.id.button1), withText("OK"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                3)));
        materialButton4.perform(scrollTo(), click());

        // Valid meeting 2
        ViewInteraction materialButton10 = onView(
                allOf(withId(R.id.create_meeting_bt_valid_meeting), withText("Valider ma réunion"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                9),
                        isDisplayed()));
        materialButton5.perform(click());

        onView(withId(R.id.main_rv)).check(new AndroidTestUtil.RecyclerViewItemCountAssertion(2));
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

    public static ViewAction setTextInTextView(final String value){
        return new ViewAction() {
            @SuppressWarnings("unchecked")
            @Override
            public Matcher<View> getConstraints() {
                return AllOf.allOf(isDisplayed(), isAssignableFrom(TextView.class));
            }

            @Override
            public void perform(UiController uiController, View view) {
                ((TextView) view).setText(value);
            }

            @Override
            public String getDescription() {
                return "replace text";
            }
        };
    }
}
