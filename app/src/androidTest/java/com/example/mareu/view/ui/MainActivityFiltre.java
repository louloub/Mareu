package com.example.mareu.view.ui;


import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.DatePicker;
import android.widget.TimePicker;

import androidx.test.espresso.DataInteraction;
import androidx.test.espresso.ViewInteraction;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import com.example.mareu.R;
import com.example.mareu.service.model.Meeting;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.threeten.bp.LocalDate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.contrib.PickerActions.setDate;
import static androidx.test.espresso.contrib.PickerActions.setTime;
import static androidx.test.espresso.matcher.ViewMatchers.assertThat;
import static androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class MainActivityFiltre {

    private static final Comparator<Meeting> ROOM_COMPARATOR_MEETING_ASC = new Comparator<Meeting>() {
        @Override
        public int compare(Meeting e1, Meeting e2) {
            return (e1.getRoom() - e2.getRoom());
        }
    };
    private static final Comparator<Meeting> ROOM_COMPARATOR_MEETING_DSC = new Comparator<Meeting>() {
        @Override
        public int compare(Meeting e1, Meeting e2) {
            return (e2.getRoom() - e1.getRoom());
        }
    };
    private static final Comparator<Meeting> DATE_COMPARATOR_ASC = new Comparator<Meeting>() {
        @Override
        public int compare(Meeting e1, Meeting e2) {
            return (e1.getDate().compareTo(e2.getDate()));
        }
    };
    private static final Comparator<Meeting> DATE_COMPARATOR_DSC = new Comparator<Meeting>() {
        @Override
        public int compare(Meeting e1, Meeting e2) {
            return (e2.getDate().compareTo(e1.getDate()));
        }
    };

    private static final String PARTICIPANT1 = "participant1@google.fr";
    private static final String PARTICIPANT2 = "participant2@google.fr";
    private static final String PARTICIPANT3 = "participant3@google.fr";

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void mainActivityFiltre() {

        // Add Meeting
        Meeting meeting2 = createMeeting("Sujet 2",2,2020,5,11,12,20);

        // Add Meeting
        Meeting meeting3 = createMeeting("Sujet 3",3,2020,3,12,15,30);

        // Add Meeting
        Meeting meeting1 = createMeeting("Sujet 1",1,2020,2,27,10,10);

        // BUTTON FILTER SORT
        ViewInteraction actionMenuItemView = onView(
                allOf(withId(R.id.toolbar_bt_sort_meeting), withContentDescription("Settings"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.toolbar_tb_toolbar),
                                        1),
                                0),
                        isDisplayed()));
        actionMenuItemView.perform(click());

        // DSC ROOM
        DataInteraction appCompatCheckedTextView3 = onData(anything())
                .inAdapterView(allOf(withId(R.id.select_dialog_listview),
                        childAtPosition(
                                withId(R.id.contentPanel),
                                0)))
                .atPosition(1);
        appCompatCheckedTextView3.perform(click());

        ViewInteraction materialButton16 = onView(
                allOf(withId(android.R.id.button1), withText("Valider"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.buttonPanel),
                                        0),
                                3)));
        materialButton16.perform(scrollTo(), click());

        List<Meeting> meetingList = new ArrayList<Meeting>();
        meetingList.add(meeting2);
        meetingList.add(meeting3);
        meetingList.add(meeting1);

        Collections.sort(meetingList, ROOM_COMPARATOR_MEETING_DSC);

        assertEquals(3,meetingList.get(0).getRoom());

        /*// TODO : check if list is in good order with 3 meeting
        // BUTTON FILTER SORT
        ViewInteraction actionMenuItemView2 = onView(
                allOf(withId(R.id.toolbar_bt_sort_meeting), withContentDescription("Settings"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.toolbar_tb_toolbar),
                                        1),
                                0),
                        isDisplayed()));
        actionMenuItemView2.perform(click());

        // ASC DATE
        DataInteraction appCompatCheckedTextView4 = onData(anything())
                .inAdapterView(allOf(withId(R.id.select_dialog_listview),
                        childAtPosition(
                                withId(R.id.contentPanel),
                                0)))
                .atPosition(2);
        appCompatCheckedTextView4.perform(click());

        ViewInteraction materialButton17 = onView(
                allOf(withId(android.R.id.button1), withText("Valider"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.buttonPanel),
                                        0),
                                3)));
        materialButton17.perform(scrollTo(), click());

        // TODO : check if list is in good order
        // BUTTON FILTER SORT
        ViewInteraction actionMenuItemView3 = onView(
                allOf(withId(R.id.toolbar_bt_sort_meeting), withContentDescription("Settings"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.toolbar_tb_toolbar),
                                        1),
                                0),
                        isDisplayed()));
        actionMenuItemView3.perform(click());

        // DSC DATE
        DataInteraction appCompatCheckedTextView5 = onData(anything())
                .inAdapterView(allOf(withId(R.id.select_dialog_listview),
                        childAtPosition(
                                withId(R.id.contentPanel),
                                0)))
                .atPosition(3);
        appCompatCheckedTextView5.perform(click());

        ViewInteraction materialButton18 = onView(
                allOf(withId(android.R.id.button1), withText("Valider"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.buttonPanel),
                                        0),
                                3)));
        materialButton18.perform(scrollTo(), click());

        // TODO : check if list is in good order
        // BUTTON FILTER ROOM
        ViewInteraction actionMenuItemView4 = onView(
                allOf(withId(R.id.toolbar_bt_filter_room_meeting), withContentDescription("Settings"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.toolbar_tb_toolbar),
                                        1),
                                1),
                        isDisplayed()));
        actionMenuItemView4.perform(click());

        // ROOM 1
        DataInteraction appCompatCheckedTextView6 = onData(anything())
                .inAdapterView(allOf(withId(R.id.select_dialog_listview),
                        childAtPosition(
                                withId(R.id.contentPanel),
                                0)))
                .atPosition(1);
        appCompatCheckedTextView6.perform(click());

        ViewInteraction materialButton19 = onView(
                allOf(withId(android.R.id.button1), withText("Valider"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.buttonPanel),
                                        0),
                                3)));
        materialButton19.perform(scrollTo(), click());

        // TODO : check if list is in good order

        // ROOM 2
        ViewInteraction actionMenuItemView5 = onView(
                allOf(withId(R.id.toolbar_bt_filter_room_meeting), withContentDescription("Settings"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.toolbar_tb_toolbar),
                                        1),
                                1),
                        isDisplayed()));
        actionMenuItemView5.perform(click());

        DataInteraction appCompatCheckedTextView7 = onData(anything())
                .inAdapterView(allOf(withId(R.id.select_dialog_listview),
                        childAtPosition(
                                withId(R.id.contentPanel),
                                0)))
                .atPosition(2);
        appCompatCheckedTextView7.perform(click());

        ViewInteraction materialButton20 = onView(
                allOf(withId(android.R.id.button1), withText("Valider"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.buttonPanel),
                                        0),
                                3)));
        materialButton20.perform(scrollTo(), click());

        // TODO : check if list is in good order

        // ROOM 3
        ViewInteraction actionMenuItemView6 = onView(
                allOf(withId(R.id.toolbar_bt_filter_room_meeting), withContentDescription("Settings"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.toolbar_tb_toolbar),
                                        1),
                                1),
                        isDisplayed()));
        actionMenuItemView6.perform(click());

        DataInteraction appCompatCheckedTextView8 = onData(anything())
                .inAdapterView(allOf(withId(R.id.select_dialog_listview),
                        childAtPosition(
                                withId(R.id.contentPanel),
                                0)))
                .atPosition(3);
        appCompatCheckedTextView8.perform(click());

        ViewInteraction materialButton21 = onView(
                allOf(withId(android.R.id.button1), withText("Valider"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.buttonPanel),
                                        0),
                                3)));
        materialButton21.perform(scrollTo(), click());

        // TODO : check if list is in good order

        // ALL ROOM

        ViewInteraction actionMenuItemView7 = onView(
                allOf(withId(R.id.toolbar_bt_filter_room_meeting), withContentDescription("Settings"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.toolbar_tb_toolbar),
                                        1),
                                1),
                        isDisplayed()));
        actionMenuItemView7.perform(click());

        DataInteraction appCompatCheckedTextView9 = onData(anything())
                .inAdapterView(allOf(withId(R.id.select_dialog_listview),
                        childAtPosition(
                                withId(R.id.contentPanel),
                                0)))
                .atPosition(0);
        appCompatCheckedTextView9.perform(click());

        ViewInteraction materialButton22 = onView(
                allOf(withId(android.R.id.button1), withText("Valider"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.buttonPanel),
                                        0),
                                3)));
        materialButton22.perform(scrollTo(), click());

        // TODO : check if list is in good order*/

    }

    private Matcher<? super List<Integer>> isInDescendingOrdering()
    {
        return new TypeSafeMatcher<List<Integer>>()
        {
            @Override
            public void describeTo (Description description)
            {
                description.appendText("describe the error has you like more");
            }

            @Override
            protected boolean matchesSafely (List<Integer> item)
            {
                for(int i = 0 ; i < item.size() -1; i++) {
                    if(item.get(i) <= item.get(i+1)) return false;
                }
                return true;
            }
        };
    }

    private Meeting createMeeting(String subject, int room, int year, int month, int day, int hour, int minutes){
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

        List<String> listOfEmailOfParticipant = new ArrayList<>();
        listOfEmailOfParticipant.add(PARTICIPANT1);
        listOfEmailOfParticipant.add(PARTICIPANT2);
        listOfEmailOfParticipant.add(PARTICIPANT3);

        LocalDate date = LocalDate.of(year,month,day);
        String hourString = String.valueOf(hour);

        return new Meeting(0,date,hourString,room,subject,listOfEmailOfParticipant);
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
