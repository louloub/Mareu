package com.example.maru.view.ui.model;

import java.util.List;

public class FilterTypeUiModel {

    private List<String> names;
    private int selectedIndex;

    public FilterTypeUiModel(List<String> names, int selectedIndex) {
        this.names = names;
        this.selectedIndex = selectedIndex;
    }

    public FilterTypeUiModel() {
    }

    public List<String> getNames() {
        return names;
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
