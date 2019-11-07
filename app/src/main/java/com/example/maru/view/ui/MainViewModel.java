package com.example.maru.view.ui;

import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.example.maru.service.model.Meeting;
import com.example.maru.service.model.MeetingJava;
import com.example.maru.utility.MeetingManager;
import com.example.maru.view.ui.adapter.MainAdapter;
import com.example.maru.view.ui.model.PropertyUiModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static com.example.maru.view.ui.SortingType.DATE;
import static com.example.maru.view.ui.SortingType.ROOM_ALPHABETICAL;

public class MainViewModel extends ViewModel {

    private MediatorLiveData<List<PropertyUiModel>> mUiModelsLiveData = new MediatorLiveData<>();
    private MutableLiveData<SortingType> mSortingTypeLiveData = new MutableLiveData<>();
    private List<PropertyUiModel> result = new ArrayList<>();
    private boolean ascendingRoom = true;
    private boolean ascendingDate = true;
    // private int checkedItems = 0;

    public MainViewModel() {
        wireUpMediator();
    }

    public MainViewModel(MeetingJava meeting) {
    }

    private void wireUpMediator() {

        final LiveData<List<MeetingJava>> meetingLiveData = MeetingManager.getInstance().getMeetingLiveData();

        mUiModelsLiveData.addSource(meetingLiveData, new Observer<List<MeetingJava>>() {
            @Override
            public void onChanged(List<MeetingJava> meetingJavas) {
                mUiModelsLiveData.setValue(combineMeeting(meetingJavas,mSortingTypeLiveData.getValue()));
            }
        });

        mUiModelsLiveData.addSource(mSortingTypeLiveData, new Observer<SortingType>() {
            @Override
            public void onChanged(SortingType sortingType) {
                mUiModelsLiveData.setValue(combineMeeting(meetingLiveData.getValue(),sortingType));
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

        if (sortingType == null || sortingType == ROOM_ALPHABETICAL) {
            Collections.sort(meetings,ROOM_COMPARATOR);
        } else if (sortingType == DATE ) {
            Collections.sort(meetings,DATECOMPARATOR);
        }
            // List<PropertyUiModel> result = new ArrayList<>();

        for (MeetingJava meetingJava : meetings) {
            String subjectMeeting = null;

            PropertyUiModel propertyUiModel = new PropertyUiModel(
                    meetingJava.getId(),meetingJava.getDate(),meetingJava.getHour(),
                    meetingJava.getRoom(),subjectMeeting, meetingJava.getListOfEmailOfParticipant());

            result.add(propertyUiModel);
        }

        return result;
    }

    // TODO : with NINO
    /*public void setSortingType(String[] sortChoice){
        mSortingTypeLiveData.setValue(DATE);
    }*/

    // TODO : test with Harold
    public void setSortingType(String sortChoice){
        if (sortChoice.equals("Croissant salle")) {
            sortRoom(ascendingRoom);
            /*Toast toastCrescentRoom = Toast.makeText(MainViewModel.this, "Trie croissant salle", Toast.LENGTH_SHORT);
            toastCrescentRoom.show();*/
            ascendingRoom = !ascendingRoom;
            MainActivity.setCheckedItemsInMenu(0);
            // TODO : Create 2 enum ROOM_ALPHABETICAL ?
            mSortingTypeLiveData.setValue(ROOM_ALPHABETICAL);
        } else if (sortChoice.equals("Decroissant salle")) {
            sortRoom(ascendingRoom);
            /*Toast toastDecreaseRoom = Toast.makeText(MainActivity.this, "Trie décroissant salle", Toast.LENGTH_SHORT);
            toastDecreaseRoom.show();*/
            ascendingRoom = !ascendingRoom;
            MainActivity.setCheckedItemsInMenu(1);
            // TODO : Create 2 enum ROOM_ALPHABETICAL ?
            mSortingTypeLiveData.setValue(ROOM_ALPHABETICAL);
        } else if (sortChoice.equals("Croissant date")) {
            sortDate(ascendingDate);
            /*Toast toastCrescentDate = Toast.makeText(MainActivity.this, "Trie croissant date", Toast.LENGTH_SHORT);
            toastCrescentDate.show();*/
            ascendingDate = !ascendingDate;
            MainActivity.setCheckedItemsInMenu(2);
            // TODO : Create 2 enum DATE ?
            mSortingTypeLiveData.setValue(DATE);
        } else if (sortChoice.equals("Decroissant date")) {
            sortDate(ascendingDate);
            /*Toast toastDecreaseDate = Toast.makeText(MainActivity.this, "Trie decroissant date", Toast.LENGTH_SHORT);
            toastDecreaseDate.show();*/
            ascendingDate = !ascendingDate;
            MainActivity.setCheckedItemsInMenu(3);
            // TODO : Create 2 enum DATE ?
            mSortingTypeLiveData.setValue(DATE);
        }
    }

    // Sort room Method
    private void sortRoom(boolean ascendingRoom) {
        //SORT ARRAY ASCENDING AND DESCENDING
        if (ascendingRoom) {
            
            // TODO : with new comparator for PropertyUiModel
            Collections.sort(result, ROOM_COMPARATOR_PUM);
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
        /*MainAdapter adapter = new MainAdapter(this, result);
        recyclerView.setAdapter(adapter);*/
    }

    // Sort date Method
    private void sortDate(boolean ascendingDate) {
        // SORT ARRAY ASCENDING AND DESCENDING
        /*if (ascendingDate) {
            Collections.sort(listOfMeeting, DateComparator);
        } else {
            Collections.reverse(listOfMeeting);
        }
        //ADAPTER
        SimpleAdapter adapter = new SimpleAdapter(this, listOfMeeting);
        recyclerView.setAdapter(adapter);*/
    }

    LiveData<List<PropertyUiModel>> getUiModelsLiveData() {
        return mUiModelsLiveData;
    }
    
    // TODO : new comparator for PropertyUiModel by HAROLD
    private static final Comparator<PropertyUiModel> ROOM_COMPARATOR_PUM = new Comparator<PropertyUiModel>() {
        @Override
        public int compare(PropertyUiModel o1, PropertyUiModel o2) {
            return (o1.getRoom() - o2.getRoom());
        }
    };

    // Comparator to sort meeting list in order of room
    private static final Comparator<MeetingJava> ROOM_COMPARATOR = new Comparator<MeetingJava>() {
        @Override
        public int compare(MeetingJava e1, MeetingJava e2) {
            return (e1.getRoom() - e2.getRoom());
        }
    };

    // Comparator to sort meeting list in order of date
    private static final Comparator<MeetingJava> DATECOMPARATOR = new Comparator<MeetingJava>() {
        @Override
        public int compare(MeetingJava e1, MeetingJava e2) {
            return (e1.getDate().compareTo(e2.getDate()));
        }
    };

    // TODO : impossible to use intent here, but i don't know why
    public void launchCreateMeeting(Intent intent) {
        // startActivity(this,intent);
        /*
        Intent intent = new Intent(context, CreateMeetingActivityJava.class);
        startActivity(intent);*/
    }

    /*void addNewProperty() {
        new InsertDataAsyncTask(mMeeting).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }*/

    private static class InsertDataAsyncTask extends AsyncTask<Void, Void, Void> {

        @NonNull
        private Meeting mMeeting;

        private InsertDataAsyncTask(@NonNull Meeting meeting) {
            mMeeting = meeting;
        }

        @Override
        protected Void doInBackground(Void... voids) {

            /*

            long newAddressId = mAddressDao.insertAddress(new Address(Mock.getAddress()));

            mPropertyDao.insertProperty(new Property(Mock.getType(), newAddressId));

            return null;

            */

            return null;
        }
    }
}