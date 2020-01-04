package com.example.maru.view.ui.model;

public class SubjectAndParticipantHintMeetingUiModel {

    private final String subjectHint;
    private final String participantHint;

    public SubjectAndParticipantHintMeetingUiModel(String subjectHint, String participantHint) {
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
