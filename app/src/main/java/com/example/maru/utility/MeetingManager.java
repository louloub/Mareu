package com.example.maru.utility;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.maru.service.model.MeetingJava;

import java.util.ArrayList;
import java.util.List;

/** MeetingManager is a Singleton */
public class MeetingManager {

    private List<MeetingJava> listMeeting = new ArrayList<>();
    private MutableLiveData<List<MeetingJava>> listMeetingLiveData = new MutableLiveData<>();

    /** Constructeur privé */
    private MeetingManager() {  }

    /** Instance unique non préinitialisée */
    private static MeetingManager INSTANCE = null;

    /** Point d'accès pour l'instance unique du singleton */
    public static MeetingManager getInstance() {
        if (INSTANCE == null)
        {
            synchronized(MeetingManager.class)
            {
                if (INSTANCE == null)
                {   INSTANCE = new MeetingManager();
                }
            }
        }
        return INSTANCE;
    }

    public void addMeeting(MeetingJava meeting) {
        listMeeting.add(meeting);
        listMeetingLiveData.setValue(listMeeting);
    }

    public LiveData<List<MeetingJava>> getMeetingLiveData() {
        return listMeetingLiveData;
    }
}