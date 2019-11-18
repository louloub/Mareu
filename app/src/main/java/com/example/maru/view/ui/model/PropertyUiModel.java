package com.example.maru.view.ui.model;

import org.threeten.bp.LocalDate;

import java.util.ArrayList;
import java.util.List;

public class PropertyUiModel {

    private int id;
    private LocalDate date;
    private String hour;
    private int room;
    private String subject;
    private List<String> listOfEmailOfParticipant;

    public PropertyUiModel(int id, LocalDate date, String hour, int room, String subject, List<String> listOfEmailOfParticipant) {
        this.id = id;
        this.date = date;
        this.hour = hour;
        this.room = room;
        this.subject = subject;
        this.listOfEmailOfParticipant = listOfEmailOfParticipant;
    }

    public PropertyUiModel() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

    public String getSubject() { return subject; }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public List<String> getListOfEmailOfParticipant() { return listOfEmailOfParticipant; }

    public void setListOfEmailOfParticipant(ArrayList<String> listOfEmailOfParticipant) {
        this.listOfEmailOfParticipant = listOfEmailOfParticipant;
    }

    @Override
    public String toString() {
        return "PropertyUiModel{" +
                "id=" + id +
                ", date=" + date +
                ", hour='" + hour + '\'' +
                ", room=" + room +
                ", subject='" + subject + '\'' +
                ", listOfEmailOfParticipant=" + listOfEmailOfParticipant +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PropertyUiModel that = (PropertyUiModel) o;
        return id == that.id;

        /*
        return id == that.id &&
                type.equals(that.type) &&
                Objects.equals(mainAddress, that.mainAddress);
        */
    }
}
