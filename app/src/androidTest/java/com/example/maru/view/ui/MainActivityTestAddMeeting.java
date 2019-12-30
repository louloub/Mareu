package com.example.maru.view.ui;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

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
import static androidx.test.espresso.contrib.PickerActions.setDate;
import static androidx.test.espresso.contrib.PickerActions.setTime;
import static androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;

/**
 * Instrumental test
 */

@LargeTest
@RunWith(AndroidJUnit4.class)
public class MainActivityTestAddMeeting {

    private static final String PARTICIPANT1 = "participant1@google.fr";
    private static final String PARTICIPANT2 = "participant2@google.fr";
    private static final String PARTICIPANT3 = "participant3@google.fr";

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    /**
     * Add One Meeting test
     */
    // A VERIFIER par NINO
    @Test
    public void addOneMeeting() {
        // Add Meeting 1
        createMeetingNumberOne();

        // Check if meeting is on Recycler View
        onView(withId(R.id.main_rv)).check(new AndroidTestUtil.RecyclerViewItemCountAssertion(1));
    }

    /**
     * Add Three Different Meeting test
     */
    // A VERIFIER par NINO
    @Test
    public void addThreeDifferentMeeting() {
        // Add Meeting 1
        createMeetingNumberOne();

        // Add Meeting 2
        createMeetingNumberTwo();

        // Add Meeting 3
        createMeetingNumberThree();

        // Check if meeting is on Recycler View
        onView(withId(R.id.main_rv)).check(new AndroidTestUtil.RecyclerViewItemCountAssertion(3));
    }

    // TODO : 27/12 une suele méthode avec les paramétre modifiable (ID / ROOM .. )
    private void createMeetingNumberOne() {
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

        // Set Participant 1 of Meeting 1
        ViewInteraction textInputEditText2 = onView(
                allOf(withId(R.id.create_meeting_teit_listOfParticipant),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.horizontal_scroll_view),
                                        0),
                                1)));
        textInputEditText2.perform(scrollTo(), replaceText(PARTICIPANT1 + "," ), closeSoftKeyboard());

        // Set Participant 2 of Meeting 1
        ViewInteraction textInputEditText5 = onView(
                allOf(withId(R.id.create_meeting_teit_listOfParticipant),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.horizontal_scroll_view),
                                        0),
                                1)));
        textInputEditText5.perform(scrollTo(), replaceText(PARTICIPANT2 + "," ), closeSoftKeyboard());

        // TODO 27/12 : use this methode
        onView(withId(R.id.create_meeting_spi_room)).perform(click());
        onData(allOf(is(instanceOf(Integer.class)))).atPosition(3).perform(click());

        // Set Room of Meeting 1

       /* ViewInteraction appCompatSpinner = onView(
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
        */

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

        onView(isAssignableFrom(DatePicker.class)).perform(setDate(2020,2,30));

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

        onView(isAssignableFrom(TimePicker.class)).perform(setTime(10,10));

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
    }
    private void createMeetingNumberTwo() {
        // Click on FAB button for create new Meeting 2
        ViewInteraction floatingActionButton2 = onView(
                allOf(withId(R.id.fab),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                2),
                        isDisplayed()));
        floatingActionButton2.perform(click());

        // Set Subject of Meeting 2
        ViewInteraction textInputEditText3 = onView(
                allOf(withId(R.id.create_meeting_tiet_subject),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.create_meeting_til_subject),
                                        0),
                                0),
                        isDisplayed()));
        textInputEditText3.perform(replaceText("sujet 2"), closeSoftKeyboard());

        // Set Participant 1 of Meeting 2
        ViewInteraction textInputEditText4 = onView(
                allOf(withId(R.id.create_meeting_teit_listOfParticipant),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.horizontal_scroll_view),
                                        0),
                                1)));
        textInputEditText4.perform(scrollTo(), replaceText(PARTICIPANT1 + "," ), closeSoftKeyboard());

        // Set Participant 2 of Meeting 2
        ViewInteraction textInputEditText5 = onView(
                allOf(withId(R.id.create_meeting_teit_listOfParticipant),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.horizontal_scroll_view),
                                        0),
                                1)));
        textInputEditText5.perform(scrollTo(), replaceText(PARTICIPANT2 + "," ), closeSoftKeyboard());

        // Set Participant 3 of Meeting 2
        ViewInteraction textInputEditText6 = onView(
                allOf(withId(R.id.create_meeting_teit_listOfParticipant),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.horizontal_scroll_view),
                                        0),
                                1)));
        textInputEditText6.perform(scrollTo(), replaceText(PARTICIPANT3 + "," ), closeSoftKeyboard());

        // Set Room of Meeting 2
        ViewInteraction appCompatSpinner2 = onView(
                allOf(withId(R.id.create_meeting_spi_room),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                4),
                        isDisplayed()));
        appCompatSpinner2.perform(click());

        DataInteraction appCompatCheckedTextView2 = onData(anything())
                .inAdapterView(childAtPosition(
                        withClassName(is("android.widget.PopupWindow$PopupBackgroundView")),
                        0))
                .atPosition(1);
        appCompatCheckedTextView2.perform(click());

        // Set Date of Meeting 2
        ViewInteraction materialButton6 = onView(
                allOf(withId(R.id.create_meeting_bt_date), withText("Choisir la date"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                7),
                        isDisplayed()));
        materialButton6.perform(click());

        onView(isAssignableFrom(DatePicker.class)).perform(setDate(2020,5,11));

        ViewInteraction materialButton7 = onView(
                allOf(withId(android.R.id.button1), withText("OK"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                3)));
        materialButton7.perform(scrollTo(), click());

        // Set Hour of Meeting 2
        ViewInteraction materialButton8 = onView(
                allOf(withId(R.id.create_meeting_bt_hour), withText("Choisir lheure"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                5),
                        isDisplayed()));
        materialButton8.perform(click());

        onView(isAssignableFrom(TimePicker.class)).perform(setTime(12,20));

        ViewInteraction materialButton9 = onView(
                allOf(withId(android.R.id.button1), withText("OK"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                3)));
        materialButton9.perform(scrollTo(), click());

        // Valid meeting 2
        ViewInteraction materialButton10 = onView(
                allOf(withId(R.id.create_meeting_bt_valid_meeting), withText("Valider ma réunion"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                9),
                        isDisplayed()));
        materialButton10.perform(click());
    }
    private void createMeetingNumberThree() {
        // Click on FAB button for create new Meeting 3
        ViewInteraction floatingActionButton3 = onView(
                allOf(withId(R.id.fab),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                2),
                        isDisplayed()));
        floatingActionButton3.perform(click());

        // Set Subject of Meeting 3
        ViewInteraction textInputEditText6 = onView(
                allOf(withId(R.id.create_meeting_tiet_subject),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.create_meeting_til_subject),
                                        0),
                                0),
                        isDisplayed()));
        textInputEditText6.perform(replaceText("sujet 3"), closeSoftKeyboard());

        // Set Participant of Meeting 3
        ViewInteraction textInputEditText7 = onView(
                allOf(withId(R.id.create_meeting_teit_listOfParticipant),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.horizontal_scroll_view),
                                        0),
                                1)));
        textInputEditText7.perform(scrollTo(), replaceText(PARTICIPANT1 + "," ), closeSoftKeyboard());

        // TODO 27/12/2918 : how change room selected (actualy is always room 2)
        // Set Room of Meeting 3
        ViewInteraction appCompatSpinner3 = onView(
                allOf(withId(R.id.create_meeting_spi_room),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                4),
                        isDisplayed()));
        appCompatSpinner3.perform(click());

        DataInteraction appCompatCheckedTextView3 = onData(anything())
                .inAdapterView(childAtPosition(
                        withClassName(is("android.widget.PopupWindow$PopupBackgroundView")),
                        0))
                .atPosition(1);
        appCompatCheckedTextView3.perform(click());

        // Set Date of Meeting 3
        ViewInteraction materialButton11 = onView(
                allOf(withId(R.id.create_meeting_bt_date), withText("Choisir la date"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                7),
                        isDisplayed()));
        materialButton11.perform(click());

        onView(isAssignableFrom(DatePicker.class)).perform(setDate(2020,3,12));

        ViewInteraction materialButton12 = onView(
                allOf(withId(android.R.id.button1), withText("OK"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                3)));
        materialButton12.perform(scrollTo(), click());

        // Set Hour of Meeting 3
        ViewInteraction materialButton13 = onView(
                allOf(withId(R.id.create_meeting_bt_hour), withText("Choisir lheure"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                5),
                        isDisplayed()));
        materialButton13.perform(click());

        onView(isAssignableFrom(TimePicker.class)).perform(setTime(15,30));

        ViewInteraction materialButton14 = onView(
                allOf(withId(android.R.id.button1), withText("OK"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                3)));
        materialButton14.perform(scrollTo(), click());

        // Valid meeting 3
        ViewInteraction materialButton15 = onView(
                allOf(withId(R.id.create_meeting_bt_valid_meeting), withText("Valider ma réunion"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                9),
                        isDisplayed()));
        materialButton15.perform(click());
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
