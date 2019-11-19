package com.example.maru.view.ui;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.maru.service.model.MeetingJava;
import com.example.maru.utility.MeetingManager;

import org.threeten.bp.LocalDate;

import java.util.List;

public class CreateMeetingViewModel extends ViewModel {

    private MutableLiveData<AddMeetingUiModel> mAddMeetingUiModelLiveData = new MutableLiveData<>();
    private MutableLiveData<ViewAction> mViewActionLiveData = new MutableLiveData<>();
    private MutableLiveData<List<MeetingJava>> meetingLiveData = MeetingManager.getInstance().getMeetingLiveData();

    public MutableLiveData<AddMeetingUiModel> getAddMeetingUiModelLiveData() {
        return mAddMeetingUiModelLiveData;
    }

    public MutableLiveData<ViewAction> getViewActionLiveData() {
        return mViewActionLiveData;
    }

    public void createMeeting(LocalDate date, String hour, int room, String subject, List<String> listOfEmailOfParticipant) {

        String subjectHint;

        if (subject == null || subject.isEmpty()) {
            subjectHint = "           : Merci d'entrer le sujet de la réunion";
        } else {
            subjectHint = null;
        }

        if (subjectHint != null) {
            mAddMeetingUiModelLiveData.setValue(new AddMeetingUiModel(subjectHint));
        } else {
            MeetingManager.getInstance().addMeeting(date, hour, room, subject, listOfEmailOfParticipant);
            mViewActionLiveData.setValue(ViewAction.OK);
            meetingLiveData.setValue(MeetingManager.getInstance().getMeeting());
        }

        // TODO : faire la suite des hint 18/11/19
    }

    public enum ViewAction {
        OK,
        KO,
    }
}
