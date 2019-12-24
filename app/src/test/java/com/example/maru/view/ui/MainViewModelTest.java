package com.example.maru.view.ui;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.MutableLiveData;

import com.example.maru.LiveDataTestUtil;
import com.example.maru.service.model.MeetingJava;
import com.example.maru.utility.MeetingManager;
import com.example.maru.view.ui.model.MeetingUiModel;
import com.example.maru.view.ui.model.RoomFilterTypeUiModel;
import com.example.maru.view.ui.model.SortingTypeUiModel;

import org.hamcrest.Matchers;
import org.hamcrest.collection.IsEmptyCollection;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.Matchers.hasItemInArray;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder;
import static org.hamcrest.collection.IsIterableContainingInOrder.contains;
import static org.hamcrest.MatcherAssert.assertThat;

import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mockito;
import org.threeten.bp.LocalDate;

import java.util.ArrayList;
import java.util.Arrays;
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
    MainViewModel mainViewModel;
    private MutableLiveData<List<MeetingJava>> mMeetingListLiveData;

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
        MeetingJava meetingJava1 = new MeetingJava(0, LocalDate.of(2019, 12, 22), "15h15", 1, "Sujet 1", getParticipants());
        MeetingJava meetingJava2 = new MeetingJava(1, LocalDate.of(2018, 12, 23), "16h16", 2, "Sujet 2", getParticipants());
        MeetingJava meetingJava3 = new MeetingJava(2, LocalDate.of(2017, 12, 22), "13h13", 3, "Sujet 3", getParticipants());
        List<MeetingJava> meetingJavaList = new ArrayList<>();
        meetingJavaList.add(meetingJava1);
        meetingJavaList.add(meetingJava2);
        meetingJavaList.add(meetingJava3);
        mMeetingListLiveData.setValue(meetingJavaList);
        Mockito.doReturn(mMeetingListLiveData).when(mMeetingManager).getMeetingListLiveData();
    }

    // A VERIFIER par NINO
    private void givenTwoSimilareMeetingDateAndOneOtherForSortingTypeAndFilterTest() {
        MeetingJava meetingJava1 = new MeetingJava(0, LocalDate.of(2019, 12, 22), "15h15", 1, "Sujet 1", getParticipants());
        MeetingJava meetingJava2 = new MeetingJava(1, LocalDate.of(2020, 12, 23), "16h16", 2, "Sujet 2", getParticipants());
        MeetingJava meetingJava3 = new MeetingJava(2, LocalDate.of(2020, 12, 23), "16h16", 3, "Sujet 3", getParticipants());
        List<MeetingJava> meetingJavaList = new ArrayList<>();
        meetingJavaList.add(meetingJava1);
        meetingJavaList.add(meetingJava2);
        meetingJavaList.add(meetingJava3);
        mMeetingListLiveData.setValue(meetingJavaList);
        Mockito.doReturn(mMeetingListLiveData).when(mMeetingManager).getMeetingListLiveData();
    }

    // A VERIFIER par NINO
    private void givenTwoSimilareMeetingRoomAndOneOtherForSortingTypeAndFilterTest() {
        MeetingJava meetingJava1 = new MeetingJava(0, LocalDate.of(2019, 12, 22), "15h15", 1, "Sujet 1", getParticipants());
        MeetingJava meetingJava2 = new MeetingJava(1, LocalDate.of(2020, 12, 23), "16h16", 2, "Sujet 2", getParticipants());
        MeetingJava meetingJava3 = new MeetingJava(2, LocalDate.of(2020, 12, 23), "16h16", 2, "Sujet 3", getParticipants());
        List<MeetingJava> meetingJavaList = new ArrayList<>();
        meetingJavaList.add(meetingJava1);
        meetingJavaList.add(meetingJava2);
        meetingJavaList.add(meetingJava3);
        mMeetingListLiveData.setValue(meetingJavaList);
        Mockito.doReturn(mMeetingListLiveData).when(mMeetingManager).getMeetingListLiveData();
    }

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

    @Test
    public void shouldMeetingAreSortingWithRoomAscendantWhenThisSortIsChoice() throws InterruptedException  {
        // GIVEN
        givenThreeDifferentMeetingForSortingTypeAndFilterTest();
        mainViewModel.setSortingType("Croissant salle", Mockito.mock(SortingTypeUiModel.class));

        // WHEN
        List<MeetingUiModel> result = LiveDataTestUtil.getOrAwaitValue(mainViewModel.getUiModelsLiveData());

        // THEN
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
        List<MeetingUiModel> result = LiveDataTestUtil.getOrAwaitValue(mainViewModel.getUiModelsLiveData());

        // THEN
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
        List<MeetingUiModel> result = LiveDataTestUtil.getOrAwaitValue(mainViewModel.getUiModelsLiveData());

        // THEN
        assertEquals("2017-12-22",result.get(0).getDate().toString());
        assertEquals("2018-12-23",result.get(1).getDate().toString());
        assertEquals("2019-12-22",result.get(2).getDate().toString());
    }

    @Test
    public void shouldMeetingAreSortingWithDateDescendantWhenThisSortIsChoice() throws InterruptedException {
        // GIVEN
        givenThreeDifferentMeetingForSortingTypeAndFilterTest();
        mainViewModel.setSortingType("Decroissant date", Mockito.mock(SortingTypeUiModel.class));

        // WHEN
        List<MeetingUiModel> result = LiveDataTestUtil.getOrAwaitValue(mainViewModel.getUiModelsLiveData());

        // THEN
        assertEquals("2019-12-22",result.get(0).getDate().toString());
        assertEquals("2018-12-23",result.get(1).getDate().toString());
        assertEquals("2017-12-22",result.get(2).getDate().toString());
    }

    @Test
    public void shouldMeetingAreFilterWithSelectedRoom1() throws InterruptedException {
        // GIVEN
        givenThreeDifferentMeetingForSortingTypeAndFilterTest();
        mainViewModel.setRoomFilterType("salle 1", Mockito.mock(RoomFilterTypeUiModel.class));

        // WHEN
        List<MeetingUiModel> result = LiveDataTestUtil.getOrAwaitValue(mainViewModel.getUiModelsLiveData());

        // THEN
        assertEquals("1",result.get(0).getRoom());
    }

    @Test
    public void shouldMeetingAreFilterWithSelectedRoom2() throws InterruptedException {
        // GIVEN
        givenThreeDifferentMeetingForSortingTypeAndFilterTest();
        mainViewModel.setRoomFilterType("salle 2", Mockito.mock(RoomFilterTypeUiModel.class));

        // WHEN
        List<MeetingUiModel> result = LiveDataTestUtil.getOrAwaitValue(mainViewModel.getUiModelsLiveData());

        // THEN
        assertEquals("2",result.get(0).getRoom());
    }

    @Test
    public void shouldMeetingAreFilterWithSelectedRoom3() throws InterruptedException {
        // GIVEN
        givenThreeDifferentMeetingForSortingTypeAndFilterTest();
        mainViewModel.setRoomFilterType("salle 3", Mockito.mock(RoomFilterTypeUiModel.class));

        // WHEN
        List<MeetingUiModel> result = LiveDataTestUtil.getOrAwaitValue(mainViewModel.getUiModelsLiveData());

        // THEN
        assertEquals("3",result.get(0).getRoom());
    }

    @Test
    public void shouldMeetingAreFilterWithAllRoom() throws InterruptedException {
        // GIVEN
        givenThreeDifferentMeetingForSortingTypeAndFilterTest();
        mainViewModel.setRoomFilterType("toutes les salles", Mockito.mock(RoomFilterTypeUiModel.class));

        // WHEN
        List<MeetingUiModel> result = LiveDataTestUtil.getOrAwaitValue(mainViewModel.getUiModelsLiveData());

        // THEN
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
        List<MeetingUiModel> result = LiveDataTestUtil.getOrAwaitValue(mainViewModel.getUiModelsLiveData());

        // THEN
        assertEquals("2019-12-22",result.get(0).getDate().toString());
    }

    // A VERIFIER par NINO
    @Test
    public void shouldMeetingAreCorrectlySortingAscendantWithTwoMeetingWithSameDateAndOneOther() throws InterruptedException {
        // GIVEN
        givenTwoSimilareMeetingDateAndOneOtherForSortingTypeAndFilterTest();
        mainViewModel.setSortingType("Croissant date", Mockito.mock(SortingTypeUiModel.class));

        // WHEN
        List<MeetingUiModel> result = LiveDataTestUtil.getOrAwaitValue(mainViewModel.getUiModelsLiveData());

        // THEN
        assertEquals("2019-12-22",result.get(0).getDate().toString());
        assertEquals("2020-12-23",result.get(1).getDate().toString());
        assertEquals("2020-12-23",result.get(2).getDate().toString());
    }

    // A VERIFIER par NINO
    @Test
    public void shouldMeetingAreCorrectlySortingDescendantWithTwoMeetingWithSameDateAndOneOther() throws InterruptedException {
        // GIVEN
        givenTwoSimilareMeetingDateAndOneOtherForSortingTypeAndFilterTest();
        mainViewModel.setSortingType("Decroissant date", Mockito.mock(SortingTypeUiModel.class));

        // WHEN
        List<MeetingUiModel> result = LiveDataTestUtil.getOrAwaitValue(mainViewModel.getUiModelsLiveData());

        // THEN
        assertEquals("2020-12-23",result.get(0).getDate().toString());
        assertEquals("2020-12-23",result.get(1).getDate().toString());
        assertEquals("2019-12-22",result.get(2).getDate().toString());
    }

    // A VERIFIER par NINO
    @Test
    public void shouldMeetingAreCorrectlySortingAscendantWithTwoMeetingWithSameRoomAndOneOther() throws InterruptedException  {
        // GIVEN
        givenTwoSimilareMeetingRoomAndOneOtherForSortingTypeAndFilterTest();
        mainViewModel.setSortingType("Croissant salle", Mockito.mock(SortingTypeUiModel.class));

        // WHEN
        List<MeetingUiModel> result = LiveDataTestUtil.getOrAwaitValue(mainViewModel.getUiModelsLiveData());

        // THEN
        assertEquals(0,result.get(0).getId());
        assertEquals(1,result.get(1).getId());
        assertEquals(2,result.get(2).getId());
    }

    // A VERIFIER par NINO
    @Test
    public void shouldMeetingAreCorrectlySortingDescendantWithTwoMeetingWithSameRoomAndOneOther() throws InterruptedException  {
        // GIVEN
        givenTwoSimilareMeetingRoomAndOneOtherForSortingTypeAndFilterTest();
        mainViewModel.setSortingType("Decroissant salle", Mockito.mock(SortingTypeUiModel.class));

        // WHEN
        List<MeetingUiModel> result = LiveDataTestUtil.getOrAwaitValue(mainViewModel.getUiModelsLiveData());

        // THEN
        assertEquals(1,result.get(0).getId());
        assertEquals(2,result.get(1).getId());
        assertEquals(0,result.get(2).getId());
    }

    // TODO 18/12/19 : assertThat voir projet MVVM NINO (108 à 127) // (158 à 170)

    @Test
    public void shouldMeetingCorrectlyHaveOneSubjectThreeParticipantsOneRoomOneDateOneHour() throws InterruptedException {
        // GIVEN
        MeetingJava meetingJava1 = new MeetingJava(0, LocalDate.of(2019, 12, 22), "15h15", 1, "Sujet 1", getParticipants());
        MeetingJava meetingJava2 = new MeetingJava(1, LocalDate.of(2018, 12, 23), "16h16", 2, "Sujet 2", getParticipants());
        MeetingJava meetingJava3 = new MeetingJava(2, LocalDate.of(2017, 12, 22), "13h13", 3, "Sujet 3", getParticipants());
        List<MeetingJava> meetingJavaList = new ArrayList<>();
        meetingJavaList.add(meetingJava1);
        meetingJavaList.add(meetingJava2);
        meetingJavaList.add(meetingJava3);
        mMeetingListLiveData.setValue(meetingJavaList);
        Mockito.doReturn(mMeetingListLiveData).when(mMeetingManager).getMeetingListLiveData();

        // WHEN
        List<MeetingUiModel> result = LiveDataTestUtil.getOrAwaitValue(mainViewModel.getUiModelsLiveData());

        // THEN

        // TODO 21/12/2019 : why id don't work ?
        assertThat(
                meetingJavaList,
                containsInAnyOrder(
                        hasProperty("id", is(2)),
                        hasProperty("id", is(1)),
                        hasProperty("id", is(0))
                )
        );

        // Subject
        assertThat(
                result,
                containsInAnyOrder(
                        hasProperty("subject", is("Sujet 1")),
                        hasProperty("subject", is("Sujet 2")),
                        hasProperty("subject", is("Sujet 3"))
                        )
        );

        // Participants
        assertThat(getParticipants(),hasItem("test3@test.fr"));
        assertThat(getParticipants(),hasItem("test1@test.fr"));
        assertThat(getParticipants(),hasItem("test2@test.fr"));

        // Room
        // assertThat();

        // Hour
        assertThat(
                result,
                containsInAnyOrder(
                        hasProperty("hour", is("13h13")),
                        hasProperty("hour", is("15h15")),
                        hasProperty("hour", is("16h16"))
                )
        );

        // Date
        assertThat(
                result,
                containsInAnyOrder(
                        hasProperty("date",is("2017-12-22")),
                        hasProperty("date",is("2019-12-22")),
                        hasProperty("date",is("2018-12-23"))
                )
        );
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