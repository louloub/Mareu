package com.example.mareu.view.ui.model;

import java.util.List;

public class RoomFilterTypeUiModel {

    private String title;
    private String positiveButtonText;
    private String toastChoiceMeeting;
    private List<String> names;
    private int selectedIndex;

    public RoomFilterTypeUiModel(String title, String positiveButtonText, String toastChoiceMeeting, List<String> names, int selectedIndex) {
        this.title = title;
        this.positiveButtonText = positiveButtonText;
        this.toastChoiceMeeting = toastChoiceMeeting;
        this.names = names;
        this.selectedIndex = selectedIndex;
    }

    public RoomFilterTypeUiModel() {

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPositiveButtonText() {
        return positiveButtonText;
    }

    public void setPositiveButtonText(String positiveButtonText) {
        this.positiveButtonText = positiveButtonText;
    }

    public String getToastChoiceMeeting() {
        return toastChoiceMeeting;
    }

    public void setToastChoiceMeeting(String toastChoiceMeeting) {
        this.toastChoiceMeeting = toastChoiceMeeting;
    }

    public void setNames(List<String> names) {
        this.names = names;
    }

    public int getSelectedIndex() {
        return selectedIndex;
    }

    public void setSelectedIndex(int selectedIndex) {
        this.selectedIndex = selectedIndex;
    }

    public List<String> getListOFilterType() {
        return names;
    }

}
