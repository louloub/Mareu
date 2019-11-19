package com.example.maru.view.ui;

class AddMeetingUiModel {

    private final String subjectHint;
    private final String participantHint;

    /*private final String subjectHint;
    private final String subjectHint;
    private final String subjectHint;*/


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

    // TODO : add new hint (18/11/19)
}
