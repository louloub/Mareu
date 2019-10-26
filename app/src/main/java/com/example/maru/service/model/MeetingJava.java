package com.example.maru.service.model;

import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MeetingJava implements Serializable {

    private String date;
    private String hour;
    private String place;
    private String subject;
    private ArrayList<String> listOfEmailOfParticipant;

    public MeetingJava(String date, String hour, String place, String subject, ArrayList<String> listOfEmailOfParticipant) {
        this.date = date;
        this.hour = hour;
        this.place = place;
        this.subject = subject;
        this.listOfEmailOfParticipant = listOfEmailOfParticipant;
    }

    public MeetingJava(){}

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
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
}