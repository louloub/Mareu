package com.example.maru.view.ui;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.maru.service.model.Meeting;
import com.example.maru.service.model.MeetingJava;
import com.example.maru.utility.MeetingManager;
import com.example.maru.view.ui.model.MeetingUiModel;

import org.hamcrest.collection.IsIterableContainingInAnyOrder;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mockito;
import org.threeten.bp.LocalDate;
import org.threeten.bp.LocalDateTime;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.example.maru.view.ui.SortingType.ROOM_ALPHABETICAL_ASC;
import static org.junit.Assert.*;

public class MainViewModelTest {

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    private MeetingManager meetingManager;
    MainViewModel mainViewModel;

    private MutableLiveData<MeetingJava> meetingLiveData;
    private MutableLiveData<List<MeetingJava>> meetingListLiveData;
    private MutableLiveData<SortingType> mSortingTypeLiveData;
    private MutableLiveData<Integer> mSelectedFilterTypeLiveData;

    List<MeetingJava> listOfMeeting = new ArrayList<>();
    List<String> listOfParticipant = new ArrayList<>();
    MeetingJava meetingJava, meetingJava2;

    @Before
    public void setUp() throws Exception {
        // meetingManager = Mockito.mock(MeetingManager.class);
        meetingManager = MeetingManager.getInstance();

        meetingLiveData = new MutableLiveData<>();
        meetingListLiveData = new MutableLiveData<>();
        mSortingTypeLiveData = new MutableLiveData<>();
        mSelectedFilterTypeLiveData = new MutableLiveData<>();

        // Mockito.doReturn(meetingListLiveData).when(meetingManager).getMeetingLiveData();

        mainViewModel = new MainViewModel(meetingManager);
    }

    @Test
    public void addMeeting() {
        // Given
        mSortingTypeLiveData.setValue(ROOM_ALPHABETICAL_ASC);
        mSelectedFilterTypeLiveData.setValue(0);

        // When
        listOfParticipant.add("guillaume@yahoo.fr");
        listOfParticipant.add("email@email.fr");
        listOfParticipant.add("test@test.fr");

        meetingJava = new MeetingJava(
                0,
                LocalDate.of(2019,12,22),
                "15h15",
                2,
                "Sujet 1",
                listOfParticipant
        );
        MeetingManager.getInstance().addMeetingFromObject(meetingJava);
        listOfMeeting.add(meetingJava);

        meetingJava2 = new MeetingJava(
                0,
                LocalDate.of(2018,12,22),
                "15h15",
                2,
                "Sujet 1",
                listOfParticipant
        );

        // Then
        MeetingJava meetingJavaToTest = meetingManager.getMeetingList().get(0);
        // final boolean equals = meetingJavaToTest.equals(meetingJava);
        // assertEquals(true, equals);
        // assertEqualsHomeMade(meetingJavaToTest,meetingJava2);
        boolean equals = assertEqualsHomeMade(meetingJavaToTest,meetingJava);
        assertTrue(equals);
        // assertTrue();
        // equals(meetingManager.getMeetingList().contains(meetingJava));
        // assertTrue(meetingManager.getMeetingList().contains(meetingJava));
        // assertEquals(meetingManager.getMeetingList().get(0),meetingJava);
        // isEquals(meetingManager.getMeetingList().get(0),meetingJava);
    }

    static private boolean assertEqualsHomeMade (MeetingJava meetingJava1, MeetingJava meetingJava2){

        if (meetingJava1!=null) {
            if (meetingJava1.getSubject()!=null) {
                if (meetingJava1.getListOfEmailOfParticipant()!=null) {
                    if (meetingJava1.getRoom()!=0) {
                        if (meetingJava1.getDate()!=null) {
                            if (meetingJava1.getHour()!=null) {
                                if (meetingJava2!=null) {
                                    if (meetingJava2.getSubject()!=null && meetingJava2.getSubject().equals(meetingJava1.getSubject())) {
                                        if (meetingJava2.getListOfEmailOfParticipant()!=null && meetingJava2.getListOfEmailOfParticipant().equals(meetingJava1.getListOfEmailOfParticipant()) ) {
                                            if (meetingJava2.getRoom()!=0 && meetingJava2.getRoom()==meetingJava1.getRoom() ) {
                                                if (meetingJava2.getDate()!=null && meetingJava2.getDate().isEqual(meetingJava1.getDate())) {
                                                    if (meetingJava2.getHour()!=null && meetingJava2.getHour().equals(meetingJava1.getHour())) {
                                                        return true;
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    @Test
    public void getMeetings() {
        // List<MeetingJava> meetings = MeetingManager.getInstance().getMeetingList();

        // List<MeetingJava> expectedMeetings = DummyMeetingGenerator.DUMMY_MEETINGS;

        // assertThat(meetings, IsIterableContainingInAnyOrder.containsInAnyOrder(expectedMeetings.toArray()));
    }

    @Test
    public void should_desplay_two_meeting_when_meeting_manager_has_two () {
        // Given
        // List<MeetingJava> meetingJava = new ArrayList<MeetingJava>();
        // LOcalDate.off

        /*MeetingJava meetingJava1 = new MeetingJava();
        meetingJava.add(meetingJava1);
        MutableLiveData<List<MeetingJava>> mutableLiveData = new MutableLiveData<>();
        mutableLiveData.setValue(meetingJava);
        Mockito.when(meetingManager.getMeetingLiveData()).thenReturn(mutableLiveData);
        */

        // When
        // LiveData<List<MeetingUiModel>> result = mainViewModel.getUiModelsLiveData();

        // Then
        // assertEquals(1,result.getValue().size());
    }
}