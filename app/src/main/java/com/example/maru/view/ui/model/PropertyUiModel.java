package com.example.maru.view.ui.model;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.threeten.bp.LocalDate;

import java.util.ArrayList;
import java.util.Objects;

public class PropertyUiModel {

    private LocalDate date;
    private String hour;
    private int room;
    private String subject;
    private ArrayList<String> listOfEmailOfParticipant;
/*

    private final int id;
    @NonNull
    private final String type;
    @Nullable
    private final String mainAddress;
*/

    public PropertyUiModel(LocalDate date, String hour, int room, String subject, ArrayList<String> listOfEmailOfParticipant) {
        this.date = date;
        this.hour = hour;
        this.room = room;
        this.subject = subject;
        this.listOfEmailOfParticipant = listOfEmailOfParticipant;
    }

    /*public PropertyUiModel(int id, @NonNull String type, @Nullable String mainAddress) {
        this.id = id;
        this.type = type;
        this.mainAddress = mainAddress;
    }*/

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
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

    /*public int getId() {
        return id;
    }

    @NonNull
    public String getType() {
        return type;
    }

    @Nullable
    public String getMainAddress() {
        return mainAddress;
    }*/



    /*@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PropertyUiModel that = (PropertyUiModel) o;
        return id == that.id &&
                type.equals(that.type) &&
                Objects.equals(mainAddress, that.mainAddress);
    }*/
}
