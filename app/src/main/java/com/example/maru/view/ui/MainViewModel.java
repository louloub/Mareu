package com.example.maru.view.ui;

import android.os.AsyncTask;

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

import org.threeten.bp.LocalDate;
import org.threeten.bp.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

import static com.example.maru.view.ui.SortingType.DATE_ASC;
import static com.example.maru.view.ui.SortingType.DATE_DSC;
import static com.example.maru.view.ui.SortingType.ROOM_ALPHABETICAL_ASC;
import static com.example.maru.view.ui.SortingType.ROOM_ALPHABETICAL_DSC;

public class MainViewModel extends ViewModel {

    private MediatorLiveData<List<PropertyUiModel>> mUiModelsLiveData = new MediatorLiveData<>();
    private MutableLiveData<SortingType> mSortingTypeLiveData = new MutableLiveData<>();
    private MutableLiveData<SortingTypeUiModel> mSortingTypeUiModelLiveData = new MutableLiveData<>();
    private int selectedSortingTypeIndex = 0;

    public MainViewModel() {
        wireUpMediator();
    }

    private void wireUpMediator() {

        final LiveData<List<MeetingJava>> meetingLiveData = MeetingManager.getInstance().getMeetingLiveData();

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
            @Nullable SortingType sortingType
    ) {
        if (meetings == null) {
            return null;
        }

        if (sortingType == null || sortingType == ROOM_ALPHABETICAL_ASC) {

            Collections.sort(meetings, ROOM_COMPARATOR_MEETING_JAVA);

        } else if (sortingType == ROOM_ALPHABETICAL_DSC) {

            Collections.sort(meetings, ROOM_COMPARATOR_MEETING_JAVA);
            Collections.reverse(meetings);

        } else if (sortingType == DATE_ASC) {

            Collections.sort(meetings, DATECOMPARATOR);

        } else if (sortingType == DATE_DSC) {

            Collections.sort(meetings, DATECOMPARATOR);
            Collections.reverse(meetings);
        }

        List<PropertyUiModel> result = new ArrayList<>();

        for (MeetingJava meetingJava : meetings) {
            String subjectMeeting = null;

            PropertyUiModel propertyUiModel = new PropertyUiModel(
                    meetingJava.getId(), meetingJava.getDate(), meetingJava.getHour(),
                    meetingJava.getRoom(), subjectMeeting, meetingJava.getListOfEmailOfParticipant());

            result.add(propertyUiModel);
        }

        return result;
    }

    public void setSortingType(String sortChoice) {
        switch (sortChoice) {
            case "Croissant salle":
                mSortingTypeLiveData.setValue(ROOM_ALPHABETICAL_ASC);
                selectedSortingTypeIndex = 0;
                break;
            case "Decroissant salle":
                mSortingTypeLiveData.setValue(ROOM_ALPHABETICAL_DSC);
                selectedSortingTypeIndex = 1;
                break;
            case "Croissant date":
                mSortingTypeLiveData.setValue(DATE_ASC);
                selectedSortingTypeIndex = 2;
                break;
            case "Decroissant date":
                mSortingTypeLiveData.setValue(DATE_DSC);
                selectedSortingTypeIndex = 3;
                break;
        }
    }

    LiveData<List<PropertyUiModel>> getUiModelsLiveData() {
        return mUiModelsLiveData;
    }

    public void displaySortingTypePopup() {
        List<String> list = new ArrayList<>();

        list.add("Croissant salle");
        list.add("Decroissant salle");
        list.add("Croissant date");
        list.add("Decroissant date");

        mSortingTypeUiModelLiveData.setValue(new SortingTypeUiModel(list, selectedSortingTypeIndex));
    }

    public LiveData<SortingTypeUiModel> getmSortingTypeUiModelLiveData() {
        return mSortingTypeUiModelLiveData;
    }

    void addNewRandomMeeting() {
        int hour = new Random().nextInt(24);

        int room = new Random().nextInt(10);

        new InsertDataAsyncTask(
                new MeetingJava(0, LocalDate.now(), "" + hour, room, "S",
                new ArrayList<String>())).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

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

    private static final Comparator<MeetingJava> ROOM_COMPARATOR_MEETING_JAVA = new Comparator<MeetingJava>() {
        @Override
        public int compare(MeetingJava e1, MeetingJava e2) {
            return (e1.getRoom() - e2.getRoom());
        }
    };

    private static final Comparator<MeetingJava> DATECOMPARATOR = new Comparator<MeetingJava>() {
        @Override
        public int compare(MeetingJava e1, MeetingJava e2) {
            return (e1.getDate().compareTo(e2.getDate()));
        }
    };
}

/*// Sort room Method
    private void sortRoom(boolean ascendingRoom) {
        //SORT ARRAY ASCENDING AND DESCENDING
        if (ascendingRoom) {
            // TODO : with new comparator for PropertyUiModel
            Collections.sort(result, ROOM_COMPARATOR_PROPERTY_UI_MODEL);
            // TODO : with getInstance.getMeetingLiveData casted in list
            // Collections.sort((List<MeetingJava>) MeetingManager.getInstance().getMeetingLiveData(), ROOM_COMPARATOR);
            // TODO : with mSortingTypeLiveData
            // Collections.sort(mSortingTypeLiveData, ROOM_COMPARATOR);
            // TODO : with mSortingTypeLiveData casted in List
            // Collections.sort((List<MeetingJava>) mSortingTypeLiveData, ROOM_COMPARATOR);
        } else {
            Collections.reverse(result);
        }
        //ADAPTER
        *//*MainAdapter adapter = new MainAdapter(this, result);
        recyclerView.setAdapter(adapter);*//*
    }

    // Sort date Method
    private void sortDate(boolean ascendingDate) {
        // SORT ARRAY ASCENDING AND DESCENDING
        *//*if (ascendingDate) {
            Collections.sort(listOfMeeting, DateComparator);
        } else {
            Collections.reverse(listOfMeeting);
        }
        //ADAPTER
        SimpleAdapter adapter = new SimpleAdapter(this, listOfMeeting);
        recyclerView.setAdapter(adapter);*//*
    }
*/