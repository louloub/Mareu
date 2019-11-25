package com.example.maru.utility;

import androidx.lifecycle.MutableLiveData;

import com.example.maru.service.model.MeetingJava;

import org.threeten.bp.LocalDate;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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
    private MeetingManager() {
    }

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

    public void addMeeting(LocalDate date, String hour, int room, String subject, List<String> listOfEmailOfParticipant)
    {
        listMeeting.add(new MeetingJava(meetingCount, date, hour, room, subject, listOfEmailOfParticipant));
        meetingCount++;
        listMeetingLiveData.postValue(listMeeting);
    }

    public MutableLiveData<List<MeetingJava>> getMeetingLiveData() {
        return listMeetingLiveData;
    }

    public void deleteMeeting(int meetingId) {

        for (Iterator<MeetingJava> iterator = listMeeting.iterator(); iterator.hasNext(); ) {
            MeetingJava meetingJava = iterator.next();

            if (meetingJava.getId() == meetingId) {
                iterator.remove();
                break;
            }
        }

        listMeetingLiveData.postValue(listMeeting);
    }

    public List<MeetingJava> getMeeting() {
        return listMeeting;
    }
}