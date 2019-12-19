package com.example.maru.view.ui;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.test.espresso.DataInteraction;
import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.ViewInteraction;
import androidx.test.espresso.contrib.PickerActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.rule.ActivityTestRule;

import com.example.maru.AndroidTestUtil;
import com.example.maru.R;
import com.example.maru.service.model.MeetingJava;
import com.example.maru.utility.MeetingManager;
import com.example.maru.view.ViewModelFactory;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.threeten.bp.LocalDate;
import org.threeten.bp.LocalDateTime;
import org.threeten.bp.LocalTime;
import org.threeten.bp.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.contrib.PickerActions.setDate;
import static androidx.test.espresso.contrib.PickerActions.setTime;
import static androidx.test.espresso.matcher.ViewMatchers.hasMinimumChildCount;
import static androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withSpinnerText;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertTrue;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.core.AllOf.allOf;
import static org.hamcrest.core.StringContains.containsString;
import static org.hamcrest.core.StringStartsWith.startsWith;
import static org.hamcrest.object.HasToString.hasToString;

public class MainActivityTest3 {

    private CreateMeetingViewModel mCreateMeetingViewModel;

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(MainActivity.class);
    private MeetingManager service;
    List<MeetingJava> meetingList = new ArrayList<>();
    List<String> participantList = new ArrayList<>();
    private int i;
    private MeetingManager mMeetingManager;

    @Before
    public void setUp() {
        MainActivity activity = mActivityRule.getActivity();
    }

    /**
     * Add Meeting On list
     */
    @Test
    public void myMeetingList_addMeeting() throws InterruptedException {
        // Click on FAB button for create new Meeting
        onView(withId(R.id.fab)).perform(click());

        // TODO 18/11/19 : never use RANDOM dans les tests !

        // Set Subject of Meeting
        String subject = "Subject 1";
        onView(withId(R.id.create_meeting_tiet_subject))
                .perform(typeText(subject));

        // Set Participant of Meeting
        String participant1 = "participant@yourdj.fr ,";
        String participant2 = "toi@moi.fr ,";
        String participant3 = "tonemail@gmail.fr ,";
        onView(withId(R.id.create_meeting_teit_listOfParticipant))
                .perform(typeText(participant1+participant2+participant3));

        // Set Room of Meeting
        onView(withId(R.id.create_meeting_spi_room)).perform(click());
        onData(allOf(is(instanceOf(Integer.class)), is(3))).perform(click());
        onView(withId(R.id.create_meeting_spi_room)).check(matches(withSpinnerText(containsString(String.valueOf(3)))));

        // Set Date of Meeting
        onView(withId(R.id.create_meeting_bt_date)).perform(click());
        onView(isAssignableFrom(DatePicker.class)).perform(setDate(2020,1,30));
        onView(withId(android.R.id.button2)).perform(click());
        onView(withId(R.id.create_meeting_et_edit_date))
                .perform(setTextInTextView("30-01-2020"));

        // Set Hour of Meeting
        onView(withId(R.id.create_meeting_bt_hour)).perform(click());
        onView(isAssignableFrom(TimePicker.class)).perform(setTime(15,30));
        onView(withId(android.R.id.button2)).perform(click());
        onView(withId(R.id.create_meeting_et_edit_hour))
                .perform(setTextInTextView("15h30"));

        // Valid meeting
        onView(withId(R.id.create_meeting_bt_valid_meeting)).perform(click());

        // Increment variable i
        i++;

        // Sleep 5s
        Thread.sleep(5000);

        // Check if recycler view as one meeting
        onView(withId(R.id.main_rv)).check(new AndroidTestUtil.RecyclerViewItemCountAssertion(1));
    }

    @Test
    public void myMeetingList_displayMeetings() {



        // when(apiClient.fetchNews()).thenReturn(null);
        // assertNotNull(mainViewModel.getmMeetingListLiveData());
        // assertTrue(viewModel.getNewsListState().hasObservers());
    }

    // TODO 18/11/19 : supprimer meeting dans une list de plusieurs meetins et verifier que c'est le bon supprimer

    /**
     * Retrive and display Meeting On list
     */
    @Test
    public void myListOfMeetingIsDisplay(){

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