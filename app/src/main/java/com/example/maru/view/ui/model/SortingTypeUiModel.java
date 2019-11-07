package com.example.maru.view.ui.model;

import java.util.List;

public class SortingTypeUiModel {

    private List<String> names;
    private int selectedIndex;

    public SortingTypeUiModel(List<String> names, int selectedIndex) {
        this.names = names;
        this.selectedIndex = selectedIndex;
    }

    public List<String> getNames() {
        return names;
    }

    public int getSelectedIndex() {
        return selectedIndex;
    }
}
