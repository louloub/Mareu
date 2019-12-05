package com.example.maru.view.ui;

import android.widget.DatePicker;

import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.test.espresso.contrib.PickerActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.rule.ActivityTestRule;

import com.example.maru.R;
import com.example.maru.service.model.MeetingJava;
import com.example.maru.view.ViewModelFactory;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.threeten.bp.LocalDate;
import org.threeten.bp.LocalDateTime;
import org.threeten.bp.LocalTime;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.contrib.PickerActions.setDate;
import static androidx.test.espresso.matcher.ViewMatchers.hasMinimumChildCount;
import static androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.AllOf.allOf;
import static org.hamcrest.core.StringStartsWith.startsWith;
import static org.hamcrest.object.HasToString.hasToString;

public class ListMeetingActivityTest {

    private CreateMeetingViewModel mCreateMeetingViewModel;

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule =
            new ActivityTestRule(MainActivity.class);

    @Before
    public void setUp() {
        MainActivity activity = mActivityRule.getActivity();
        List<MeetingJava> MeetingJavaList = new ArrayList<MeetingJava>();

        // mCreateMeetingViewModel = new ViewModelProvider(this, ViewModelFactory.getInstance()).get(CreateMeetingViewModel.class);
    }

    /**
     * Add Meeting On list
     */
    @Test
    public void myMeetingList_addMeetings() {
        // Click on FAB button for create new Meeting
        onView(withId(R.id.fab)).perform(click());

        // Create Random
        Random random = new Random();
        int monthRandom, dayRandom, roomRandom;
        // Create loop for add 5 meeting to list
        for (int i = 0; i < 3; i++) {
            int yearRandom = random.nextInt(2020 - 2019) + 2019;
            if (yearRandom == 2020) {
                monthRandom = random.nextInt(12-1) + 1;
                dayRandom = random.nextInt(30-1) + 1;
            } else {
                monthRandom = 12;
                dayRandom = random.nextInt(30-5) + 5;
            }
            // Set Subject of Meeting
            onView(withId(R.id.create_meeting_tiet_subject))
                    .perform(typeText("Subject " +i++));
            // Set Participant of Meeting
            onView(withId(R.id.create_meeting_teit_listOfParticipant))
                    .perform(typeText("Participant " +i++ + ", Participant " + i++ + ", Participant " + i++ + ","));
            // Set Room of Meeting
            onView(withId(R.id.create_meeting_spi_room)).perform(click());
            roomRandom = random.nextInt(10-1) +1;
            onData(hasToString(startsWith(String.valueOf(roomRandom)))).perform(click());

            // Set Date of Meeting
            onView(withId(R.id.create_meeting_bt_date)).perform(click());
            onView(isAssignableFrom(DatePicker.class)).perform(setDate(yearRandom, monthRandom, dayRandom));

            // onView(withId(R.id.create_meeting_bt_date)).perform(setDate(2017, 6, 30));

            // Set Hour of Meeting
            onView(withId(R.id.create_meeting_bt_hour)).perform(click());
            /*onData(allOf(is(instanceOf(Integer.class)), is(i))).perform(click());
            onData(allOf(is(instanceOf(Integer.class)), is(i))).perform(click());
            onData(allOf(is(instanceOf(Integer.class)), is(i))).perform(click());*/

            // Valid Meeting
            onView(withId(R.id.create_meeting_bt_valid_meeting)).perform(click());
        }
    }

    /**
     * We ensure that our recyclerview is displaying all the meeting which are in the meeting API
     */
    @Test
    public void myMeetingList_displayTheMeetings() {
        // First scroll to the position that needs to be matched and click on it.
        onView(withId(R.id.main_rv))
                .check(matches(hasMinimumChildCount(3)));
    }
}