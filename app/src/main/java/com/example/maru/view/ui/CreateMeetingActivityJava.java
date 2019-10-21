package com.example.maru.view.ui;

import android.os.Bundle;
import android.text.Editable;
import android.text.Spannable;
import android.text.Spanned;
import android.text.TextWatcher;
import android.text.style.ImageSpan;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.View;
import android.widget.HorizontalScrollView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.maru.R;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipDrawable;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.textfield.TextInputEditText;

public class CreateMeetingActivityJava extends AppCompatActivity {

    private int SpannedLength = 0,chipLength = 4;
    private int keycodeEnterPressed = 1;
    private int keycodeDelPressed = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_meeting);
        retrieveXML();
    }

    public void retrieveXML(){
        TextInputEditText textInputEditText = findViewById(R.id.create_meeting_teit_listOfParticipant);
        HorizontalScrollView horizontalScrollView = findViewById(R.id.horizontal_scroll_view);
        ChipGroup chipGroup = findViewById(R.id.chipGroup);
        horizontalScrollView.setBackground(textInputEditText.getBackground());
        textInputEditText.setBackground(null);
        chipsForParticipant(textInputEditText,chipGroup);
        // whenKeyIsCliqued(textInputEditText);
    }

    /*public void whenKeyIsCliqued(final TextInputEditText textInputEditText){
        textInputEditText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                switch (keyCode) {
                    case KeyEvent.KEYCODE_ENTER:
                        keycodeEnterPressed = 1;
                        // chipsForParticipant(textInputEditText);
                        return true;
                    case KeyEvent.KEYCODE_DEL:
                        keycodeEnterPressed = 2;
                        // chipsForParticipant(textInputEditText);
                        return true;
                    default:
                        return false;
                }
            }
        });
    }
*/
    public void chipsForParticipant(final TextInputEditText textInputEditText, final ChipGroup chipGroup){
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

                /*if (editable.length() > 1 && editable.toString().endsWith(",")){
                    final ChipDrawable chip = ChipDrawable.createFromResource(CreateMeetingActivityJava.this, R.xml.chip);
                    chip.setText(editable.subSequence(SpannedLength,editable.length()-1));
                    chip.setBounds(1, 1, chip.getIntrinsicWidth(), chip.getIntrinsicHeight());
                    chip.isCloseIconVisible();
                    ImageSpan span = new ImageSpan(chip);
                    editable.setSpan(span, SpannedLength, editable.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    SpannedLength = editable.length();

                    chip.setCloseIcon(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            chipGroup.removeView();
                        }
                    });
                }*/

                /*if (editable.length() > 1 && editable.toString().endsWith(",")){
                    final Chip chip = new Chip(CreateMeetingActivityJava.this);
                    chip.setChipDrawable(ChipDrawable.createFromResource(CreateMeetingActivityJava.this, R.xml.chip));
                    chip.setText(editable.subSequence(SpannedLength,editable.length()-1));
                    // chip.setBounds(1, 1, chip.getIntrinsicWidth(), chip.getIntrinsicHeight());
                    chip.isCloseIconVisible();
                    ImageSpan span = new ImageSpan(chip);
                    editable.setSpan(span, SpannedLength, editable.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    SpannedLength = editable.length();
                }*/

                // CLOSE ICON WORKS BUT BUG WITH WRITE TEXT
                if (editable.length() > 1 && editable.toString().endsWith(",")) {
                    final Chip chip = new Chip(CreateMeetingActivityJava.this);
                    chip.setChipDrawable(ChipDrawable.createFromResource(CreateMeetingActivityJava.this, R.xml.chip));
                    int paddingDp = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, getResources().getDisplayMetrics());
                    chip.setPadding(paddingDp, paddingDp, paddingDp, paddingDp);
                    chip.setText(editable.subSequence(SpannedLength,editable.length()-1));
                    chip.setOnCloseIconClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            chipGroup.removeView(chip);
                        }
                    });
                    chipGroup.addView(chip);
                    // return chip;
                }

                // WORKS WITH "," for write new chip
                /*if (editable.length() > 1 && editable.toString().endsWith(",")){
                    ChipDrawable chip = ChipDrawable.createFromResource(CreateMeetingActivityJava.this, R.xml.chip);
                    chip.setText(editable.subSequence(SpannedLength,editable.length()-1));
                    chip.setBounds(1, 1, chip.getIntrinsicWidth(), chip.getIntrinsicHeight());
                    chip.isCloseIconVisible();
                    ImageSpan span = new ImageSpan(chip);
                    editable.setSpan(span, SpannedLength, editable.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    SpannedLength = editable.length();
                }*/

                /*if (keycodeEnterPressed==2){
                    Chip chip = (Chip) chipGroup.getChildAt(chipGroup.getChildCount()-1);
                    chipGroup.removeView(chip);
                }*/

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
