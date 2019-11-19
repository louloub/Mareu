package com.example.maru.view.ui;

import androidx.lifecycle.LiveData;
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
    private MutableLiveData<String> mStringForSubjectHint = new MutableLiveData<>();
    private MutableLiveData<String> mStringForParticipantHint = new MutableLiveData<>();
    private MutableLiveData<String> mStringForDateHint = new MutableLiveData<>();
    private MutableLiveData<String> mStringForHourHint = new MutableLiveData<>();

    public MutableLiveData<AddMeetingUiModel> getAddMeetingUiModelLiveData() {
        return mAddMeetingUiModelLiveData;
    }
    public MutableLiveData<ViewAction> getViewActionLiveData() {
        return mViewActionLiveData;
    }

    LiveData<String> getStringForSubjectHint() {
        return mStringForSubjectHint;
    }
    LiveData<String> getStringForParticipantHint() { return mStringForParticipantHint; }
    LiveData<String> getStringForDateHint() { return mStringForDateHint; }
    LiveData<String> getStringForHourHint() { return mStringForHourHint; }

    public void createMeeting(
            LocalDate date, String hour, int room, String subject, List<String> listOfEmailOfParticipant)
    {
        String subjectHint;
        String participantHint;
        String dateHint;
        String hourHint;
        String roomHint;

        if (subject == null || subject.isEmpty()) {
            subjectHint = "           : Merci d'entrer le sujet de la réunion";
            mStringForSubjectHint.setValue(subjectHint);
        } else {
            subjectHint = null;
        }

        /*if (subjectHint != null) {
            mAddMeetingUiModelLiveData.setValue(new AddMeetingUiModel(subjectHint));
        } else {
            MeetingManager.getInstance().addMeeting(date, hour, room, subject, listOfEmailOfParticipant);
            mViewActionLiveData.setValue(ViewAction.OK);
            meetingLiveData.setValue(MeetingManager.getInstance().getMeeting());
        }*/

        if (listOfEmailOfParticipant.size()==0) {
            participantHint = "Merci d'entrer le(s) participant(s)";
            mStringForParticipantHint.setValue(participantHint);
        } else {
            participantHint = "";
            mStringForParticipantHint.setValue(participantHint);
        }

        /*if (participantHint != null) {
            mAddMeetingUiModelLiveData.setValue(new AddMeetingUiModel(participantHint));
        } else {
            MeetingManager.getInstance().addMeeting(date, hour, room, subject, listOfEmailOfParticipant);
            mViewActionLiveData.setValue(ViewAction.OK);
            meetingLiveData.setValue(MeetingManager.getInstance().getMeeting());
        }*/

        if (date == null) {
            dateHint = "Merci de sélectionner une date";
            mStringForDateHint.setValue(dateHint);
        } else {
            dateHint = null;
        }

        if (hour == null) {
            hourHint = "Merci de sélectionner une heure";
            mStringForHourHint.setValue(hourHint);
        } else {
            hourHint = null;
        }


        // TODO : faire la suite des hint 18/11/19
    }

    public enum ViewAction {
        OK,
        KO,
    }
}
