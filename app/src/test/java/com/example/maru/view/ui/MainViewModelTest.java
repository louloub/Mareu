package com.example.maru.view.ui;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.maru.LiveDataTestUtil;
import com.example.maru.service.model.MeetingJava;
import com.example.maru.utility.MeetingManager;
import com.example.maru.view.ui.model.DateFilterTypeUiModel;
import com.example.maru.view.ui.model.MeetingUiModel;
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

    @Before
    public void setUp() throws Exception {
        mMeetingManager = Mockito.mock(MeetingManager.class);

        mMeetingLiveData = new MutableLiveData<>();
        mMeetingListLiveData = new MutableLiveData<>();
        mSortingTypeLiveData = new MutableLiveData<>();
        mSelectedFilterTypeLiveData = new MutableLiveData<>();
        mSortingTypeUiModelLiveData = new MutableLiveData<>();

        Mockito.doReturn(mMeetingListLiveData).when(mMeetingManager).getMeetingListLiveData();

        mainViewModel = new MainViewModel(mMeetingManager);
    }

    private List <String> getParticipants() {
        List<String> listOfParticipant = new ArrayList<>();
        listOfParticipant.add("test1@test.fr");
        listOfParticipant.add("test2@test.fr");
        listOfParticipant.add("test3@test.fr");
        return listOfParticipant;
    }

    // WORKS
    @Test
    public void shouldDisplayTwoMeetingsUiModelsWhenRepositoryHasTwoMeetings() throws InterruptedException {
        // GIVEN
        MeetingJava meetingJava1 = new MeetingJava(0, LocalDate.of(2019,12,22), "15h15", 1, "Sujet 1", getParticipants());
        MeetingJava meetingJava2 = new MeetingJava(1, LocalDate.of(2018,12,23), "16h16", 1, "Sujet 2", getParticipants());
        List <MeetingJava> meetingJavaList = new ArrayList<>();
        meetingJavaList.add(meetingJava1);
        meetingJavaList.add(meetingJava2);
        mMeetingListLiveData.setValue(meetingJavaList);
        Mockito.doReturn(mMeetingListLiveData).when(mMeetingManager).getMeetingListLiveData();

        // WHEN
        List<MeetingUiModel> result = LiveDataTestUtil.getOrAwaitValue(mainViewModel.getUiModelsLiveData());

        // THEN
        assertEquals(2,result.size());

        // TODO 11/12/19 : faire la collection d'assertion (id, date, sujet ..) check MainViewModelTest project MMVVM NINO
    }

    // TODO 11/12/19 : check les IF pour voir ce qu'on doit test
    // TODO 11/12/19 : tester ce qu'affiche le viewModel
    // TODO 11/12/19 : tester toutes les possibilités du setSortingType et des autres filtres etc

    /*@Test
    public void deleteMeeting() {
        // GIVEN
        listOfMeeting.add(meetingJava1);
        listOfMeeting.add(meetingJava2);
        mMeetingListLiveData.setValue(listOfMeeting);

        // WHEN
        mainViewModel.deleteMeeting(0);

        // THEN
        boolean bool = mMeetingListLiveData.getValue().size() == 1;
        assertTrue(bool);
    }*/

    // WORKS (à verifier par Nino)
    @Test
    public void shouldMeetingAreSortingWithRoomAscendantWhenThisSortIsChoice() throws InterruptedException  {
        // GIVEN
        MeetingJava meetingJava1 = new MeetingJava(0, LocalDate.of(2019,12,22), "15h15", 1, "Sujet 1", getParticipants());
        MeetingJava meetingJava2 = new MeetingJava(1, LocalDate.of(2018,12,23), "16h16", 2, "Sujet 2", getParticipants());
        MeetingJava meetingJava3 = new MeetingJava(2, LocalDate.of(2017,12,22), "13h13", 3, "Sujet 3", getParticipants());
        List <MeetingJava> meetingJavaList = new ArrayList<>();
        meetingJavaList.add(meetingJava1);
        meetingJavaList.add(meetingJava2);
        meetingJavaList.add(meetingJava3);
        mMeetingListLiveData.setValue(meetingJavaList);
        Mockito.doReturn(mMeetingListLiveData).when(mMeetingManager).getMeetingListLiveData();
        mainViewModel.setSortingType("Croissant salle", Mockito.mock(SortingTypeUiModel.class));

        // WHEN
        List<MeetingUiModel> result = LiveDataTestUtil.getOrAwaitValue(mainViewModel.getUiModelsLiveData());

        // THEN
        assertEquals(0,result.get(0).getId());
        assertEquals(1,result.get(1).getId());
        assertEquals(2,result.get(2).getId());
    }

    // WORKS (à verifier par Nino)
    @Test
    public void shouldMeetingAreSortingWithRoomDescendantWhenThisSortIsChoice() throws InterruptedException {
        // GIVEN
        MeetingJava meetingJava1 = new MeetingJava(0, LocalDate.of(2019,12,22), "15h15", 1, "Sujet 1", getParticipants());
        MeetingJava meetingJava2 = new MeetingJava(1, LocalDate.of(2018,12,23), "16h16", 2, "Sujet 2", getParticipants());
        MeetingJava meetingJava3 = new MeetingJava(2, LocalDate.of(2017,12,22), "13h13", 3, "Sujet 3", getParticipants());
        List <MeetingJava> meetingJavaList = new ArrayList<>();
        meetingJavaList.add(meetingJava1);
        meetingJavaList.add(meetingJava2);
        meetingJavaList.add(meetingJava3);
        mMeetingListLiveData.setValue(meetingJavaList);
        Mockito.doReturn(mMeetingListLiveData).when(mMeetingManager).getMeetingListLiveData();
        mainViewModel.setSortingType("Decroissant salle", Mockito.mock(SortingTypeUiModel.class));

        // WHEN
        List<MeetingUiModel> result = LiveDataTestUtil.getOrAwaitValue(mainViewModel.getUiModelsLiveData());

        // THEN
        assertEquals(2,result.get(0).getId());
        assertEquals(1,result.get(1).getId());
        assertEquals(0,result.get(2).getId());
    }

    // WORKS (à verifier par Nino)
    @Test
    public void shouldMeetingAreSortingWithDateAscendantWhenThisSortIsChoice() throws InterruptedException {
        // GIVEN
        MeetingJava meetingJava1 = new MeetingJava(0, LocalDate.of(2019,12,22), "15h15", 1, "Sujet 1", getParticipants());
        MeetingJava meetingJava2 = new MeetingJava(1, LocalDate.of(2018,12,23), "16h16", 2, "Sujet 2", getParticipants());
        MeetingJava meetingJava3 = new MeetingJava(2, LocalDate.of(2017,12,22), "13h13", 3, "Sujet 3", getParticipants());
        List <MeetingJava> meetingJavaList = new ArrayList<>();
        meetingJavaList.add(meetingJava1);
        meetingJavaList.add(meetingJava2);
        meetingJavaList.add(meetingJava3);
        mMeetingListLiveData.setValue(meetingJavaList);
        Mockito.doReturn(mMeetingListLiveData).when(mMeetingManager).getMeetingListLiveData();
        List<MeetingUiModel> result = LiveDataTestUtil.getOrAwaitValue(mainViewModel.getUiModelsLiveData());

        // WHEN
        mainViewModel.setSortingType("Croissant date", Mockito.mock(SortingTypeUiModel.class));

        // THEN
        assertEquals("2019-12-22",result.get(0).getDate().toString());
        assertEquals("2018-12-23",result.get(1).getDate().toString());
        assertEquals("2017-12-22",result.get(2).getDate().toString());
    }

    // WORKING ON
    @Test
    public void shouldMeetingAreSortingWithDateDescendantWhenThisSortIsChoice() throws InterruptedException {
        // GIVEN
        MeetingJava meetingJava1 = new MeetingJava(0, LocalDate.of(2019,12,22), "15h15", 1, "Sujet 1", getParticipants());
        MeetingJava meetingJava2 = new MeetingJava(1, LocalDate.of(2018,12,23), "16h16", 2, "Sujet 2", getParticipants());
        MeetingJava meetingJava3 = new MeetingJava(2, LocalDate.of(2017,12,22), "13h13", 3, "Sujet 3", getParticipants());
        List <MeetingJava> meetingJavaList = new ArrayList<>();
        meetingJavaList.add(meetingJava1);
        meetingJavaList.add(meetingJava2);
        meetingJavaList.add(meetingJava3);
        mMeetingListLiveData.setValue(meetingJavaList);
        Mockito.doReturn(mMeetingListLiveData).when(mMeetingManager).getMeetingListLiveData();
        List<MeetingUiModel> result = LiveDataTestUtil.getOrAwaitValue(mainViewModel.getUiModelsLiveData());

        // WHEN
        mainViewModel.setSortingType("Croissant date", Mockito.mock(SortingTypeUiModel.class));

        // THEN
        assertEquals("2017-12-22",result.get(2).getDate().toString());
        assertEquals("2018-12-23",result.get(1).getDate().toString());
        assertEquals("2019-12-22",result.get(0).getDate().toString());
    }

    @Test
    public void setSortingTypeUiModel(){
        // GIVEN
        SortingTypeUiModel sortingTypeUiModel = Mockito.mock(SortingTypeUiModel.class);

        // WHEN
        mainViewModel.setSortingType("Croissant salle", sortingTypeUiModel);

        // THEN
        LiveData<Integer> i = mainViewModel.getSelectedSortingTypeIndexLiveData();
        boolean bool = i.getValue().equals(0);
        assertTrue(bool);
    }

    @Test
    public void setRoomFilterTypeUiMdel(){
        // GIVEN
        RoomFilterTypeUiModel roomFilterTypeUiModel = Mockito.mock(RoomFilterTypeUiModel.class);

        // WHEN
        mainViewModel.setRoomFilterType("toutes les salles",roomFilterTypeUiModel);

        // THEN
        LiveData<Integer> i = mainViewModel.getSelectedFilterTypeIndexLiveData();
        boolean bool = i.getValue().equals(0);
        assertTrue(bool);
    }

    @Test
    public void setDateFilterTypeUiModel(){
        // GIVEN
        DateFilterTypeUiModel dateFilterTypeUiModel = Mockito.mock(DateFilterTypeUiModel.class);
        boolean bool;
        String dateForFilter = "2019-12-30";

        // WHEN
        mainViewModel.setDateFilterType(dateForFilter);

        // THEN
        // bool = mainViewModel.getDate
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