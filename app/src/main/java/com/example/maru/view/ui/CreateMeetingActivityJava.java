package com.example.maru.view.ui;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.Spanned;
import android.text.TextWatcher;
import android.text.style.ImageSpan;
import android.view.KeyEvent;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;

import com.example.maru.R;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipDrawable;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

public class CreateMeetingActivityJava extends AppCompatActivity {

    private int SpannedLength = 0,chipLength = 4;
    private boolean keycodeEnterPressed = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_meeting);
        retrieveXML();
    }

    public void retrieveXML(){
        TextInputEditText textInputEditText = findViewById(R.id.create_meeting_teit_listOfParticipant);
        HorizontalScrollView horizontalScrollView = findViewById(R.id.horizontal_scroll_view);
        horizontalScrollView.setBackground(textInputEditText.getBackground());
        textInputEditText.setBackground(null);
        chipsForParticipant(textInputEditText);
        whenEnterIsCliqued(textInputEditText);
    }

    public void whenEnterIsCliqued(final TextInputEditText textInputEditText){
        textInputEditText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                switch (keyCode) {
                    case KeyEvent.KEYCODE_ENTER:
                        keycodeEnterPressed = true;
                        chipsForParticipant(textInputEditText);
                        return true;
                    default:
                        return false;
                }
            }
        });
    }

    public void chipsForParticipant(final TextInputEditText textInputEditText){
        textInputEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                if(charSequence.length() == SpannedLength - chipLength){
                    SpannedLength = charSequence.length();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

                /*textInputEditText.setOnKeyListener(new View.OnKeyListener() {
                    @Override
                    public boolean onKey(View v, int keyCode, KeyEvent event) {
                        switch (keyCode) {
                            case KeyEvent.KEYCODE_ENTER:
                                keycodeEnterPressed = true;
                                return true;
                            default:
                                return false;
                        }
                    }
                });*/

                /*if (editable.length() > 1 && keycodeEnterPressed){
                    ChipDrawable chip = ChipDrawable.createFromResource(CreateMeetingActivityJava.this, R.xml.chip);
                    chip.setText(editable.subSequence(SpannedLength,editable.length()-1));
                    chip.setBounds(0, 0, chip.getIntrinsicWidth(), chip.getIntrinsicHeight());
                    chip.isCloseIconVisible();
                    ImageSpan span = new ImageSpan(chip);
                    editable.setSpan(span, SpannedLength, editable.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    SpannedLength = editable.length();
                }*/

                if (editable.length() > 1 && editable.toString().endsWith(",")){
                    ChipDrawable chip = ChipDrawable.createFromResource(CreateMeetingActivityJava.this, R.xml.chip);
                    chip.setText(editable.subSequence(SpannedLength,editable.length()-1));
                    chip.setBounds(0, 0, chip.getIntrinsicWidth(), chip.getIntrinsicHeight());
                    chip.isCloseIconVisible();
                    ImageSpan span = new ImageSpan(chip);
                    editable.setSpan(span, SpannedLength, editable.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    SpannedLength = editable.length();
                }

                /*if (event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                    mButton.performClick();
                    return true;
                }

                if (editable.toString().getKeyCode == KeyEvent.KEYCODE_ENTER) {
                    mButton.performClick();
                    return true;
                }*/
            }
        });
    }
}
