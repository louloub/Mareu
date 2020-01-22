package com.example.mareu.view.ui;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.DatePicker;
import android.widget.TimePicker;

import androidx.test.espresso.ViewInteraction;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;

import com.example.mareu.AndroidTestUtil;
import com.example.mareu.R;
import com.example.mareu.view.MainActivity;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.contrib.PickerActions.setDate;
import static androidx.test.espresso.contrib.PickerActions.setTime;
import static androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;

/**
 * Instrumental test
 */

@LargeTest
@RunWith(AndroidJUnit4.class)
public class MainActivityTestDeleteMeeting {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);
    private static final String PARTICIPANT1 = "participant1@google.fr";
    private static final String PARTICIPANT2 = "participant2@google.fr";
    private static final String PARTICIPANT3 = "participant3@google.fr";

    /**
     * Delete One Meeting
     */
    @Test
    public void deleteOneMeetingInListOfOneMeeting() throws InterruptedException {
        // Add one meeting
        createMeeting("Sujet 1",1,2020,2,30,10,10);

        // Delete meeting
        onView(withId(R.id.main_rv)).perform(
                RecyclerViewActions.actionOnItemAtPosition(0,
                        AndroidTestUtil.MyRecyclerViewFinder.clickChildViewWithId(R.id.meeting_iv_delete_meeting)
                )
        );

        // Check if meeting list is empty
        onView(withId(R.id.main_rv)).check(new AndroidTestUtil.RecyclerViewItemCountAssertion(0));
    }

    /**
     * Delete One Meeting in list
     */
    @Test
    public void deleteOneMeetingInListOfThreeMeeting() throws InterruptedException {
        // Add Meeting 1
        createMeeting("Sujet 1",1,2020,2,30,10,10);

        // Add Meeting 2
        createMeeting("Sujet 2",2,2020,5,11,12,20);

        // Add Meeting 3
        createMeeting("Sujet 3",3,2020,3,12,15,30);

        // Delete meeting
        onView(withId(R.id.main_rv)).perform(
            RecyclerViewActions.actionOnItemAtPosition(0,
                        AndroidTestUtil.MyRecyclerViewFinder.clickChildViewWithId(R.id.meeting_iv_delete_meeting)
                )
        );

        // Check if meeting list contain two meeting
        onView(withId(R.id.main_rv)).check(new AndroidTestUtil.RecyclerViewItemCountAssertion(2));
    }

    /**
     * Create Meeting with Settings
     */
    private void createMeeting(String subject, int room, int year, int month, int day, int hour, int minutes){
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
        onView(withId(R.id.create_meeting_til_subject)).perform(click());
        onView(withId(R.id.create_meeting_tiet_subject)).perform(typeText(subject), closeSoftKeyboard());

        // Set Participant 1 of Meeting
        ViewInteraction textInputEditText2 = onView(
                allOf(withId(R.id.create_meeting_teit_listOfParticipant),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.horizontal_scroll_view),
                                        0),
                                1)));
        textInputEditText2.perform(scrollTo(), replaceText(PARTICIPANT1 + "," ), closeSoftKeyboard());

        // Set Participant 2 of Meeting
        ViewInteraction textInputEditText5 = onView(
                allOf(withId(R.id.create_meeting_teit_listOfParticipant),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.horizontal_scroll_view),
                                        0),
                                1)));
        textInputEditText5.perform(scrollTo(), replaceText(PARTICIPANT2 + "," ), closeSoftKeyboard());

        // Set Participant 3 of Meeting
        ViewInteraction textInputEditText6 = onView(
                allOf(withId(R.id.create_meeting_teit_listOfParticipant),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.horizontal_scroll_view),
                                        0),
                                1)));
        textInputEditText6.perform(scrollTo(), replaceText(PARTICIPANT3 + "," ), closeSoftKeyboard());

        // Set Room of Meeting
        onView(withId(R.id.create_meeting_spi_room)).perform(click());
        onData(allOf(is(instanceOf(Integer.class)))).atPosition(room-1).perform(click());

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

        onView(isAssignableFrom(DatePicker.class)).perform(setDate(year,month,day));

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

        onView(isAssignableFrom(TimePicker.class)).perform(setTime(hour,minutes));

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
