package com.example.maru.view.ui;

import android.content.Intent;
import android.os.AsyncTask;

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

    public MainViewModel() {
        wireUpMediator();
    }

    private void wireUpMediator() {
        // MeetingManager.getInstance();

        final LiveData<List<MeetingJava>> meetingLiveData = MeetingManager.getInstance().getMeetingLiveData();

        mUiModelsLiveData.addSource(mSortingTypeLiveData, new Observer<SortingType>() {
            @Override
            public void onChanged(SortingType sortingType) {
                mUiModelsLiveData.setValue(combineMeeting(meetingLiveData.getValue(),sortingType));
            }
        });

        mUiModelsLiveData.addSource(meetingLiveData, new Observer<List<MeetingJava>>() {
            @Override
            public void onChanged(List<MeetingJava> meetingJavas) {
                mUiModelsLiveData.setValue(combineMeeting(meetingJavas,mSortingTypeLiveData.getValue()));
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

            List<PropertyUiModel> result = new ArrayList<>();

        for (MeetingJava meetingJava : meetings) {
            String subjectMeeting = null;

            PropertyUiModel propertyUiModel = new PropertyUiModel(meetingJava.getId(),meetingJava.getDate(),meetingJava.getHour(),
                    meetingJava.getRoom(),subjectMeeting, meetingJava.getListOfEmailOfParticipant());

            result.add(propertyUiModel);
        }

        return result;
    }

    public void setSortingType(){
        mSortingTypeLiveData.setValue(DATE);
    }

    LiveData<List<PropertyUiModel>> getUiModelsLiveData() {
        return mUiModelsLiveData;
    }

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