package com.example.maru.view.ui;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.HorizontalScrollView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.maru.R;
import com.google.android.material.textfield.TextInputEditText;

import kotlin.text.StringsKt;

public class CreateMeetingActivityJava extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_meeting);
        HorizontalScrollView horizontalScrollView = findViewById(R.id.horizontal_scroll_view);
        TextInputEditText textInputEditText = findViewById(R.id.create_meeting_teit_listOfParticipant);
        horizontalScrollView.setBackground(textInputEditText.getBackground());
        TextInputEditText textInputEditText1 = findViewById(R.id.create_meeting_teit_listOfParticipant);
        textInputEditText1.setBackground(null);
        textInputEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String retrievedText = s.toString();
                CharSequence retrievedTextCharSequence = retrievedText;
                int startIndex = 0;
                int endIndex = retrievedTextCharSequence.length()-1;
                boolean startFound = false;

                while(startIndex <= endIndex){
                    int index = !startFound ? startIndex : endIndex;
                    char it = retrievedTextCharSequence.charAt(index);
                    boolean var = false;
                    boolean match = it <= ' ';
                    if (!startFound) {
                        if (!match) {
                            startFound = true;
                        } else {
                            ++startIndex;
                        }
                    } else {
                        if (!match) {
                            break;
                        }
                        --endIndex;
                    }
                }
                Object object = null;
                String trimmed = retrievedTextCharSequence.subSequence(startIndex,endIndex+1).toString();
                /*if (trimmed.length() > 1 && StringsKt.endsWith(trimmed, ",", false,2,object)){

                }*/
            }
        });
    }
}
