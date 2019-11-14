package com.example.maru.view.ui;

import android.os.AsyncTask;
import android.util.Log;

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

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static android.content.ContentValues.TAG;
import static com.example.maru.view.ui.SortingType.DATE_ASC;
import static com.example.maru.view.ui.SortingType.DATE_DSC;
import static com.example.maru.view.ui.SortingType.ROOM_ALPHABETICAL_ASC;
import static com.example.maru.view.ui.SortingType.ROOM_ALPHABETICAL_DSC;

public class MainViewModel extends ViewModel {

    private MediatorLiveData<List<PropertyUiModel>> mUiModelsLiveData = new MediatorLiveData<>();
    private MutableLiveData<SortingType> mSortingTypeLiveData = new MutableLiveData<>();
    private MutableLiveData<SortingTypeUiModel> mSortingTypeUiModelLiveData = new MutableLiveData<>();
    private int selectedSortingTypeIndex = 0;
    private final LiveData<List<MeetingJava>> meetingLiveData = MeetingManager.getInstance().getMeetingLiveData(); // TODO INJECT THIS INSTEAD

    public MainViewModel() { wireUpMediator(); }

    private void wireUpMediator() {

        mUiModelsLiveData.addSource(meetingLiveData, new Observer<List<MeetingJava>>() {
            @Override
            public void onChanged(List<MeetingJava> meetingJavas) {
                mUiModelsLiveData.setValue(combineMeeting(meetingJavas, mSortingTypeLiveData.getValue()));
            }
        });

        mUiModelsLiveData.addSource(mSortingTypeLiveData, new Observer<SortingType>() {
            @Override
            public void onChanged(SortingType sortingType) {
                mUiModelsLiveData.setValue(combineMeeting(meetingLiveData.getValue(), sortingType));
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
                sortingTypeUiModel.setSelectedIndex(selectedSortingTypeIndex);
                // mSortingTypeUiModelLiveData.setValue(sortingTypeUiModel);
                break;
            case "Decroissant salle":
                mSortingTypeLiveData.setValue(ROOM_ALPHABETICAL_DSC);
                selectedSortingTypeIndex = 1;
                sortingTypeUiModel.setSelectedIndex(selectedSortingTypeIndex);
                // mSortingTypeUiModelLiveData.setValue(sortingTypeUiModel);
                break;
            case "Croissant date":
                mSortingTypeLiveData.setValue(DATE_ASC);
                selectedSortingTypeIndex = 2;
                sortingTypeUiModel.setSelectedIndex(selectedSortingTypeIndex);
                break;
            case "Decroissant date":
                mSortingTypeLiveData.setValue(DATE_DSC);
                selectedSortingTypeIndex = 3;
                sortingTypeUiModel.setSelectedIndex(selectedSortingTypeIndex);
                break;
        }
    }

    /*void setSortingTypeFromInt(int which) {
        switch (which) {
            case 0:
                mSortingTypeLiveData.setValue(ROOM_ALPHABETICAL_ASC);
                break;
            case 1:
                mSortingTypeLiveData.setValue(ROOM_ALPHABETICAL_DSC);
                break;
            case 2:
                mSortingTypeLiveData.setValue(DATE_ASC);
                break;
            case 3:
                mSortingTypeLiveData.setValue(DATE_DSC);
                break;
        }
    }*/

    String getSortingType() {
        String str = "";
        switch (selectedSortingTypeIndex) {
            case 0:
                str = "Croissant salle";
                break;
            case 1:
                str = "Decroissant salle";
                break;
            case 2:
                str = "Croissant date";
                break;
            case 3:
                str = "Decroissant date";
                break;
        }
        return str;
    }

    LiveData<List<PropertyUiModel>> getUiModelsLiveData() {
        return mUiModelsLiveData;
    }

    void displaySortingTypePopup() {
        List<String> list = new ArrayList<>();

        list.add("Croissant salle");
        list.add("Decroissant salle");
        list.add("Croissant date");
        list.add("Decroissant date");

        mSortingTypeUiModelLiveData.setValue(new SortingTypeUiModel(list,selectedSortingTypeIndex));

        // mSortingTypeUiModelLiveData.setValue(new SortingTypeUiModel(list, selectedSortingTypeIndex));
        Log.d(TAG, "mSortingTypeUiModelLiveData = " +mSortingTypeUiModelLiveData );
    }

    LiveData<SortingTypeUiModel> getmSortingTypeUiModelLiveData() { return mSortingTypeUiModelLiveData; }

    void deleteMeeting(int meetingId) { MeetingManager.getInstance().deleteMeeting(meetingId); }

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