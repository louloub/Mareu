package com.example.mareu.view.ui.model;

public class HintUiModel {

    private final String textHint;
    private final String sourceHint;

    public HintUiModel(String textHint, String sourceHint) {
        this.textHint = textHint;
        this.sourceHint = sourceHint;
    }

    public String getTextHint() {
        return textHint;
    }

    public String getSourceHint() {
        return sourceHint;
    }
}
