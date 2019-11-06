package com.example.maru.utility;

import androidx.lifecycle.LiveData;

import com.example.maru.service.model.MeetingJava;

import java.util.ArrayList;
import java.util.List;

public class MeetingManager extends LiveData<MeetingJava> {

    // MeetingManager is a Singleton
    private static final String TAG = "TAG" ;
    private static ArrayList<MeetingJava> listMeeting;
    private static LiveData<List<MeetingJava>> listMeetingLiveData;

    /** Constructeur privé */
    private MeetingManager() { listMeeting = new ArrayList<>(); }
    // private MeetingManager() { listMeetingLiveData = new MutableLiveData<>();


    /** Instance unique non préinitialisée */
    private static MeetingManager INSTANCE = null;

    /** Point d'accès pour l'instance unique du singleton */
    public static MeetingManager getInstance()
    {
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

    public static void addMeeting(MeetingJava meeting) {
        listMeeting.add(meeting);
    }

    public static LiveData<List<MeetingJava>> getMeeting() {return listMeetingLiveData;}


    // TODO : other method don't work because getMeeting is arraylist
    // public static ArrayList<MeetingJava> getMeeting() {return listMeeting;}

    // TODO : this method don't work
    /*public static LiveData<List<MeetingJava>> getMeeting() {

        if (listMeetingLiveData == null) {
            listMeetingLiveData = new MutableLiveData<>();
            getInstance();
        }
        return listMeetingLiveData;
    }*/

    // LiveData<List<MeetingJava>> getMeetingList() {return listMeeting;}

    // TODO : singleton fonctionnel non MVVM en dessous

    /*private static ArrayList<MeetingJava> listMeeting;

    *//** Constructeur privé *//*
    private MeetingManager() { listMeeting = new ArrayList<>(); }

    *//** Instance unique non préinitialisée *//*
    private static MeetingManager INSTANCE = null;

    *//** Point d'accès pour l'instance unique du singleton *//*
    public static MeetingManager getInstance()
    {
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

    public static void addMeeting(MeetingJava meeting) {
        listMeeting.add(meeting);
    }

    // public static ArrayList<MeetingJava> getMeeting() {return listMeeting;}

    public static LiveData<List<MeetingJava>> getMeeting() {return listMeeting;}

    // LiveData<List<MeetingJava>> getMeetingList() {return listMeeting;}*/
}
