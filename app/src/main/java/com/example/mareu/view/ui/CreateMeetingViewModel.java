package com.example.mareu.view.ui;

import android.text.Editable;
import android.widget.AdapterView;

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
    private final MutableLiveData<HintUiModel> mHintUiModelLiveData = new MutableLiveData<>();
    private final MutableLiveData<Integer> mMonthSelectedInIntData = new MutableLiveData<>();
    private final MutableLiveData<Integer> mYearsSelectedInIntLiveData = new MutableLiveData<>();
    private final MutableLiveData<Integer> mDaysSelectedInIntLiveData = new MutableLiveData<>();
    private final MutableLiveData<String> mChosenDateStringLiveData = new MutableLiveData<>();
    private final MutableLiveData<LocalDate> mChosenDateLiveData = new MutableLiveData<>();
    private final MutableLiveData<String> mChosenTimeStringLiveData = new MutableLiveData<>();
    private final MutableLiveData<CharSequence> mParticipantChip = new MutableLiveData<>();
    private final MutableLiveData<Integer> mRoomSelected = new MutableLiveData<>();

    MutableLiveData<ViewAction> getViewActionLiveData() {
        return mViewActionLiveData;
    }
    LiveData<HintUiModel> getHintUiModel() { return mHintUiModelLiveData; }
    MutableLiveData<Integer> getMonthSelectedInInt(){return mMonthSelectedInIntData;}
    MutableLiveData<Integer> getYearsSelectedInInt(){return mYearsSelectedInIntLiveData;}
    MutableLiveData<Integer> getDaysSelectedInInt(){return mDaysSelectedInIntLiveData;}
    MutableLiveData<String> getChosenDateString(){return mChosenDateStringLiveData;}
    MutableLiveData<LocalDate> getChosenDate(){return mChosenDateLiveData;}
    MutableLiveData<String> getChosenTime(){return mChosenTimeStringLiveData;}
    MutableLiveData<Integer> getRoomSelected(){return mRoomSelected;}

    void createMeeting(
            LocalDate date,
            String hour,
            int room,
            String subject,
            List<String> listOfEmailOfParticipant) {
        String hintText;

        if (subject == null || subject.isEmpty()) {
            hintText = "           : Merci d'entrer le sujet de la réunion" ;
            HintUiModel hintUiModel = new HintUiModel(hintText,"Subject");
            mHintUiModelLiveData.setValue(hintUiModel);
        }

        if (listOfEmailOfParticipant.size() == 0) {
            hintText = "Merci d'entrer le(s) participant(s)";
            HintUiModel hintUiModel = new HintUiModel(hintText,"Participant");
            mHintUiModelLiveData.setValue(hintUiModel);
        } else {
            listOfEmailOfParticipant.size();
            hintText = "";
            HintUiModel hintUiModel = new HintUiModel(hintText,"Participant");
            mHintUiModelLiveData.setValue(hintUiModel);
        }

        if (mChosenDateLiveData.getValue() == null) {
            hintText = "Merci de sélectionner une date";
            HintUiModel hintUiModel = new HintUiModel(hintText,"Date");
            mHintUiModelLiveData.setValue(hintUiModel);
        }

        if (hour == null) {
            hintText = "Merci de sélectionner une heure";
            HintUiModel hintUiModel = new HintUiModel(hintText,"Hour");
            mHintUiModelLiveData.setValue(hintUiModel);
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

    void setHintForParticipants(String mParticipantHint) {
        mHintUiModelLiveData.setValue(new HintUiModel(mParticipantHint,"Participant"));
    }

    void setRoomSelected(AdapterView<?> adapterView, int position) {
        int item = (int) adapterView.getItemAtPosition(position);
        if (item != 0) {
            mRoomSelected.setValue(item);
        }
    }

    void setDateSelectedWithPickerDialog(int year, int month, int dayOfMonth){
        mMonthSelectedInIntData.setValue(month++);
        mYearsSelectedInIntLiveData.setValue(year);
        mDaysSelectedInIntLiveData.setValue(dayOfMonth);

        String dayInStringFormat = String.format(Locale.FRANCE,"%02d", dayOfMonth);
        String monthInStringFormat = String.format(Locale.FRANCE,"%02d", month);

        String chosenDate = dayInStringFormat + "-" + monthInStringFormat + "-" + year;
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy", Locale.FRANCE);

        LocalDate localDateFormatted = LocalDate.parse(chosenDate, dateTimeFormatter);
        mChosenDateLiveData.setValue(localDateFormatted);

        mChosenDateStringLiveData.setValue(chosenDate);
    }

    void setTimeSelectedWithPickerDialog(int hourOfDay, int minutes){
        String chosenHour = hourOfDay + "h" + String.format(Locale.FRANCE,"%02d", minutes);
        mChosenTimeStringLiveData.setValue(chosenHour);
    }

    public enum ViewAction {
        OK,
        KO,
    }
}
