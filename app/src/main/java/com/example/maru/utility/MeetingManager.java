package com.example.maru.utility;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.maru.service.model.MeetingJava;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import static android.content.ContentValues.TAG;

/**
 * MeetingManager is a Singleton
 */
public class MeetingManager {

    /**
     * Instance unique non préinitialisée
     */
    private static MeetingManager INSTANCE = null;
    private List<MeetingJava> listMeeting = new ArrayList<>();
    private MutableLiveData<List<MeetingJava>> listMeetingLiveData = new MutableLiveData<>();

    private int meetingCount = 0;
    /**
     * Constructeur privé
     */
    private MeetingManager() {}

    /**
     * Point d'accès pour l'instance unique du singleton
     */
    public static MeetingManager getInstance() {
        if (INSTANCE == null) {
            synchronized (MeetingManager.class) {
                if (INSTANCE == null) {
                    INSTANCE = new MeetingManager();
                }
            }
        }
        return INSTANCE;
    }

    // TODO : Le listadapter a besoin d'ID uniques !
    //  cette methjode doit prendre en parametre des primitifs uniquement (date / reasin / participants / etc)
    //  meetingCount peut servir d'ID unique
    public void addMeeting(MeetingJava meeting) {
        listMeeting.add(meeting);
        meeting.setId(meetingCount++);

        // String uuid = UUID.randomUUID().toString();

        listMeetingLiveData.postValue(listMeeting);
    }

    public LiveData<List<MeetingJava>> getMeetingLiveData() {
        return listMeetingLiveData;
    }

    public void deleteMeeting(int meetingId) {

        for (Iterator<MeetingJava> iterator = listMeeting.iterator(); iterator.hasNext(); ) {
            MeetingJava meetingJava = iterator.next();

            if (meetingJava.getId() == meetingId)  {
                iterator.remove();
                break;
            }
        }

        listMeetingLiveData.postValue(listMeeting);
    }

    public List<MeetingJava> getMeeting(){
        return listMeeting;
    }
}