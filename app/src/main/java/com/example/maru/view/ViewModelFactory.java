package com.example.maru.view;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.maru.service.model.Meeting;
import com.example.maru.service.model.MeetingJava;
import com.example.maru.view.ui.MainViewModel;

public class ViewModelFactory implements ViewModelProvider.Factory {

    private static ViewModelFactory sFactory;

    /*@NonNull
    private final AddressDao addressDao;
    @NonNull
    private final PropertyDao propertyDao;*/

    @NonNull
    private final MeetingJava meeting;

    private ViewModelFactory(@NonNull MeetingJava meeting) {
        this.meeting = meeting;
    }

    public static ViewModelFactory getInstance() {
        if (sFactory == null) {
            synchronized (ViewModelFactory.class) {
                if (sFactory == null) {
                    sFactory = new ViewModelFactory(
                            new MeetingJava()
                    );
                }
            }
        }

        return sFactory;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        if (modelClass.isAssignableFrom(MainViewModel.class)) {
            return (T) new MainViewModel(meeting);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}