package com.example.maru.view.ui;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.example.maru.service.model.MeetingJava;
import com.example.maru.utility.MeetingManager;
import com.example.maru.view.ui.model.PropertyUiModel;
import com.example.maru.view.ui.model.SortingTypeUiModel;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

import static android.content.ContentValues.TAG;
import static com.example.maru.view.ui.SortingType.DATE_ASC;
import static com.example.maru.view.ui.SortingType.DATE_DSC;
import static com.example.maru.view.ui.SortingType.ROOM_ALPHABETICAL_ASC;
import static com.example.maru.view.ui.SortingType.ROOM_ALPHABETICAL_DSC;

public class MainViewModel extends ViewModel {

    private MediatorLiveData<List<PropertyUiModel>> mUiModelsLiveData = new MediatorLiveData<>();
    private MutableLiveData<SortingType> mSortingTypeLiveData = new MutableLiveData<>();
    private MutableLiveData<SortingTypeUiModel> mSortingTypeUiModelLiveData = new MutableLiveData<>();
    private MutableLiveData<String> mStringForToastExeptionOnCreatMeetingLiveData = new MutableLiveData<>();
    private MutableLiveData<Boolean> mLaunchIntentFromCreateMeetingToMainActivityLiveData = new MutableLiveData<>();
    private MutableLiveData<Integer> mSelectedSortingTypeIndexLiveData = new MutableLiveData<>();
    private int selectedSortingTypeIndex = 0;
    private final LiveData<List<MeetingJava>> meetingLiveData = MeetingManager.getInstance().getMeetingLiveData(); // TODO INJECT THIS INSTEAD
    List<String> list = new ArrayList<>();

    public MainViewModel() { wireUpMediator(); }

    private void wireUpMediator() {

        mUiModelsLiveData.addSource(meetingLiveData, new Observer<List<MeetingJava>>() {
            @Override
            public void onChanged(List<MeetingJava> meetingJavas) {
                setSortingType("Decroissant salle", new SortingTypeUiModel(list,integerToIntIndex(mSelectedSortingTypeIndexLiveData)));
                mUiModelsLiveData.setValue(combineMeeting(meetingJavas, mSortingTypeLiveData.getValue()));
            }
        });

        mUiModelsLiveData.addSource(mSortingTypeLiveData, new Observer<SortingType>() {
            @Override
            public void onChanged(SortingType sortingType) {
                mUiModelsLiveData.setValue(combineMeeting(meetingLiveData.getValue(), sortingType));
            }
        });

        mUiModelsLiveData.addSource(mSelectedSortingTypeIndexLiveData, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                selectedSortingTypeIndex = integer;
            }
        });
    }

    @Nullable
    private List<PropertyUiModel> combineMeeting(
            @Nullable List<MeetingJava> meetings,
            @Nullable SortingType sortingType) {
        if (meetings == null) {
            return null;
        }

        if (sortingType == null || sortingType == ROOM_ALPHABETICAL_ASC) {

            Collections.sort(meetings, ROOM_COMPARATOR_MEETING_JAVA_ASC);

        } else if (sortingType == ROOM_ALPHABETICAL_DSC) {

            Collections.sort(meetings, ROOM_COMPARATOR_MEETING_JAVA_DSC);

        } else if (sortingType == DATE_ASC) {

            Collections.sort(meetings, DATE_COMPARATOR_ASC);

        } else if (sortingType == DATE_DSC) {

            Collections.sort(meetings, DATE_COMPARATOR_DSC);
        }

        List<PropertyUiModel> result = new ArrayList<>();

        for (MeetingJava meetingJava : meetings) {

            PropertyUiModel propertyUiModel = new PropertyUiModel(
                    meetingJava.getId(), meetingJava.getDate(), meetingJava.getHour(),
                    meetingJava.getRoom(), meetingJava.getSubject(), meetingJava.getListOfEmailOfParticipant());

            result.add(propertyUiModel);
        }

        return result;
    }

    void setSortingType(String sortChoice, SortingTypeUiModel sortingTypeUiModel) {
        switch (sortChoice) {
            case "Croissant salle":
                mSortingTypeLiveData.setValue(ROOM_ALPHABETICAL_ASC);
                selectedSortingTypeIndex = 0;
                mSelectedSortingTypeIndexLiveData.setValue(selectedSortingTypeIndex);
                sortingTypeUiModel.setSelectedIndex(selectedSortingTypeIndex);
                // setSortingType(ROOM_ALPHABETICAL_ASC);
                // mSortingTypeUiModelLiveData.setValue(sortingTypeUiModel);
                break;
            case "Decroissant salle":
                mSortingTypeLiveData.setValue(ROOM_ALPHABETICAL_DSC);
                selectedSortingTypeIndex = 1;
                sortingTypeUiModel.setSelectedIndex(selectedSortingTypeIndex);
                mSelectedSortingTypeIndexLiveData.setValue(selectedSortingTypeIndex);
                // mSortingTypeUiModelLiveData.setValue(sortingTypeUiModel);
                break;
            case "Croissant date":
                mSortingTypeLiveData.setValue(DATE_ASC);
                selectedSortingTypeIndex = 2;
                sortingTypeUiModel.setSelectedIndex(selectedSortingTypeIndex);
                mSelectedSortingTypeIndexLiveData.setValue(selectedSortingTypeIndex);
                break;
            case "Decroissant date":
                mSortingTypeLiveData.setValue(DATE_DSC);
                selectedSortingTypeIndex = 3;
                sortingTypeUiModel.setSelectedIndex(selectedSortingTypeIndex);
                mSelectedSortingTypeIndexLiveData.setValue(selectedSortingTypeIndex);
                break;
        }
    }

    LiveData<List<PropertyUiModel>> getUiModelsLiveData() { return mUiModelsLiveData; }

    LiveData<String> getStringForToastExeptionOnCreatMeeting() {return mStringForToastExeptionOnCreatMeetingLiveData;}

    LiveData<SortingTypeUiModel> getmSortingTypeUiModelLiveData() { return mSortingTypeUiModelLiveData; }

    // LiveData<String> getSortingChoiceString(){return }

    LiveData<Boolean> getmLaunchIntentFromCreateMeetingToMainActivityLiveData() { return mLaunchIntentFromCreateMeetingToMainActivityLiveData;}

    LiveData<Integer> getSelectedSortingTypeIndexLiveDate() { return mSelectedSortingTypeIndexLiveData;}

    void displaySortingTypePopup() {

        // List<String> list = new ArrayList<>();

        list.add("Croissant salle");
        list.add("Decroissant salle");
        list.add("Croissant date");
        list.add("Decroissant date");

        mSortingTypeUiModelLiveData.setValue(new SortingTypeUiModel(list,integerToIntIndex(mSelectedSortingTypeIndexLiveData)));
        // mSortingTypeUiModelLiveData.setValue(new SortingTypeUiModel(list, selectedSortingTypeIndex));
    }

    private int integerToIntIndex(MutableLiveData<Integer> mSelectedSortingTypeIndexLiveData){
        int indexInt = 0;

        if (mSelectedSortingTypeIndexLiveData.getValue()!=null) {

            switch (Objects.requireNonNull(mSelectedSortingTypeIndexLiveData.getValue())) {
                case 0:
                    indexInt = 0;
                    break;
                case 1:
                    indexInt = 1;
                    break;
                case 2:
                    indexInt = 2;
                    break;
                case 3:
                    indexInt = 3;
                    break;
            }
        }
        return indexInt;
    }

    private void sortingTypeIndexToSortingType(int index){
        switch (index) {
            case 0 :
                mSortingTypeLiveData.setValue(ROOM_ALPHABETICAL_ASC);
                break;
            case 1 :
                mSortingTypeLiveData.setValue(ROOM_ALPHABETICAL_DSC);
                break;
            case 2 :
                mSortingTypeLiveData.setValue(DATE_ASC);
                break;
            case 3 :
                mSortingTypeLiveData.setValue(DATE_DSC);
                break;
        }
    }

    private void sortingTypeToIndex(){

        if (mSortingTypeLiveData.getValue()!=null) {
            switch (Objects.requireNonNull(mSortingTypeLiveData.getValue())) {
                case ROOM_ALPHABETICAL_ASC :
                    selectedSortingTypeIndex = 0;
                    break;
                case ROOM_ALPHABETICAL_DSC :
                    selectedSortingTypeIndex = 1;
                    break;
                case DATE_ASC :
                    selectedSortingTypeIndex = 2;
                    break;
                case DATE_DSC :
                    selectedSortingTypeIndex = 3;
                    break;
            }
        }
    }

    void deleteMeeting(int meetingId) { MeetingManager.getInstance().deleteMeeting(meetingId); }

    void onValidMeetingClick(
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
    }

    // TODO A bouger dans le CreateMeetingActivity
    private static class InsertDataAsyncTask extends AsyncTask<Void, Void, Void> {

        @NonNull
        private MeetingJava mMeeting;

        private InsertDataAsyncTask(@NonNull MeetingJava meeting) {
            mMeeting = meeting;
        }

        @Override
        protected Void doInBackground(Void... voids) {

            MeetingManager.getInstance().addMeeting(mMeeting);

            return null;
        }
    }
    // TODO end todo

    private static final Comparator<MeetingJava> ROOM_COMPARATOR_MEETING_JAVA_ASC = new Comparator<MeetingJava>() {
        @Override
        public int compare(MeetingJava e1, MeetingJava e2) {
            return (e1.getRoom() - e2.getRoom());
        }
    };

    private static final Comparator<MeetingJava> ROOM_COMPARATOR_MEETING_JAVA_DSC = new Comparator<MeetingJava>() {
        @Override
        public int compare(MeetingJava e1, MeetingJava e2) {
            return (e2.getRoom() - e1.getRoom());
        }
    };

    private static final Comparator<MeetingJava> DATE_COMPARATOR_ASC = new Comparator<MeetingJava>() {
        @Override
        public int compare(MeetingJava e1, MeetingJava e2) {
            return (e1.getDate().compareTo(e2.getDate()));
        }
    };

    private static final Comparator<MeetingJava> DATE_COMPARATOR_DSC = new Comparator<MeetingJava>() {
        @Override
        public int compare(MeetingJava e1, MeetingJava e2) {
            return (e2.getDate().compareTo(e1.getDate()));
        }
    };
}