package com.example.maru.view;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.maru.view.ui.CreateMeetingViewModel;
import com.example.maru.view.ui.MainViewModel;

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
            return (T) new MainViewModel();
        } else if (modelClass.isAssignableFrom(CreateMeetingViewModel.class)) {
            return (T) new CreateMeetingViewModel();
        }

        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}