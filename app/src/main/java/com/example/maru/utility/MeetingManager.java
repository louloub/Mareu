package com.example.maru.utility;

import com.example.maru.service.model.MeetingJava;

import java.util.ArrayList;

public class MeetingManager
{
    private static ArrayList<MeetingJava> listMeeting;

    /** Constructeur privé */
    private MeetingManager() { listMeeting = new ArrayList<>(); }

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

    public static ArrayList<MeetingJava> getMeeting() {
        return listMeeting;
    }
}
