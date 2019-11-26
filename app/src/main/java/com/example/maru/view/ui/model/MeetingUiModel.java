package com.example.maru.view.ui.model;

// Destiné à la vue, ce sont des données primitives
// Il ne doit y avoir que des String à afficher
// Donné que la vue doit afficher

public class MeetingUiModel {

    private int id;
    private String date;
    private String hour;
    private String room;
    private String subject;
    private String listOfEmailOfParticipant;

    public MeetingUiModel(
            int id, String date, String hour, String room, String subject, String listOfEmailOfParticipant) {
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

    public String getDate() {
        return date;
    }

    public String getHour() {
        return hour;
    }

    public String getRoom() {
        return room;
    }

    public String getSubject() {
        return subject;
    }

    public String getListOfEmailOfParticipant() {
        return listOfEmailOfParticipant;
    }

    @Override
    public String toString() {
        return "MeetingUiModel{" +
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
        MeetingUiModel that = (MeetingUiModel) o;
        return id == that.id;

        /*
        return id == that.id &&
                type.equals(that.type) &&
                Objects.equals(mainAddress, that.mainAddress);
        */
    }
}
