package com.example.mareu.view.helper;

public class SortingFilterType {

    private String title;
    private String positiveButtonText;
    private String toastChoiceSorting;
    private String[] names;
    private int selectedIndex;

    public SortingFilterType() {
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

    public String getToastChoiceSorting() {
        return toastChoiceSorting;
    }

    public void setToastChoiceSorting(String toastChoiceSorting) {
        this.toastChoiceSorting = toastChoiceSorting;
    }

    public int getSelectedIndex() {
        return selectedIndex;
    }

    public void setSelectedIndex(int selectedIndex) {
        this.selectedIndex = selectedIndex;
    }

    public String[] getNames() {
        return names;
    }

    public void setNames(String[] names) {
        this.names = names;
    }
}


