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

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class MainViewModelTest {

    // TODO 04/12/19 : clone le projet de Nino
    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    // TODO 04/12/19 : voir MOCKITO sur OC

    private MeetingManager meetingManager;
    MainViewModel mainViewModel;

    @Before
    public void setUp() throws Exception {
        meetingManager = Mockito.mock(MeetingManager.class);
        mainViewModel = new MainViewModel(meetingManager);
    }

    @Test
    public void should_desplay_two_meeting_when_meeting_manager_has_two () {
        // Given
        List<MeetingJava> meetingJava = new ArrayList<MeetingJava>();
        // LOcalDate.off
        MeetingJava meetingJava1 = new MeetingJava();
        meetingJava.add(meetingJava1);
        MutableLiveData<List<MeetingJava>> mutableLiveData = new MutableLiveData<>();
        mutableLiveData.setValue(meetingJava);
        Mockito.when(meetingManager.getMeetingLiveData()).thenReturn(mutableLiveData);

        // When
        LiveData<List<MeetingUiModel>> result = mainViewModel.getUiModelsLiveData();

        // Then
        assertEquals(1,result.getValue().size());
    }

    @Test
    public void getMeetings() {
        List<MeetingJava> meetings = MeetingManager.getInstance().getMeetingList();

        List<MeetingJava> expectedMeetings = DummyMeetingGenerator.DUMMY_MEETINGS;

        assertThat(meetings, IsIterableContainingInAnyOrder.containsInAnyOrder(expectedMeetings.toArray()));
    }
}