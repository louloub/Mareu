package com.example.maru.view.ui;

import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.contrib.PickerActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.rule.ActivityTestRule;

import com.example.maru.R;
import com.example.maru.service.model.MeetingJava;
import com.example.maru.utility.MeetingManager;
import com.example.maru.view.ViewModelFactory;

import org.hamcrest.Matcher;
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
import static androidx.test.espresso.contrib.PickerActions.setTime;
import static androidx.test.espresso.matcher.ViewMatchers.hasMinimumChildCount;
import static androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withSpinnerText;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.AllOf.allOf;
import static org.hamcrest.core.StringContains.containsString;
import static org.hamcrest.core.StringStartsWith.startsWith;
import static org.hamcrest.object.HasToString.hasToString;

public class ListMeetingActivityTest {

    private CreateMeetingViewModel mCreateMeetingViewModel;

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(MainActivity.class);
    private MeetingManager service;

    @Before
    public void setUp() {
        MainActivity activity = mActivityRule.getActivity();
        List<MeetingJava> MeetingJavaList = new ArrayList<>();
        // Mockito.doReturn(mMeetingListLiveData).when(mMeetingManager).getMeetingListLiveData();
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
        int monthRandom, dayRandom, roomRandom, hourRandom, minutesRandom;

        // Create loop for add 5 meeting to list
        for (int i = 0; i < 3; i++) {
            int yearRandom = 2020;
            monthRandom = random.nextInt(12-1) + 1;
            dayRandom = random.nextInt(30-1) + 1;
            hourRandom = random.nextInt(24-1) + 1;
            minutesRandom = random.nextInt(60-1) + 1;

            String dayRandomFormat = String.format("%02d", dayRandom);
            String monthRandomFormat = String.format("%02d", monthRandom);
            String hourRandomFormat = String.format("%02d", hourRandom);
            String minutesRandomFormat = String.format("%02d", minutesRandom);

            // Set Subject of Meeting
            onView(withId(R.id.create_meeting_tiet_subject))
                    .perform(typeText("Subject " +i++));
            // Set Participant of Meeting
            onView(withId(R.id.create_meeting_teit_listOfParticipant))
                    .perform(typeText("Participant " +i++ + ", Participant " + i++ + ", Participant " + i++ + ","));
            // Set Room of Meeting
            onView(withId(R.id.create_meeting_spi_room)).perform(click());
            roomRandom = random.nextInt(10-1) +1;

            onData(allOf(is(instanceOf(Integer.class)), is(roomRandom))).perform(click());
            onView(withId(R.id.create_meeting_spi_room)).check(matches(withSpinnerText(containsString(String.valueOf(roomRandom)))));

            // Set Date of Meeting
            onView(withId(R.id.create_meeting_bt_date)).perform(click());
            onView(isAssignableFrom(DatePicker.class)).perform(setDate(yearRandom, monthRandom, dayRandom));
            onView(withId(android.R.id.button2)).perform(click());
            onView(withId(R.id.create_meeting_et_edit_date))
                    .perform(setTextInTextView("" +dayRandomFormat+ "/" +monthRandomFormat+ "/" +yearRandom));

            // Set Hour of Meeting
            onView(withId(R.id.create_meeting_bt_hour)).perform(click());
            onView(isAssignableFrom(TimePicker.class)).perform(setTime(hourRandom,minutesRandom));
            onView(withId(android.R.id.button2)).perform(click());
            onView(withId(R.id.create_meeting_et_edit_hour))
                    .perform(setTextInTextView("" +hourRandomFormat+ "h" +minutesRandomFormat ));

            // Valid meeting
            onView(withId(R.id.create_meeting_bt_valid_meeting)).perform(click());
        }
    }

    /**
     * We ensure that our recyclerview is displaying all the meeting
     */
    @Test
    public void myMeetingList_displayTheMeetings() {
        // First scroll to the position that needs to be matched and click on it.
        onView(withId(R.id.main_rv))
                .check(matches(hasMinimumChildCount(3)));
    }

    public static ViewAction setTextInTextView(final String value){
        return new ViewAction() {
            @SuppressWarnings("unchecked")
            @Override
            public Matcher<View> getConstraints() {
                return allOf(isDisplayed(), isAssignableFrom(TextView.class));
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