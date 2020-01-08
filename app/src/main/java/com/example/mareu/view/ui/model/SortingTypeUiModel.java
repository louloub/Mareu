package com.example.mareu.view.ui.model;

import java.util.List;

public class SortingTypeUiModel {

    private String title;
    private String positiveButtonText;
    private String toastChoiceSorting;
    private List<String> names;
    private int selectedIndex;

    public SortingTypeUiModel(String title, String positiveButtonText, String toastChoiceSorting, List<String> names, int selectedIndex) {
        this.title = title;
        this.positiveButtonText = positiveButtonText;
        this.toastChoiceSorting = toastChoiceSorting;
        this.names = names;
        this.selectedIndex = selectedIndex;
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

    public void setToastChoiceSorting(String toastChoiceSorting) {
        this.toastChoiceSorting = toastChoiceSorting;
    }

    public String getToastChoiceSorting() {
        return toastChoiceSorting;
    }

    public SortingTypeUiModel() {
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

    public List<String> getListOfSortingType() {
        return names;
    }
}


