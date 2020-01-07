package com.example.mareu.view.ui;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.mareu.utility.MeetingManager;
import com.example.mareu.view.ui.model.HintUiModel;

import org.threeten.bp.LocalDate;
import org.threeten.bp.format.DateTimeFormatter;

import java.util.List;
import java.util.Locale;

public class CreateMeetingViewModel extends ViewModel {

    private final MutableLiveData<ViewAction> mViewActionLiveData = new MutableLiveData<>();
    private final MutableLiveData<HintUiModel> mStringForHint = new MutableLiveData<>();
    private final MutableLiveData<Integer> mMonthSelectedInInt = new MutableLiveData<>();
    private final MutableLiveData<Integer> mYearsSelectedInInt = new MutableLiveData<>();
    private final MutableLiveData<Integer> mDaysSelectedInInt = new MutableLiveData<>();
    private final MutableLiveData<String> mChosenDateString = new MutableLiveData<>();
    private final MutableLiveData<LocalDate> mLocalDate = new MutableLiveData<>();


    MutableLiveData<ViewAction> getViewActionLiveData() {
        return mViewActionLiveData;
    }
    LiveData<HintUiModel> getHintUiModel() { return mStringForHint; }
    MutableLiveData<Integer> getMonthSelectedInInt(){return mMonthSelectedInInt;}
    MutableLiveData<Integer> getYearsSelectedInInt(){return mYearsSelectedInInt;}
    MutableLiveData<Integer> getDaysSelectedInInt(){return mDaysSelectedInInt;}
    MutableLiveData<String> getChosenDateString(){return mChosenDateString;}
    MutableLiveData<LocalDate> getLocalDate(){return mLocalDate;}

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

    public void setDateSelectedWithPickerDialog(int year, int month, int dayOfMonth){
        mMonthSelectedInInt.setValue(month++);
        mYearsSelectedInInt.setValue(year);
        mDaysSelectedInInt.setValue(dayOfMonth);

        String dayInStringFormat = String.format(Locale.FRANCE,"%02d", dayOfMonth);
        String monthInStringFormat = String.format(Locale.FRANCE,"%02d", month);

        String chosenDate = dayInStringFormat + "-" + monthInStringFormat + "-" + year;
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy", Locale.FRANCE);

        LocalDate localDateFormatted = LocalDate.parse(chosenDate, dateTimeFormatter);
        mLocalDate.setValue(localDateFormatted);

        mChosenDateString.setValue(chosenDate);
    }

    public enum ViewAction {
        OK,
        KO,
    }
}
