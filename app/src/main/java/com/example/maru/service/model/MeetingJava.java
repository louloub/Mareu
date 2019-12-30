package com.example.maru.service.model;

import org.threeten.bp.LocalDate;

import java.io.Serializable;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class MeetingJava implements Serializable, Comparable<MeetingJava> {

    static final AtomicInteger NEXT_ID = new AtomicInteger(0);

    private int id;

    {
        NEXT_ID.getAndIncrement();
    }

    private final LocalDate date;
    private final String hour;
    private final int room;
    private final String subject;
    private final List<String> listOfEmailOfParticipant;

    public MeetingJava(int id, LocalDate date, String hour, int room, String subject, List<String> listOfEmailOfParticipant) {
        this.id = id;
        this.date = date;
        this.hour = hour;
        this.room = room;
        this.subject = subject;
        this.listOfEmailOfParticipant = listOfEmailOfParticipant;
    }

    public int getId() {
        return id;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getHour() {
        return hour;
    }

    public int getRoom() {
        return room;
    }

    public String getSubject() {
        return subject;
    }

    public List<String> getListOfEmailOfParticipant() {
        return listOfEmailOfParticipant;
    }

    @Override
    public int compareTo(MeetingJava meetingJava) {
        return (this.room - meetingJava.room);
    }

    @Override
    //this is required to print the user-friendly information about the Employee
    public String toString() {
        return "[sujet=" + this.subject + ", heure=" + this.hour + ", date=" + this.date + ", salle=" +
                this.room + "]";
    }
}