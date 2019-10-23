package com.example.maru.view.ui;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.maru.R;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipDrawable;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.textfield.TextInputEditText;
import com.jakewharton.threetenabp.AndroidThreeTen;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class CreateMeetingActivityJava extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private int SpannedLength = 0,chipLength = 4;
    private int keycodeEnterPressed = 1;
    private int keycodeDelPressed = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_meeting);
        retrieveXML();
        AndroidThreeTen.init(this);
        launchTimerPickerDialog();
        launchDatePickerDialog();
    }

    public void retrieveXML(){
        TextInputEditText listOfParticipant = findViewById(R.id.create_meeting_teit_listOfParticipant);
        Spinner roomOfMeeting = findViewById(R.id.create_meeting_spi_room);
        HorizontalScrollView horizontalScrollView = findViewById(R.id.horizontal_scroll_view);
        ChipGroup chipGroup = findViewById(R.id.chipGroup);
        horizontalScrollView.setBackground(listOfParticipant.getBackground());
        listOfParticipant.setBackground(null);
        chipsForParticipant(listOfParticipant,chipGroup);
        // whenKeyIsCliqued(listOfParticipant);
        roomOfMeeting(roomOfMeeting);
    }

    /*public void whenKeyIsCliqued(final TextInputEditText listOfParticipant){
        listOfParticipant.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                switch (keyCode) {
                    case KeyEvent.KEYCODE_ENTER:
                        keycodeEnterPressed = 1;
                        // chipsForParticipant(listOfParticipant);
                        return true;
                    case KeyEvent.KEYCODE_DEL:
                        keycodeEnterPressed = 2;
                        // chipsForParticipant(listOfParticipant);
                        return true;
                    default:
                        return false;
                }
            }
        });
    }
*/

    public void launchTimerPickerDialog(){
        final TextView chooseHour = findViewById(R.id.create_meeting_et_edit_hour);
        // TODO : change visibilité of "chooseHour" if is empty
        Button button = findViewById(R.id.create_meeting_bt_hour);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final TimePickerDialog timePickerDialog = new TimePickerDialog(CreateMeetingActivityJava.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minutes) {
                        // TODO : change showing format :
                        chooseHour.setText(hourOfDay + "h" + minutes);
                    }
                }, Calendar.getInstance().get(Calendar.HOUR_OF_DAY), Calendar.getInstance().get(Calendar.MINUTE), true);
                timePickerDialog.show();
            }
        });
    }

    public void launchDatePickerDialog(){
        final TextView chooseDate = findViewById(R.id.create_meeting_et_edit_date);
        Button button = findViewById(R.id.create_meeting_bt_date);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final DatePickerDialog datePickerDialog = new DatePickerDialog(CreateMeetingActivityJava.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        chooseDate.setText(dayOfMonth + "/" + month + "/" + year );
                    }
                }, Calendar.getInstance().get(Calendar.YEAR),Calendar.getInstance().get(Calendar.MONTH),Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();
            }
        });
    }

    public void chipsForParticipant(final TextInputEditText listOfParticipant, final ChipGroup chipGroup){
        listOfParticipant.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                if(charSequence.length() == SpannedLength - chipLength){
                    SpannedLength = charSequence.length();
                }
            }

            // TODO : delete button delete chip
            @Override
            public void afterTextChanged(Editable editable) {

                /*listOfParticipant.setOnKeyListener(new View.OnKeyListener() {
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
                /*if (editable.length() > 1 && editable.toString().endsWith(",")) {
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
                }*/

                // CLOSE ICON WORKS AND "," FOR WRITE NEW CHIP
                if (editable.length() > 1 && editable.toString().endsWith(",")) {
                    final Chip chip = new Chip(CreateMeetingActivityJava.this);
                    chip.setChipDrawable(ChipDrawable.createFromResource(CreateMeetingActivityJava.this, R.xml.chip));
                    // int paddingDp = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, getResources().getDisplayMetrics());
                    // chip.setPadding(paddingDp, paddingDp, paddingDp, paddingDp);
                    chip.setText(editable.subSequence(SpannedLength,editable.length()-1));
                    chip.setOnCloseIconClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            chipGroup.removeView(chip);
                        }
                    });
                    chipGroup.addView(chip);
                    editable.clear();
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

    public void roomOfMeeting(Spinner roomOfMeeting){

        roomOfMeeting.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                Object item = adapterView.getItemAtPosition(position);
                if (item != null && !MyPreferencesFirstLaunch.isFirst(CreateMeetingActivityJava.this)) {
                    /*Toast.makeText(CreateMeetingActivityJava.this, item.toString(),
                            Toast.LENGTH_SHORT).show();*/
                    /*Toast.makeText(CreateMeetingActivityJava.this, "Salle numéro " +item.toString()+ " sélectionnée" ,
                            Toast.LENGTH_SHORT).show();*/
                }
                /*Toast.makeText(CreateMeetingActivityJava.this, "Salle numéro " +item.toString()+ " sélectionnée" ,
                        Toast.LENGTH_SHORT).show();*/
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // TODO Auto-generated method stub

            }
        });

        // Spinner Drop down elements
        List<Integer> categories = new ArrayList<Integer>();
        categories.add(1);
        categories.add(2);
        categories.add(3);
        categories.add(4);
        categories.add(5);
        categories.add(6);
        categories.add(7);
        categories.add(8);
        categories.add(9);
        categories.add(10);

        // Creating adapter for spinner
        ArrayAdapter<Integer> dataAdapter = new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_item, categories);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        roomOfMeeting.setAdapter(dataAdapter);
    }

    // check if activity is launch for the first time for TOAST in "roomOfMeeting" method
    public static class MyPreferencesFirstLaunch {

        private static final String MY_PREFERENCES = "my_preferences";

        public static boolean isFirst(Context context){
            final SharedPreferences reader = context.getSharedPreferences(MY_PREFERENCES, Context.MODE_PRIVATE);
            final boolean first = reader.getBoolean("is_first", true);
            if(first){
                final SharedPreferences.Editor editor = reader.edit();
                editor.putBoolean("is_first", false);
                editor.commit();
            }
            return first;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        String item = parent.getItemAtPosition(position).toString();

        // Showing selected spinner item
        Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
    }
}