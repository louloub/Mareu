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
import java.util.List;

public class MainViewModel extends ViewModel {

    private MediatorLiveData<List<PropertyUiModel>> mUiModelsLiveData = new MediatorLiveData<>();

    @NonNull
    private MeetingJava mMeeting;

    public MainViewModel(@NonNull MeetingJava meeting) {
        mMeeting = meeting;
        wireUpMediator();
    }

    private void wireUpMediator() {
        // MeetingManager.getInstance();


        // TODO : how create meetingLiveDate from Singleton ?
        // final LiveData<List<MeetingJava>> meetingLiveData = new LiveData<List<MeetingJava.getMeeting>>;

        final LiveData<List<MeetingJava>> meetingLiveData = MeetingManager.getMeeting();
        // final LiveData<List<MeetingJava>> meetingLiveData = new LiveData<List<MeetingJava>>;
        // final LiveData<List<Address>> addressesLiveData = mAddressDao.getAddressesLiveData();

        mUiModelsLiveData.addSource(meetingLiveData, new Observer<List<MeetingJava>>() {
            @Override
            public void onChanged(List<MeetingJava> meetingJavas) {
                mUiModelsLiveData.setValue(combineMeeting(meetingLiveData.getValue()));
            }
        });
    }

    @Nullable
    private List<PropertyUiModel> combineMeeting(@Nullable List<MeetingJava> meeting) {
        if (meeting == null) {
            return null;
        }

        List<PropertyUiModel> result = new ArrayList<>();

        for (MeetingJava meetingJava : meeting) {
            String subjectMeeting = null;

            PropertyUiModel propertyUiModel = new PropertyUiModel(meetingJava.getId(),meetingJava.getDate(),meetingJava.getHour(),
                    meetingJava.getRoom(),subjectMeeting, meetingJava.getListOfEmailOfParticipant());

            result.add(propertyUiModel);
        }

        /*for (Property property : properties) {
            String propertyAdress = null;

            for (Address address : addresses) {
                if (address.getId() == property.getId()) {
                    propertyAdress = address.getPath();
                }
            }

            PropertyUiModel propertyUiModel = new PropertyUiModel(property.getId(), property.getType(), propertyAdress);

            result.add(propertyUiModel);
        }*/

        return result;
    }

    LiveData<List<PropertyUiModel>> getUiModelsLiveData() {
        return mUiModelsLiveData;
    }

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