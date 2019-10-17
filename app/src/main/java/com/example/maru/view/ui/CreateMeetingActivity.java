package com.example.maru.view.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.text.Editable;
import android.text.Spanned;
import android.text.TextWatcher;
import android.text.style.ImageSpan;

import com.example.maru.R;
import com.google.android.material.chip.ChipDrawable;
import com.google.android.material.textfield.TextInputEditText;

public class CreateMeetingActivity extends AppCompatActivity {

    // int SpannedLength = 0,chipLength = 4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_meeting);
        Toolbar toolbar = findViewById(R.id.toolbar_tb_toolbar);
        setSupportActionBar(toolbar);
        // chip();
    }

    /*public void chip () {

        // int SpannedLength = 0,chipLength = 4;
        TextInputEditText Participant = findViewById(R.id.create_meeting_teit_listOfParticipant);

        // AppCompatEditText Participant = findViewById(R.id.create_meeting_teit_listOfParticipant);


        Participant.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                *//*if (charSequence.length() == SpannedLength - chipLength)
                {
                    SpannedLength = charSequence.length();
                }*//*
            }

            @Override
            public void afterTextChanged(Editable editable) {

                *//*if(editable.length() - SpannedLength == chipLength) {
                    ChipDrawable chip = ChipDrawable.createFromResource(getApplicationContext(), R.xml.chip );

                    // chip.setChip(editable.subSequence(SpannedLength,editable.length()));

                    // chip.setChipText(editable.subSequence(SpannedLength,editable.length()));
                    chip.setBounds(0, 0, chip.getIntrinsicWidth(), chip.getIntrinsicHeight());
                    ImageSpan span = new ImageSpan(chip);
                    editable.setSpan(span, SpannedLength, editable.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    SpannedLength = editable.length();
                }*//*
            }
        });
    }*/
}
