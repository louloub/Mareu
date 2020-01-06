package com.example.mareu.view.ui;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.mareu.utility.MeetingManager;
import com.example.mareu.view.ui.model.HintUiModel;

import org.threeten.bp.LocalDate;

import java.util.List;

public class CreateMeetingViewModel extends ViewModel {

    private final MutableLiveData<ViewAction> mViewActionLiveData = new MutableLiveData<>();
    private final MutableLiveData<HintUiModel> mStringForHint = new MutableLiveData<>();

    MutableLiveData<ViewAction> getViewActionLiveData() {
        return mViewActionLiveData;
    }
    LiveData<HintUiModel> getHintUiModel() { return mStringForHint; }

    public void createMeeting(
            LocalDate date,
            String hour,
            int room,
            String subject,
            List<String> listOfEmailOfParticipant) {
        String hintText;

        if (subject == null || subject.isEmpty()) {
            hintText = "           : Merci d'entrer le sujet de la réunion" ;
            HintUiModel hintUiModel = new HintUiModel(hintText,"Subject");
            mStringForHint.setValue(hintUiModel);
        }

        if (listOfEmailOfParticipant.size() == 0) {
            hintText = "Merci d'entrer le(s) participant(s)";
            HintUiModel hintUiModel = new HintUiModel(hintText,"Participant");
            mStringForHint.setValue(hintUiModel);
        }

        if (date == null) {
            hintText = "Merci de sélectionner une date";
            HintUiModel hintUiModel = new HintUiModel(hintText,"Date");
            mStringForHint.setValue(hintUiModel);
        }

        if (hour == null) {
            hintText = "Merci de sélectionner une heure";
            HintUiModel hintUiModel = new HintUiModel(hintText,"Hour");
            mStringForHint.setValue(hintUiModel);
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
