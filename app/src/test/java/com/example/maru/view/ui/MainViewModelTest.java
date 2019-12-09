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
import static org.mockito.MockitoAnnotations.initMocks;

public class MainViewModelTest {

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    private MeetingManager meetingManager;
    MainViewModel mainViewModel;

    private MutableLiveData<MeetingJava> mMeetingLiveData;
    private MutableLiveData<List<MeetingJava>> mMeetingListLiveData;
    private MutableLiveData<SortingType> mSortingTypeLiveData;
    private MutableLiveData<Integer> mSelectedFilterTypeLiveData;

    List<MeetingJava> listOfMeeting = new ArrayList<>();
    List<String> listOfParticipant = new ArrayList<>();
    MeetingJava meetingJava, meetingJava2;

    @Before
    public void setUp() throws Exception {
        meetingManager = Mockito.mock(MeetingManager.class);
        // meetingManager = MeetingManager.getInstance();

        mMeetingLiveData = new MutableLiveData<>();
        mMeetingListLiveData = new MutableLiveData<>();
        mSortingTypeLiveData = new MutableLiveData<>();
        mSelectedFilterTypeLiveData = new MutableLiveData<>();

        Mockito.doReturn(mMeetingListLiveData).when(meetingManager).getMeetingLiveData();
        Mockito.doReturn(listOfMeeting).when(meetingManager).getMeetingList();

        mainViewModel = new MainViewModel(meetingManager);
    }

    @Test
    public void addMeeting() {
        // GIVEN : Conditions préalables au test
        meetingManager = Mockito.mock(MeetingManager.class);
        initMocks(meetingManager);
        meetingJava = Mockito.mock(MeetingJava.class);
        listOfMeeting.add(meetingJava);
        mMeetingListLiveData.setValue(listOfMeeting);

        // WHEN : Une seule ligne : invoquation de la méthode qu'on souhaite tester
        meetingManager.addMeetingFromObject(meetingJava);

        // THEN : Changements qu'on attend en raison du comportement spécifié
        boolean isMeetingManagerEmpty = mMeetingListLiveData.getValue().isEmpty();
        assertTrue(!isMeetingManagerEmpty);
    }

    @Test
    public void getMeetings() {

        // Given

        // When

        // Then

        // List<MeetingJava> meetings = MeetingManager.getInstance().getMeetingList();

        // List<MeetingJava> expectedMeetings = DummyMeetingGenerator.DUMMY_MEETINGS;

        // assertThat(meetings, IsIterableContainingInAnyOrder.containsInAnyOrder(expectedMeetings.toArray()));
    }

    @Test
    public void deleteMeeting() {

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

        listOfMeeting = MeetingManager.getInstance().getMeetingList();
        // MeetingJava meetingJava = listOfMeeting.get(0);
        listOfMeeting.remove(0);
        boolean bool;

        if (listOfMeeting.size()==0) {
            bool = true;
        } else {
            bool = false;
        }
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
}

/*
listOfParticipant.add("guillaume@yahoo.fr");
        listOfParticipant.add("email@email.fr");
        listOfParticipant.add("test@test.fr");*/

/*meetingJava = new MeetingJava(
                0,
                LocalDate.of(2019,12,22),
                "15h15",
                2,
                "Sujet 1",
                listOfParticipant
        );

        meetingJava2 = new MeetingJava(
                0,
                LocalDate.of(2019,12,22),
                "15h15",
                2,
                "Sujet 1",
                listOfParticipant
        );*/