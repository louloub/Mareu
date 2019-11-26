package com.example.maru.view.ui;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.maru.service.model.MeetingJava;
import com.example.maru.utility.MeetingManager;
import com.example.maru.view.ui.model.HintUiModel;

import org.threeten.bp.LocalDate;

import java.util.List;

public class CreateMeetingViewModel extends ViewModel {

    private MutableLiveData<AddMeetingUiModel> mAddMeetingUiModelLiveData = new MutableLiveData<>();
    private MutableLiveData<ViewAction> mViewActionLiveData = new MutableLiveData<>();
    private MutableLiveData<List<MeetingJava>> meetingLiveData = MeetingManager.getInstance().getMeetingLiveData();

    private MutableLiveData<HintUiModel> mStringForHint = new MutableLiveData<>();

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

    LiveData<HintUiModel> getHintUiModel() { return mStringForHint; }

    LiveData<String> getStringForSubjectHint() { return mStringForSubjectHint; }
    LiveData<String> getStringForParticipantHint() {
        return mStringForParticipantHint;
    }
    LiveData<String> getStringForDateHint() {
        return mStringForDateHint;
    }
    LiveData<String> getStringForHourHint() {
        return mStringForHourHint;
    }

    public void createMeeting(
            LocalDate date, String hour, int room, String subject, List<String> listOfEmailOfParticipant) {
        String subjectHint;
        String participantHint;
        String dateHint;
        String hourHint;
        String roomHint;
        String hintText;

        if (subject == null || subject.isEmpty()) {
            hintText = "           : Merci d'entrer le sujet de la réunion" ;
            HintUiModel hintUiModel = new HintUiModel(hintText,"Subject");
            mStringForHint.setValue(hintUiModel);

            // mStringForSubjectHint.setValue("           : Merci d'entrer le sujet de la réunion");
        } else {
            subjectHint = null;
        }

        if (listOfEmailOfParticipant.size() == 0) {
            hintText = "Merci d'entrer le(s) participant(s)";
            HintUiModel hintUiModel = new HintUiModel(hintText,"Participant");
            mStringForHint.setValue(hintUiModel);
            // mStringForParticipantHint.setValue(participantHint);
        } else {
        }

        if (date == null) {
            hintText = "Merci de sélectionner une date";
            HintUiModel hintUiModel = new HintUiModel(hintText,"Date");
            mStringForHint.setValue(hintUiModel);
            // mStringForDateHint.setValue(dateHint);
        } else {
            dateHint = null;
        }

        if (hour == null) {
            hintText = "Merci de sélectionner une heure";
            HintUiModel hintUiModel = new HintUiModel(hintText,"Hour");
            mStringForHint.setValue(hintUiModel);
            // mStringForHourHint.setValue(hourHint);
        } else {
            hourHint = null;
        }

        if ((subject != null && !subject.isEmpty())
                && listOfEmailOfParticipant.size() >= 1 && date != null && hour != null && room > 0)
        {
            MeetingManager.getInstance().addMeeting(date, hour, room, subject, listOfEmailOfParticipant);
            mViewActionLiveData.setValue(ViewAction.OK);
        } else {
            mViewActionLiveData.setValue(ViewAction.KO);
        }
    }

    public void setHintForParticipants(String participantHint) {
        mStringForHint.setValue(new HintUiModel(participantHint,"Participant"));
    }

    public enum ViewAction {
        OK,
        KO,
    }
}
