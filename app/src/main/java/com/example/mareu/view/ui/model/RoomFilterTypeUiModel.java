package com.example.mareu.view.ui.model;

import java.util.List;

public class RoomFilterTypeUiModel {

    private List<String> names;
    private int selectedIndex;

    public RoomFilterTypeUiModel(List<String> names, int selectedIndex) {
        this.names = names;
        this.selectedIndex = selectedIndex;
    }

    public RoomFilterTypeUiModel() {
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
