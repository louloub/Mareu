package com.example.mareu.view.ui.model;

public class DateFilterUiModel {

    private String title;
    private String message;
    private String positiveButtonText;
    private String toastForDisplayAllMeeting;
    private String toastForInvalideDate;
    private String toastForValideDate;

    public DateFilterUiModel(String title, String message, String positiveButtonText, String toastForDisplayAllMeeting, String toastForInvalideDate, String toastForValideDate) {
        this.title = title;
        this.message = message;
        this.positiveButtonText = positiveButtonText;
        this.toastForDisplayAllMeeting = toastForDisplayAllMeeting;
        this.toastForInvalideDate = toastForInvalideDate;
        this.toastForValideDate = toastForValideDate;
    }

    public DateFilterUiModel() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPositiveButtonText() {
        return positiveButtonText;
    }

    public void setPositiveButtonText(String positiveButtonText) {
        this.positiveButtonText = positiveButtonText;
    }

    public String getToastForDisplayAllMeeting() {
        return toastForDisplayAllMeeting;
    }

    public void setToastForDisplayAllMeeting(String toastForDisplayAllMeeting) {
        this.toastForDisplayAllMeeting = toastForDisplayAllMeeting;
    }

    public String getToastForInvalideDate() {
        return toastForInvalideDate;
    }

    public void setToastForInvalideDate(String toastForInvalideDate) {
        this.toastForInvalideDate = toastForInvalideDate;
    }

    public String getToastForValideDate() {
        return toastForValideDate;
    }

    public void setToastForValideDate(String toastForValideDate) {
        this.toastForValideDate = toastForValideDate;
    }

    /*
    mChoiceDateFilterUiModel.setToastTextForDisplayAllMeeting("Tu as choisi d'afficher toutes les réunions ");
        mChoiceDateFilterUiModel.setToastTextForInvalideDate("La date est invalide");
        mChoiceDateFilterUiModel.setToastTextForValideDate("Tu as choisi d'afficher les réunions de cette date : ");
        */
}