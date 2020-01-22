package com.example.mareu.manager;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.mareu.service.model.Meeting;

import org.threeten.bp.LocalDate;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * MeetingManager is a Singleton
 */
public class MeetingManager {

    /**
     * Single instance not pre-initialized
     */
    private static MeetingManager sInstance = null;
    private final List<Meeting> mMeetingList = new ArrayList<>();
    private final MutableLiveData<List<Meeting>> mMeetingListLiveData = new MutableLiveData<>();

    private int mMeetingCount = 0;

    /**
     * Private constructor
     */
    private MeetingManager() {
    }

    /**
     * Access point for unique instance of singleton
     */
    public static MeetingManager getInstance() {
        if (sInstance == null) {
            synchronized (MeetingManager.class) {
                if (sInstance == null) {
                    sInstance = new MeetingManager();
                }
            }
        }
        return sInstance;
    }

    public void addMeeting(LocalDate date,
                           String hour,
                           int room,
                           String subject,
                           List<String> listOfEmailOfParticipant) {
        mMeetingList.add(new Meeting(mMeetingCount,
                date, hour, room, subject, listOfEmailOfParticipant));
        mMeetingCount++;
        mMeetingListLiveData.setValue(mMeetingList);
    }

    public LiveData<List<Meeting>> getMeetingListLiveData() {
        return mMeetingListLiveData;
    }

    public void deleteMeeting(int meetingId) {

        for (Iterator<Meeting> iterator = mMeetingList.iterator(); iterator.hasNext(); ) {
            Meeting meeting = iterator.next();

            if (meeting.getId() == meetingId) {
                iterator.remove();
                break;
            }
        }

        mMeetingListLiveData.setValue(mMeetingList);
    }
}