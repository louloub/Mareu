package com.example.maru.view.ui;

import android.widget.Button;
import android.widget.TextView;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.maru.service.model.MeetingJava;
import com.example.maru.utility.MeetingManager;
import com.google.android.material.textfield.TextInputEditText;

import org.threeten.bp.LocalDate;

import java.util.ArrayList;
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

    public void createMeeting(LocalDate date, String hour, int room, String subject, List<String> listOfEmailOfParticipant){

        String subjectHint;

        if (subject==null || subject.isEmpty() ) {
            subjectHint = "           : Merci d'entrer le sujet de la réunion";
        } else {
            subjectHint = null;
        }

        if (subjectHint!=null) {
            mAddMeetingUiModelLiveData.setValue(new AddMeetingUiModel(subjectHint));
        } else {
            MeetingManager.getInstance().addMeeting(date,hour,room,subject,listOfEmailOfParticipant);
            mViewActionLiveData.setValue(ViewAction.OK);
            meetingLiveData.setValue(MeetingManager.getInstance().getMeeting());
        }

        // TODO : faire la suite des hint 18/11/19
    }

    /*void onValidMeetingClick(
            Button validMeeting,
            TextInputEditText subjectOfMeeting,
            TextInputEditText listOfParticipant,
            TextView chooseHour,
            TextView chooseDate,
            ArrayList<String> listOfParticipantChip, MeetingJava meeting) {

        checkIfFieldsAreCompleted(
                validMeeting,subjectOfMeeting,listOfParticipant,
                chooseHour,chooseDate, listOfParticipantChip,meeting);
    }

    private void checkIfFieldsAreCompleted(
            Button validMeeting, TextInputEditText subjectOfMeeting, TextInputEditText listOfParticipant,
            TextView chooseHour, TextView chooseDate, ArrayList<String> listOfParticipantChip, MeetingJava meeting) {
        if (listOfParticipantChip.size()==0 && meeting.getSubject()==null) {
            toastForExceptionWhenValidateMeeting(0,subjectOfMeeting,listOfParticipant,chooseHour,chooseDate);
        } else if (meeting.getSubject()==null) {
            toastForExceptionWhenValidateMeeting(1,subjectOfMeeting,listOfParticipant,chooseHour,chooseDate);
        } else if (listOfParticipantChip.size()==0) {
            toastForExceptionWhenValidateMeeting(2,subjectOfMeeting,listOfParticipant,chooseHour,chooseDate);
        } else if (meeting.getHour()==null) {
            toastForExceptionWhenValidateMeeting(3,subjectOfMeeting,listOfParticipant,chooseHour,chooseDate);
        } else if (meeting.getDate()==null) {
            toastForExceptionWhenValidateMeeting(4,subjectOfMeeting,listOfParticipant,chooseHour,chooseDate);
        } else {
            mLaunchIntentFromCreateMeetingToMainActivityLiveData.setValue(true);
        }
    }

    private void toastForExceptionWhenValidateMeeting(
            int caseNumber, TextInputEditText subjectOfMeeting, TextInputEditText listOfParticipant,
            TextView chooseHour, TextView chooseDate) {
        switch(caseNumber){
            case 0:
                subjectOfMeeting.setHint("           : Merci d'entrer le sujet de la réunion");
                listOfParticipant.setHint("Merci d'entrer le(s) participant(s)");
                mStringForToastExeptionOnCreatMeetingLiveData.setValue("Merci d'entrer le sujet ainsi que le(s) participant(s) en les séparant d'une virgule");
                break;
            case 1:
                subjectOfMeeting.setHint("           : Merci d'entrer le sujet de la réunion");
                mStringForToastExeptionOnCreatMeetingLiveData.setValue("Merci d'entrer le sujet de la réunion");
                break;
            case 2:
                listOfParticipant.setHint("Merci d'entrer le(s) participant(s)");
                mStringForToastExeptionOnCreatMeetingLiveData.setValue("Merci d'entrer le(s) participant(s) en les séparant d'une virgule");
                break;
            case 3:
                chooseHour.setHint("Merci de sélectionner une heure");
                mStringForToastExeptionOnCreatMeetingLiveData.setValue("Merci de sélectionner une heure");
                break;
            case 4:
                chooseDate.setHint("Merci de sélectionner une date");
                mStringForToastExeptionOnCreatMeetingLiveData.setValue("Merci de sélectionner une date");
                break;
            default:
                break;
        }
    }*/

    public enum ViewAction {
        OK,
        KO,
    }
}
