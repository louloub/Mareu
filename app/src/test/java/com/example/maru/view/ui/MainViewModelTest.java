package com.example.maru.view.ui;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.maru.service.model.MeetingJava;
import com.example.maru.utility.MeetingManager;
import com.example.maru.view.ui.model.RoomFilterTypeUiModel;
import com.example.maru.view.ui.model.SortingTypeUiModel;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mockito;
import org.threeten.bp.LocalDate;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.MockitoAnnotations.initMocks;

/**
 UNIT TEST
 // GIVEN : Conditions préalables au test
 // WHEN : Une seule ligne : invoquation de la méthode qu'on souhaite tester
 // THEN : Changements qu'on attend en raison du comportement spécifié
 **/

public class MainViewModelTest {

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    private MeetingManager mMeetingManager;
    MainViewModel mainViewModel;

    private MutableLiveData<MeetingJava> mMeetingLiveData;
    private MutableLiveData<List<MeetingJava>> mMeetingListLiveData;
    private MutableLiveData<SortingType> mSortingTypeLiveData;
    private MutableLiveData<Integer> mSelectedFilterTypeLiveData;
    private MutableLiveData<SortingTypeUiModel> mSortingTypeUiModelLiveData;


    List<MeetingJava> listOfMeeting = new ArrayList<>();
    List<String> listOfParticipant = new ArrayList<>();
    MeetingJava meetingJava1, meetingJava2;

    @Before
    public void setUp() throws Exception {
        mMeetingManager = Mockito.mock(MeetingManager.class);

        mMeetingLiveData = new MutableLiveData<>();
        mMeetingListLiveData = new MutableLiveData<>();
        mSortingTypeLiveData = new MutableLiveData<>();
        mSelectedFilterTypeLiveData = new MutableLiveData<>();
        mSortingTypeUiModelLiveData = new MutableLiveData<>();

        Mockito.doReturn(mMeetingListLiveData).when(mMeetingManager).getMeetingListLiveData();
        Mockito.doReturn(listOfMeeting).when(mMeetingManager).getMeetingList();

        mainViewModel = new MainViewModel(mMeetingManager);

        listOfParticipant.add("test1@test.fr");
        listOfParticipant.add("test2@test.fr");
        listOfParticipant.add("test3@test.fr");
        meetingJava1 = new MeetingJava(0, LocalDate.of(2019,12,22), "15h15", 1, "Sujet 1", listOfParticipant);
        meetingJava2 = new MeetingJava(1, LocalDate.of(2018,12,22), "16h16", 2, "Sujet 2", listOfParticipant);
    }

    @Test
    public void addMeeting() {
        // GIVEN
        mMeetingManager = Mockito.mock(MeetingManager.class);
        initMocks(mMeetingManager);
        meetingJava1 = Mockito.mock(MeetingJava.class);
        meetingJava2 = Mockito.mock(MeetingJava.class);
        listOfMeeting.add(meetingJava1);
        listOfMeeting.add(meetingJava2);

        // WHEN
        mMeetingListLiveData.setValue(listOfMeeting);

        // THEN
        boolean isMeetingManagerEmpty = mMeetingListLiveData.getValue().isEmpty();
        assertTrue(!isMeetingManagerEmpty);
    }

    @Test
    public void getMeetings() {
        // GIVEN
        mMeetingManager = Mockito.mock(MeetingManager.class);
        initMocks(mMeetingManager);
        listOfMeeting.add(meetingJava1);
        listOfMeeting.add(meetingJava2);
        mMeetingListLiveData.setValue(listOfMeeting);

        // WHEN
        MeetingJava meetingJava3 = mMeetingListLiveData.getValue().get(0);

        // THEN
        boolean bool = assertEqualsHomeMade(meetingJava3,meetingJava1);
        assertTrue(bool);
    }

    @Test
    public void deleteMeeting() {
        // GIVEN
        mMeetingManager = Mockito.mock(MeetingManager.class);
        initMocks(mMeetingManager);
        listOfMeeting.add(meetingJava1);
        listOfMeeting.add(meetingJava2);
        mMeetingListLiveData.setValue(listOfMeeting);
        boolean bool;

        // WHEN
        mainViewModel.deleteMeeting(0);

        // THEN
        bool = mMeetingListLiveData.getValue().size() == 1;
        assertTrue(bool);
    }

    @Test
    public void setSortingTypeUiModel(){
        // GIVEN
        mMeetingManager = Mockito.mock(MeetingManager.class);
        initMocks(mMeetingManager);
        SortingTypeUiModel sortingTypeUiModel = Mockito.mock(SortingTypeUiModel.class);
        boolean bool;

        // WHEN
        mainViewModel.setSortingType("Croissant salle", sortingTypeUiModel);

        // THEN
        LiveData<Integer> i = mainViewModel.getSelectedSortingTypeIndexLiveDate();
        bool = i.getValue().equals(0);
        assertTrue(bool);
    }

    @Test
    public void setRoomFilterTypeUiMdel(){
        // GIVEN
        mMeetingManager = Mockito.mock(MeetingManager.class);
        initMocks(mMeetingManager);
        RoomFilterTypeUiModel roomFilterTypeUiModel = Mockito.mock(RoomFilterTypeUiModel.class);
        boolean bool;

        // WHEN
        mainViewModel.setRoomFilterType("toutes les salles",roomFilterTypeUiModel);

        // THEN
        LiveData<Integer> i = mainViewModel.getSelectedFilterTypeIndexLiveDate();
        bool = i.getValue().equals(0);
        assertTrue(bool);
    }

    @Test
    public void setDateFilterType(){
        // GIVEN

        // WHEN
        // mainViewModel.setDateFilterType();

        // THEN
    }

    @Test
    public void displayFilterRoomPopup(){
        // GIVEN

        // WHEN
        // mainViewModel.displayFilterRoomPopup();

        // THEN
    }

    @Test
    public void displayFilterDatePopup(){
        // GIVEN

        // WHEN
        // mainViewModel.displayFilterDatePopup();

        // THEN
    }

    @Test
    public void displaySortingTypePopup(){
        // GIVEN

        // WHEN
        // mainViewModel.displaySortingTypePopup();

        // THEN
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