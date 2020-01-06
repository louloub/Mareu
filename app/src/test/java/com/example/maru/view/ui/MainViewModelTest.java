package com.example.maru.view.ui;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.MutableLiveData;

import com.example.maru.LiveDataTestUtil;
import com.example.maru.service.model.Meeting;
import com.example.maru.utility.MeetingManager;
import com.example.maru.view.ui.model.MeetingUiModel;
import com.example.maru.view.ui.model.RoomFilterTypeUiModel;
import com.example.maru.view.ui.model.SortingTypeUiModel;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.hasItemInArray;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder;
import static org.hamcrest.collection.IsIterableContainingInOrder.contains;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mockito;
import org.threeten.bp.LocalDate;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder;
import static org.junit.Assert.*;

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
    private MainViewModel mainViewModel;
    private MutableLiveData<List<Meeting>> mMeetingListLiveData;

    @Before
    public void setUp() throws Exception {
        mMeetingManager = Mockito.mock(MeetingManager.class);
        mMeetingListLiveData = new MutableLiveData<>();
        Mockito.doReturn(mMeetingListLiveData).when(mMeetingManager).getMeetingListLiveData();
        mainViewModel = new MainViewModel(mMeetingManager);
    }

    private List<String> getParticipants() {
        List<String> listOfParticipant = new ArrayList<>();
        listOfParticipant.add("test1@test.fr");
        listOfParticipant.add("test2@test.fr");
        listOfParticipant.add("test3@test.fr");
        return listOfParticipant;
    }

    private void givenThreeDifferentMeetingForSortingTypeAndFilterTest() {
        Meeting meeting1 = new Meeting(0, LocalDate.of(2019, 12, 22), "15h15", 1, "Sujet 1", getParticipants());
        Meeting meeting2 = new Meeting(1, LocalDate.of(2018, 12, 23), "16h16", 2, "Sujet 2", getParticipants());
        Meeting meeting3 = new Meeting(2, LocalDate.of(2017, 12, 22), "13h13", 3, "Sujet 3", getParticipants());
        List<Meeting> meetingList = new ArrayList<>();
        meetingList.add(meeting1);
        meetingList.add(meeting2);
        meetingList.add(meeting3);
        mMeetingListLiveData.setValue(meetingList);
        Mockito.doReturn(mMeetingListLiveData).when(mMeetingManager).getMeetingListLiveData();
    }

    private void givenTwoSimilareMeetingDateAndOneOtherForSortingTypeAndFilterTest() {
        Meeting meeting1 = new Meeting(0, LocalDate.of(2019, 12, 22), "15h15", 1, "Sujet 1", getParticipants());
        Meeting meeting2 = new Meeting(1, LocalDate.of(2020, 12, 23), "16h16", 2, "Sujet 2", getParticipants());
        Meeting meeting3 = new Meeting(2, LocalDate.of(2020, 12, 23), "16h16", 3, "Sujet 3", getParticipants());
        List<Meeting> meetingList = new ArrayList<>();
        meetingList.add(meeting1);
        meetingList.add(meeting2);
        meetingList.add(meeting3);
        mMeetingListLiveData.setValue(meetingList);
        Mockito.doReturn(mMeetingListLiveData).when(mMeetingManager).getMeetingListLiveData();
    }

    private void givenTwoSimilareMeetingRoomAndOneOtherForSortingTypeAndFilterTest() {
        Meeting meeting1 = new Meeting(0, LocalDate.of(2019, 12, 22), "15h15", 1, "Sujet 1", getParticipants());
        Meeting meeting2 = new Meeting(1, LocalDate.of(2020, 12, 23), "16h16", 2, "Sujet 2", getParticipants());
        Meeting meeting3 = new Meeting(2, LocalDate.of(2020, 12, 23), "16h16", 2, "Sujet 3", getParticipants());
        List<Meeting> meetingList = new ArrayList<>();
        meetingList.add(meeting1);
        meetingList.add(meeting2);
        meetingList.add(meeting3);
        mMeetingListLiveData.setValue(meetingList);
        Mockito.doReturn(mMeetingListLiveData).when(mMeetingManager).getMeetingListLiveData();
    }

    @Test
    public void shouldDisplayTwoMeetingsUiModelsWhenRepositoryHasTwoMeetings() throws InterruptedException {
        // GIVEN
        Meeting meeting1 = new Meeting(0, LocalDate.of(2019,12,22), "15h15", 1, "Sujet 1", getParticipants());
        Meeting meeting2 = new Meeting(1, LocalDate.of(2018,12,23), "16h16", 1, "Sujet 2", getParticipants());
        List <Meeting> meetingList = new ArrayList<>();
        meetingList.add(meeting1);
        meetingList.add(meeting2);
        mMeetingListLiveData.setValue(meetingList);
        Mockito.doReturn(mMeetingListLiveData).when(mMeetingManager).getMeetingListLiveData();

        // WHEN
        List<MeetingUiModel> result = LiveDataTestUtil.getOrAwaitValue(mainViewModel.getMeetingUiModelsLiveData());

        // THEN
        assertEquals(2,result.size());
        assertEquals(2,result.size());
    }

    @Test
    public void shouldMeetingAreSortingWithRoomAscendantWhenThisSortIsChoice() throws InterruptedException  {
        // GIVEN
        givenThreeDifferentMeetingForSortingTypeAndFilterTest();
        mainViewModel.setSortingType("Croissant salle", Mockito.mock(SortingTypeUiModel.class));

        // WHEN
        List<MeetingUiModel> result = LiveDataTestUtil.getOrAwaitValue(mainViewModel.getMeetingUiModelsLiveData());

        // THEN
        assertEquals(3,result.size());
        assertEquals(0,result.get(0).getId());
        assertEquals(1,result.get(1).getId());
        assertEquals(2,result.get(2).getId());
    }

    @Test
    public void shouldMeetingAreSortingWithRoomDescendantWhenThisSortIsChoice() throws InterruptedException {
        // GIVEN
        givenThreeDifferentMeetingForSortingTypeAndFilterTest();
        mainViewModel.setSortingType("Decroissant salle", Mockito.mock(SortingTypeUiModel.class));

        // WHEN
        List<MeetingUiModel> result = LiveDataTestUtil.getOrAwaitValue(mainViewModel.getMeetingUiModelsLiveData());

        // THEN
        assertEquals(3,result.size());
        assertEquals(2,result.get(0).getId());
        assertEquals(1,result.get(1).getId());
        assertEquals(0,result.get(2).getId());
    }

    @Test
    public void shouldMeetingAreSortingWithDateAscendantWhenThisSortIsChoice() throws InterruptedException {
        // GIVEN
        givenThreeDifferentMeetingForSortingTypeAndFilterTest();
        mainViewModel.setSortingType("Croissant date", Mockito.mock(SortingTypeUiModel.class));

        // WHEN
        List<MeetingUiModel> result = LiveDataTestUtil.getOrAwaitValue(mainViewModel.getMeetingUiModelsLiveData());

        // THEN
        assertEquals(3,result.size());
        assertEquals("2017-12-22",result.get(0).getDate());
        assertEquals("2018-12-23",result.get(1).getDate());
        assertEquals("2019-12-22",result.get(2).getDate());
    }

    @Test
    public void shouldMeetingAreSortingWithDateDescendantWhenThisSortIsChoice() throws InterruptedException {
        // GIVEN
        givenThreeDifferentMeetingForSortingTypeAndFilterTest();
        mainViewModel.setSortingType("Decroissant date", Mockito.mock(SortingTypeUiModel.class));

        // WHEN
        List<MeetingUiModel> result = LiveDataTestUtil.getOrAwaitValue(mainViewModel.getMeetingUiModelsLiveData());

        // THEN
        assertEquals(3,result.size());
        assertEquals("2019-12-22",result.get(0).getDate());
        assertEquals("2018-12-23",result.get(1).getDate());
        assertEquals("2017-12-22",result.get(2).getDate());
    }

    @Test
    public void shouldMeetingAreFilterWithSelectedRoom1() throws InterruptedException {
        // GIVEN
        givenThreeDifferentMeetingForSortingTypeAndFilterTest();
        mainViewModel.setRoomFilterType("salle 1", Mockito.mock(RoomFilterTypeUiModel.class));

        // WHEN
        List<MeetingUiModel> result = LiveDataTestUtil.getOrAwaitValue(mainViewModel.getMeetingUiModelsLiveData());

        // THEN
        assertEquals(1,result.size());
        assertEquals("1",result.get(0).getRoom());
    }

    @Test
    public void shouldMeetingAreFilterWithSelectedRoom2() throws InterruptedException {
        // GIVEN
        givenThreeDifferentMeetingForSortingTypeAndFilterTest();
        mainViewModel.setRoomFilterType("salle 2", Mockito.mock(RoomFilterTypeUiModel.class));

        // WHEN
        List<MeetingUiModel> result = LiveDataTestUtil.getOrAwaitValue(mainViewModel.getMeetingUiModelsLiveData());

        // THEN
        assertEquals(1,result.size());
        assertEquals("2",result.get(0).getRoom());
    }

    @Test
    public void shouldMeetingAreFilterWithSelectedRoom3() throws InterruptedException {
        // GIVEN
        givenThreeDifferentMeetingForSortingTypeAndFilterTest();
        mainViewModel.setRoomFilterType("salle 3", Mockito.mock(RoomFilterTypeUiModel.class));

        // WHEN
        List<MeetingUiModel> result = LiveDataTestUtil.getOrAwaitValue(mainViewModel.getMeetingUiModelsLiveData());

        // THEN
        assertEquals(1,result.size());
        assertEquals("3",result.get(0).getRoom());
    }

    @Test
    public void shouldMeetingAreFilterWithAllRoom() throws InterruptedException {
        // GIVEN
        givenThreeDifferentMeetingForSortingTypeAndFilterTest();
        mainViewModel.setRoomFilterType("toutes les salles", Mockito.mock(RoomFilterTypeUiModel.class));

        // WHEN
        List<MeetingUiModel> result = LiveDataTestUtil.getOrAwaitValue(mainViewModel.getMeetingUiModelsLiveData());

        // THEN
        assertEquals(3,result.size());
        assertEquals("1",result.get(0).getRoom());
        assertEquals("2",result.get(1).getRoom());
        assertEquals("3",result.get(2).getRoom());

    }

    @Test
    public void shouldMeetingAreFilterWithGoodDate() throws InterruptedException {
        // GIVEN
        givenThreeDifferentMeetingForSortingTypeAndFilterTest();
        mainViewModel.setDateFilterType("2019-12-22");

        // WHEN
        List<MeetingUiModel> result = LiveDataTestUtil.getOrAwaitValue(mainViewModel.getMeetingUiModelsLiveData());

        // THEN
        assertEquals(3,result.size());
        assertEquals("2019-12-22",result.get(0).getDate());
    }

    @Test
    public void shouldMeetingAreCorrectlySortingAscendantWithTwoMeetingWithSameDateAndOneOther() throws InterruptedException {
        // GIVEN
        givenTwoSimilareMeetingDateAndOneOtherForSortingTypeAndFilterTest();
        mainViewModel.setSortingType("Croissant date", Mockito.mock(SortingTypeUiModel.class));

        // WHEN
        List<MeetingUiModel> result = LiveDataTestUtil.getOrAwaitValue(mainViewModel.getMeetingUiModelsLiveData());

        // THEN
        assertEquals(3,result.size());
        assertEquals("2019-12-22",result.get(0).getDate());
        assertEquals("2020-12-23",result.get(1).getDate());
        assertEquals("2020-12-23",result.get(2).getDate());
    }

    @Test
    public void shouldMeetingAreCorrectlySortingDescendantWithTwoMeetingWithSameDateAndOneOther() throws InterruptedException {
        // GIVEN
        givenTwoSimilareMeetingDateAndOneOtherForSortingTypeAndFilterTest();
        mainViewModel.setSortingType("Decroissant date", Mockito.mock(SortingTypeUiModel.class));

        // WHEN
        List<MeetingUiModel> result = LiveDataTestUtil.getOrAwaitValue(mainViewModel.getMeetingUiModelsLiveData());

        // THEN
        assertEquals(3,result.size());
        assertEquals("2020-12-23",result.get(0).getDate());
        assertEquals("2020-12-23",result.get(1).getDate());
        assertEquals("2019-12-22",result.get(2).getDate());
    }

    @Test
    public void shouldMeetingAreCorrectlySortingAscendantWithTwoMeetingWithSameRoomAndOneOther() throws InterruptedException  {
        // GIVEN
        givenTwoSimilareMeetingRoomAndOneOtherForSortingTypeAndFilterTest();
        mainViewModel.setSortingType("Croissant salle", Mockito.mock(SortingTypeUiModel.class));

        // WHEN
        List<MeetingUiModel> result = LiveDataTestUtil.getOrAwaitValue(mainViewModel.getMeetingUiModelsLiveData());

        // THEN
        assertEquals(0,result.get(0).getId());
        assertEquals(1,result.get(1).getId());
        assertEquals(2,result.get(2).getId());
    }

    @Test
    public void shouldMeetingAreCorrectlySortingDescendantWithTwoMeetingWithSameRoomAndOneOther() throws InterruptedException  {
        // GIVEN
        givenTwoSimilareMeetingRoomAndOneOtherForSortingTypeAndFilterTest();
        mainViewModel.setSortingType("Decroissant salle", Mockito.mock(SortingTypeUiModel.class));

        // WHEN
        List<MeetingUiModel> result = LiveDataTestUtil.getOrAwaitValue(mainViewModel.getMeetingUiModelsLiveData());

        // THEN
        assertEquals(3,result.size());
        assertEquals(1,result.get(0).getId());
        assertEquals(2,result.get(1).getId());
        assertEquals(0,result.get(2).getId());
    }

    @Test
    public void shouldMeetingCorrectlyHaveOneIdOneSubjectThreeParticipantsOneRoomOneDateOneHour() throws InterruptedException {
        // GIVEN
        Meeting meeting1 = new Meeting(0, LocalDate.of(2019, 12, 22), "15h15", 1, "Sujet 1", getParticipants());
        Meeting meeting2 = new Meeting(1, LocalDate.of(2018, 12, 23), "16h16", 2, "Sujet 2", getParticipants());
        Meeting meeting3 = new Meeting(2, LocalDate.of(2017, 12, 22), "13h13", 3, "Sujet 3", getParticipants());
        List<Meeting> meetingList = new ArrayList<>();
        meetingList.add(meeting1);
        meetingList.add(meeting2);
        meetingList.add(meeting3);
        mMeetingListLiveData.setValue(meetingList);
        Mockito.doReturn(mMeetingListLiveData).when(mMeetingManager).getMeetingListLiveData();

        // WHEN (UiModel = all data are String)
        List<MeetingUiModel> result = LiveDataTestUtil.getOrAwaitValue(mainViewModel.getMeetingUiModelsLiveData());

        // THEN
        Assert.assertThat(
                result,
                containsInAnyOrder(
                        allOf(
                                hasProperty("id", is(0)),
                                hasProperty("subject", is("Sujet 1")),
                                hasProperty("room", is("1")),
                                hasProperty("hour", is("15h15")),
                                hasProperty("date",is("2019-12-22"))
                        ),
                        allOf(
                                hasProperty("id", is(1)),
                                hasProperty("subject", is("Sujet 2")),
                                hasProperty("room", is("2")),
                                hasProperty("hour", is("16h16")),
                                hasProperty("date",is("2018-12-23"))
                        ),
                        allOf(
                                hasProperty("id", is(2)),
                                hasProperty("subject", is("Sujet 3")),
                                hasProperty("room", is("3")),
                                hasProperty("hour", is("13h13")),
                                hasProperty("date",is("2017-12-22"))
                        )
                )
        );

        // Participants
        assertThat(
                getParticipants(),hasItem("test3@test.fr"));
        assertThat(
                getParticipants(),hasItem("test1@test.fr"));
        assertThat(
                getParticipants(),hasItem("test2@test.fr"));


    }

    static private boolean assertEqualsHomeMade (Meeting meeting1, Meeting meeting2){

        if (meeting1 !=null) {
            if (meeting1.getSubject()!=null) {
                if (meeting1.getListOfEmailOfParticipant()!=null) {
                    if (meeting1.getRoom()!=0) {
                        if (meeting1.getDate()!=null) {
                            if (meeting1.getHour()!=null) {
                                if (meeting2 !=null) {
                                    if (meeting2.getSubject()!=null && meeting2.getSubject().equals(meeting1.getSubject())) {
                                        if (meeting2.getListOfEmailOfParticipant()!=null && meeting2.getListOfEmailOfParticipant().equals(meeting1.getListOfEmailOfParticipant()) ) {
                                            if (meeting2.getRoom()!=0 && meeting2.getRoom()== meeting1.getRoom() ) {
                                                if (meeting2.getDate()!=null && meeting2.getDate().isEqual(meeting1.getDate())) {
                                                    return meeting2.getHour() != null && meeting2.getHour().equals(meeting1.getHour());
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