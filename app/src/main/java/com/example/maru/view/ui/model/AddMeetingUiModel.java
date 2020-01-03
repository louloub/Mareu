package com.example.maru.view.ui.model;

public class AddMeetingUiModel {

    private final String subjectHint;
    private final String participantHint;

    public AddMeetingUiModel(String subjectHint, String participantHint) {
        this.subjectHint = subjectHint;
        this.participantHint = participantHint;
    }

    public String getSubjectHint() {
        return subjectHint;
    }

    public String getParticipantHint() {
        return participantHint;
    }
}
