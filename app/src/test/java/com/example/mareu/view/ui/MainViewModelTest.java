package com.example.mareu.view.ui;

import android.content.res.Resources;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.MutableLiveData;

import com.example.mareu.LiveDataTestUtil;
import com.example.mareu.R;
import com.example.mareu.service.model.Meeting;
import com.example.mareu.utility.MeetingManager;
import com.example.mareu.view.ui.model.MeetingUiModel;
import com.example.mareu.view.ui.model.RoomFilterTypeUiModel;
import com.example.mareu.view.ui.model.SortingTypeUiModel;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder;
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
import static org.junit.Assert.*;

/**
 UNIT TEST
 // GIVEN / WHEN / THEN
 **/

public class MainViewModelTest {

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    private MeetingManager mMeetingManager;
    private MainViewModel mainViewModel;
    private MutableLiveData<List<Meeting>> mMeetingListLiveData;
    private Resources mResources;

    @Before
    public void setUp() {
        mMeetingManager = Mockito.mock(MeetingManager.class);
        mResources = Mockito.mock(Resources.class);

        Mockito.doReturn("room_alphabetical_asc_string").when(mResources).getString(R.string.room_alphabetical_asc_string);
        Mockito.doReturn("room_alphabetical_dsc_string").when(mResources).getString(R.string.room_alphabetical_dsc_string);

        Mockito.doReturn("date_asc_string").when(mResources).getString(R.string.date_asc_string);
        Mockito.doReturn("date_dsc_string").when(mResources).getString(R.string.date_dsc_string);

        Mockito.doReturn("all_room_string").when(mResources).getString(R.string.all_room_string);
        Mockito.doReturn("room_1_string").when(mResources).getString(R.string.room_1_string);
        Mockito.doReturn("room_2_string").when(mResources).getString(R.string.room_2_string);
        Mockito.doReturn("room_3_string").when(mResources).getString(R.string.room_3_string);
        Mockito.doReturn("room_4_string").when(mResources).getString(R.string.room_4_string);
        Mockito.doReturn("room_5_string").when(mResources).getString(R.string.room_5_string);
        Mockito.doReturn("room_6_string").when(mResources).getString(R.string.room_6_string);
        Mockito.doReturn("room_7_string").when(mResources).getString(R.string.room_7_string);
        Mockito.doReturn("room_8_string").when(mResources).getString(R.string.room_8_string);
        Mockito.doReturn("room_9_string").when(mResources).getString(R.string.room_9_string);
        Mockito.doReturn("room_10_string").when(mResources).getString(R.string.room_10_string);

        mMeetingListLiveData = new MutableLiveData<>();
        Mockito.doReturn(mMeetingListLiveData).when(mMeetingManager).getMeetingListLiveData();
        mainViewModel = new MainViewModel(mMeetingManager, mResources);
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

    private void givenTwoSimilarMeetingDateAndOneOtherForSortingTypeAndFilterTest() {
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

    private void givenTwoSimilarMeetingRoomAndOneOtherForSortingTypeAndFilterTest() {
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
    }

    @Test
    public void shouldMeetingAreSortingWithRoomAscendantWhenThisSortIsChoice() throws InterruptedException  {
        // GIVEN
        givenThreeDifferentMeetingForSortingTypeAndFilterTest();
        mainViewModel.setSortingType(mResources.getString(R.string.room_alphabetical_asc_string), Mockito.mock(SortingTypeUiModel.class));

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
        mainViewModel.setSortingType(mResources.getString(R.string.room_alphabetical_dsc_string), Mockito.mock(SortingTypeUiModel.class));

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
        mainViewModel.setSortingType(mResources.getString(R.string.date_asc_string), Mockito.mock(SortingTypeUiModel.class));

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
        mainViewModel.setRoomFilterType(mResources.getString(R.string.room_1_string), Mockito.mock(RoomFilterTypeUiModel.class));

        // WHEN
        List<MeetingUiModel> result = LiveDataTestUtil.getOrAwaitValue(mainViewModel.getMeetingUiModelsLiveData());

        // THEN
        assertEquals("1",result.get(0).getRoom());
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
        mainViewModel.compareDateToFilter("2019-12-22");

        // WHEN
        List<MeetingUiModel> result = LiveDataTestUtil.getOrAwaitValue(mainViewModel.getMeetingUiModelsLiveData());

        // THEN
        assertEquals(1,result.size());
        assertEquals("2019-12-22",result.get(0).getDate());
    }

    @Test
    public void shouldMeetingAreCorrectlySortingAscendantWithTwoMeetingWithSameDateAndOneOther() throws InterruptedException {
        // GIVEN
        givenTwoSimilarMeetingDateAndOneOtherForSortingTypeAndFilterTest();
        mainViewModel.setSortingType(mResources.getString(R.string.date_asc_string), Mockito.mock(SortingTypeUiModel.class));

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
        givenTwoSimilarMeetingDateAndOneOtherForSortingTypeAndFilterTest();
        mainViewModel.setSortingType(mResources.getString(R.string.date_dsc_string), Mockito.mock(SortingTypeUiModel.class));

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
        givenTwoSimilarMeetingRoomAndOneOtherForSortingTypeAndFilterTest();
        mainViewModel.setSortingType(mResources.getString(R.string.room_alphabetical_asc_string), Mockito.mock(SortingTypeUiModel.class));

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
        givenTwoSimilarMeetingRoomAndOneOtherForSortingTypeAndFilterTest();
        mainViewModel.setSortingType(mResources.getString(R.string.room_alphabetical_dsc_string), Mockito.mock(SortingTypeUiModel.class));

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
}