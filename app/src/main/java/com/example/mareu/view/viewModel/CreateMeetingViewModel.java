package com.example.mareu.view.viewModel;

import android.widget.AdapterView;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.mareu.manager.MeetingManager;
import com.example.mareu.view.helper.HintType;

import org.threeten.bp.LocalDate;
import org.threeten.bp.format.DateTimeFormatter;

import java.util.List;
import java.util.Locale;

public class CreateMeetingViewModel extends ViewModel {

    private final MutableLiveData<ViewAction> mViewActionLiveData = new MutableLiveData<>();
    private final MutableLiveData<HintType> mHintUiModelLiveData = new MutableLiveData<>();
    private final MutableLiveData<Integer> mMonthSelectedInIntData = new MutableLiveData<>();
    private final MutableLiveData<Integer> mYearsSelectedInIntLiveData = new MutableLiveData<>();
    private final MutableLiveData<Integer> mDaysSelectedInIntLiveData = new MutableLiveData<>();
    private final MutableLiveData<String> mChosenDateStringLiveData = new MutableLiveData<>();
    private final MutableLiveData<LocalDate> mChosenDateLiveData = new MutableLiveData<>();
    private final MutableLiveData<String> mChosenTimeStringLiveData = new MutableLiveData<>();
    private final MutableLiveData<Integer> mRoomSelected = new MutableLiveData<>();
    private final MutableLiveData<String> mChoiceDateFilterUiModelLiveData = new MutableLiveData<>();

    public LiveData<ViewAction> getViewActionLiveData() {
        return mViewActionLiveData;
    }

    public LiveData<HintType> getHintUiModel() {
        return mHintUiModelLiveData;
    }

    public LiveData<Integer> getMonthSelectedInInt() {
        return mMonthSelectedInIntData;
    }

    public LiveData<Integer> getYearsSelectedInInt() {
        return mYearsSelectedInIntLiveData;
    }

    public LiveData<Integer> getDaysSelectedInInt() {
        return mDaysSelectedInIntLiveData;
    }

    public LiveData<String> getChosenDateString() {
        return mChosenDateStringLiveData;
    }

    public LiveData<LocalDate> getChosenDate() {
        return mChosenDateLiveData;
    }

    public LiveData<String> getChosenTime() {
        return mChosenTimeStringLiveData;
    }

    public LiveData<Integer> getRoomSelected() {
        return mRoomSelected;
    }

    public void createMeeting(
            LocalDate date,
            String hour,
            int room,
            String subject,
            List<String> listOfEmailOfParticipant) {
        String hintText;

        HintType hintType;

        if (subject == null || subject.isEmpty()) {
            hintText = "           : Merci d'entrer le sujet de la réunion";
            hintType = new HintType(hintText, "Subject");
            mHintUiModelLiveData.setValue(hintType);
        }

        if (listOfEmailOfParticipant.size() == 0) {
            hintText = "Merci d'entrer le(s) participant(s)";
            hintType = new HintType(hintText, "Participant");
            mHintUiModelLiveData.setValue(hintType);
        } else {
            listOfEmailOfParticipant.size();
            hintText = "";
            hintType = new HintType(hintText, "Participant");
            mHintUiModelLiveData.setValue(hintType);
        }

        if (mChosenDateLiveData.getValue() == null) {
            hintText = "Merci de sélectionner une date";
            hintType = new HintType(hintText, "Date");
            mHintUiModelLiveData.setValue(hintType);
        }

        if (hour == null) {
            hintText = "Merci de sélectionner une heure";
            hintType = new HintType(hintText, "Hour");
            mHintUiModelLiveData.setValue(hintType);
        }

        if ((subject != null && !subject.isEmpty())
                && listOfEmailOfParticipant.size() >= 1 && date != null && hour != null && room > 0) {
            MeetingManager.getInstance().addMeeting(date, hour, room, subject, listOfEmailOfParticipant);
            mViewActionLiveData.setValue(ViewAction.OK);
        } else {
            mViewActionLiveData.setValue(ViewAction.KO);
        }
    }

    public void setHintForParticipants(String mParticipantHint) {
        mHintUiModelLiveData.setValue(new HintType(mParticipantHint, "Participant"));
    }

    public void setRoomSelected(AdapterView<?> adapterView, int position) {
        int item = (int) adapterView.getItemAtPosition(position);
        if (item != 0) {
            mRoomSelected.setValue(item);
        }
    }

    public void setDateSelectedWithPickerDialog(int year, int month, int dayOfMonth) {
        mMonthSelectedInIntData.setValue(month++);
        mYearsSelectedInIntLiveData.setValue(year);
        mDaysSelectedInIntLiveData.setValue(dayOfMonth);

        String dayInStringFormat = String.format(Locale.FRANCE, "%02d", dayOfMonth);
        String monthInStringFormat = String.format(Locale.FRANCE, "%02d", month);

        String chosenDate = dayInStringFormat + "-" + monthInStringFormat + "-" + year;
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy", Locale.FRANCE);

        LocalDate localDateFormatted = LocalDate.parse(chosenDate, dateTimeFormatter);
        mChosenDateLiveData.setValue(localDateFormatted);

        mChosenDateStringLiveData.setValue(chosenDate);
    }

    public void setTimeSelectedWithPickerDialog(int hourOfDay, int minutes) {
        String chosenHour = hourOfDay + "h" + String.format(Locale.FRANCE, "%02d", minutes);
        mChosenTimeStringLiveData.setValue(chosenHour);
    }

    public enum ViewAction {
        OK,
        KO,
    }
}
