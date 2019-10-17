package com.example.maru.view.ui;

import android.os.Bundle;
import android.text.Editable;
import android.text.Spanned;
import android.text.TextWatcher;
import android.text.style.ImageSpan;
import android.widget.HorizontalScrollView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.maru.R;
import com.google.android.material.chip.ChipDrawable;
import com.google.android.material.textfield.TextInputEditText;

public class CreateMeetingActivityJava extends AppCompatActivity {

    final TextInputEditText textInputEditText = findViewById(R.id.create_meeting_teit_listOfParticipant);
    private int SpannedLength = 0,chipLength = 4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_meeting);
        retrieveXML();
        chipsForParticipant();
    }

    public void retrieveXML(){

        HorizontalScrollView horizontalScrollView = findViewById(R.id.horizontal_scroll_view);

        horizontalScrollView.setBackground(textInputEditText.getBackground());

        TextInputEditText textInputEditText1 = findViewById(R.id.create_meeting_teit_listOfParticipant);
        textInputEditText1.setBackground(null);

        ChipDrawable chipDrawable = ChipDrawable.createFromResource(getApplicationContext(), R.xml.chip);
        chipDrawable.setBounds(0,0,chipDrawable.getIntrinsicHeight(),chipDrawable.getIntrinsicHeight());
    }

    public void chipsForParticipant(){
        textInputEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                if (charSequence.length() == SpannedLength - chipLength)
                {
                    SpannedLength = charSequence.length();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

                if (editable.length() - SpannedLength == chipLength) {
                    ChipDrawable chip = ChipDrawable.createFromResource(getApplicationContext(), R.xml.chip);
                    chip.setChipText(editable.subSequence(SpannedLength,editable.length()));
                    chip.setBounds(0, 0, chip.getIntrinsicWidth(), chip.getIntrinsicHeight());
                    ImageSpan span = new ImageSpan(chip);

                    // Editable editable = textInputEditText.getText();

                    editable.setSpan(span, SpannedLength, editable.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    SpannedLength = editable.length();
                }
            }
        });
    }
}
