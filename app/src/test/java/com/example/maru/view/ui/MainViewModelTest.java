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
    MeetingJava meetingJava;

    @Before
    public void setUp() throws Exception {
        meetingManager = Mockito.mock(MeetingManager.class);
        // MeetingJava meetingJava = Mockito.mock(MeetingJava.class);
        // List<MeetingJava> listOfMeeting = Collections.singletonList(Mockito.mock(MeetingJava.class));

        meetingLiveData = new MutableLiveData<>();
        meetingListLiveData = new MutableLiveData<>();
        mSortingTypeLiveData = new MutableLiveData<>();
        mSelectedFilterTypeLiveData = new MutableLiveData<>();

        Mockito.doReturn(meetingListLiveData).when(meetingManager).getMeetingLiveData();

        /*Mockito.doReturn(meetingListLiveData).when(listOfMeeting).getMeetingListLiveData();
        Mockito.doReturn(meetingLiveData).when(meetingJava).getSubject();

        AddressDao addressDao = Mockito.mock(AddressDao.class);
        PropertyDao propertyDao = Mockito.mock(PropertyDao.class);
        addressesLiveData = new MutableLiveData<>();
        propertiesLiveData = new MutableLiveData<>();
        Mockito.doReturn(addressesLiveData).when(addressDao).getAddressesLiveData();
        Mockito.doReturn(propertiesLiveData).when(propertyDao).getPropertiesLiveData();
        viewModel = new MainViewModel(addressDao, propertyDao);*/

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

        // meetingListLiveData.postValue(listOfMeeting);
        // meetingManager.addMeetingFromObject(meetingJava);

        // TODO : 06/12/19 : mMeetingListLiveData is empty in ManViewModel
        // mainViewModel = new MainViewModel(meetingManager);

        // Then
        assertTrue(meetingManager.getMeetingList().contains(meetingJava));
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