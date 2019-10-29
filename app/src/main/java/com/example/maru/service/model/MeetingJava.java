package com.example.maru.service.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class MeetingJava implements Serializable, Comparable<MeetingJava> {

    private Date date;
    private String hour;
    private int room;
    private String subject;
    private ArrayList<String> listOfEmailOfParticipant;

    public MeetingJava(Date date, String hour, int room, String subject, ArrayList<String> listOfEmailOfParticipant) {
        this.date = date;
        this.hour = hour;
        this.room = room;
        this.subject = subject;
        this.listOfEmailOfParticipant = listOfEmailOfParticipant;
    }

    public MeetingJava(){}

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public int getRoom() {
        return room;
    }

    public void setRoom(int room) {
        this.room = room;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public ArrayList<String> getListOfEmailOfParticipant() {
        return listOfEmailOfParticipant;
    }

    public void setListOfEmailOfParticipant(ArrayList<String> listOfEmailOfParticipant) {
        this.listOfEmailOfParticipant = listOfEmailOfParticipant;
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

    /*@Override
    public int compareTo(MeetingJava meetingJava) {
        return getDate().compareTo(o.getDate());
    }*/
}