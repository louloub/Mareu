package com.example.maru.view.ui.model;

public class HintUiModel {

    private String textHint;
    private String sourceHint;

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
