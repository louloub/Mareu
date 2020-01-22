package com.example.mareu.view;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.mareu.MainApplication;
import com.example.mareu.utility.MeetingManager;
import com.example.mareu.view.viewModel.CreateMeetingViewModel;
import com.example.mareu.view.viewModel.MainViewModel;

public class ViewModelFactory implements ViewModelProvider.Factory {

    private static ViewModelFactory sFactory;

    private ViewModelFactory() {
    }

    public static ViewModelFactory getInstance() {
        if (sFactory == null) {
            synchronized (ViewModelFactory.class) {
                if (sFactory == null) {
                    sFactory = new ViewModelFactory(
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
            return (T) new MainViewModel(MeetingManager.getInstance(),MainApplication.getInstance().getResources());

        } else if (modelClass.isAssignableFrom(CreateMeetingViewModel.class)) {
            return (T) new CreateMeetingViewModel();
        }

        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}