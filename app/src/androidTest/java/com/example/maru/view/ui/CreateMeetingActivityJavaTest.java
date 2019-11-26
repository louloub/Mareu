package com.example.maru.view.ui;

import android.support.test.runner.AndroidJUnit4;

import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.test.rule.ActivityTestRule;

import com.example.maru.R;
import com.example.maru.service.model.MeetingJava;
import com.example.maru.view.ViewModelFactory;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.threeten.bp.LocalDate;
import org.threeten.bp.LocalDateTime;
import org.threeten.bp.LocalTime;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)

public class CreateMeetingActivityJavaTest {

    private CreateMeetingViewModel mCreateMeetingViewModel;
    private CreateMeetingActivityJava mActivity;
    private List<MeetingJava> mMeetingJava = DUMMY_MEETING;
    private static int POSITION_ITEM = 0;
    private LocalDateTime mTime;

    @Rule
    public ActivityTestRule<CreateMeetingActivityJava> mActivityRule =
            new ActivityTestRule(CreateMeetingActivityJava.class);

    @Before
    public void setUp() {
        mCreateMeetingViewModel = new ViewModelProvider((ViewModelStoreOwner) this,ViewModelFactory.getInstance()).get(CreateMeetingViewModel.class);
        mTime = LocalDateTime.of(LocalDate.now(),
                LocalTime.of(new Random().nextInt(24), new Random().nextInt(60),
                        new Random().nextInt(60), new Random().nextInt(999999999 + 1)));    }

    @Test
    public void myMeetingList_addMeeting_shouldAddMeeting() {
        // Given : We remove the element at position 2
        // onView(withId(R.id.main_rv)).check(assertArrayEquals();)

        // Given : create a meetingJava list
        mMeetingJava = generateNeighbours();

        // Create random meeting
        MeetingJava meetingJava = DUMMY_MEETING.get(new Random().nextInt(mMeetingJava.size()));

        onView(withId(R.id.create_meeting_tiet_subject)).perform();

        String sujet = meetingJava.getSubject();
        List<String> listOfParticipant = meetingJava.getListOfEmailOfParticipant();
        int room = meetingJava.getRoom();
        LocalDate date = meetingJava.getDate();
        String hour = meetingJava.getHour();


        LocalDateTime time = LocalDateTime.of(LocalDate.now(),
                LocalTime.of(new Random().nextInt(24), new Random().nextInt(60),
                        new Random().nextInt(60), new Random().nextInt(999999999 + 1)));


        /*onView(withId(R.id.list_favorites_neighbours))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, new DeleteViewAction()));*/

        /*// Given : create a neighbour for test at the position 0
        Neighbour neighbour = this.mNeighbours.get(POSITION_ITEM);
        // Neighbour neighbour = new Neighbour();

        // When : click on a item of neighbour list at position 0
        onView(withId(R.id.list_neighbours))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        // Then : attent to have the name of the neighbour in the TextView
        onView(withId(R.id.neighbourNameOnImage)).check(matches(withText(neighbour.getName())));*/
    }



    public static List<MeetingJava> DUMMY_MEETING = Arrays.asList(
            new MeetingJava(1, generateLocalDate, ),
            new MeetingJava(10, 2019-12-10, "20h00", 2, "Subject", mMeetingJava)
        );

    static List<MeetingJava> generateNeighbours() {
        return new ArrayList<>(DUMMY_MEETING);
    }

    static LocalDateTime generateLocalDateTime(){
        LocalDateTime time = null;

        time = LocalDateTime.of(LocalDate.now(),
                LocalTime.of(new Random().nextInt(24), new Random().nextInt(60),
                        new Random().nextInt(60), new Random().nextInt(999999999 + 1)));
        System.out.println(time);

        return time;
    }

    static LocalDate generateLocalDate(){
        LocalDate time = null;

        time = LocalDate.of(LocalDate.now(), LocalDate.of(2019, new Random().nextInt(12),new Random().nextInt(30)));
        // LocalDate.of(2019, 12,30);

        return time;
    }
}