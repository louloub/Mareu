package com.example.maru.view.ui;

import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;

import com.example.maru.service.model.Meeting;
import com.example.maru.view.ui.model.PropertyUiModel;

import java.util.List;

public class MainViewModel extends ViewModel {

    private MediatorLiveData<List<PropertyUiModel>> mUiModelsLiveData = new MediatorLiveData<>();

    @NonNull
    private Meeting mMeeting;

    public MainViewModel(@NonNull Meeting meeting) {
        mMeeting = meeting;
        // wireUpMediator();
    }

    /*private void wireUpMediator() {
        final LiveData<List<Meeting>> meetingLiveData = new LiveData<List<Meeting>>;
        final LiveData<List<Address>> addressesLiveData = mAddressDao.getAddressesLiveData();

        mUiModelsLiveData.addSource(propertiesLiveData, new Observer<List<Property>>() {
            @Override
            public void onChanged(List<Property> properties) {
                mUiModelsLiveData.setValue(combineMeeting(properties, addressesLiveData.getValue()));
            }
        });

        mUiModelsLiveData.addSource(addressesLiveData, new Observer<List<Address>>() {
            @Override
            public void onChanged(List<Address> addresses) {
                mUiModelsLiveData.setValue(combineMeeting(propertiesLiveData.getValue(), addresses));
            }
        });
    }*/

    /*@Nullable
    private List<PropertyUiModel> combineMeeting(@Nullable List<Meeting> meeting) {
        if (mMeeting == null) {
            return null;
        }

        List<PropertyUiModel> result = new ArrayList<>();

        for (Property property : properties) {
            String propertyAdress = null;

            for (Address address : addresses) {
                if (address.getId() == property.getId()) {
                    propertyAdress = address.getPath();
                }
            }

            PropertyUiModel propertyUiModel = new PropertyUiModel(property.getId(), property.getType(), propertyAdress);

            result.add(propertyUiModel);
        }

        return result;
    }*/

    LiveData<List<PropertyUiModel>> getUiModelsLiveData() {
        return mUiModelsLiveData;
    }

    void addNewProperty() {
        new InsertDataAsyncTask(mMeeting).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

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