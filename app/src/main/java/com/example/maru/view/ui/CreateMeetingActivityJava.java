package com.example.maru.view.ui;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.HorizontalScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.maru.R;
import com.example.maru.service.model.MeetingJava;
import com.example.maru.utility.MeetingManager;
import com.example.maru.view.ViewModelFactory;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipDrawable;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.textfield.TextInputEditText;
import com.jakewharton.threetenabp.AndroidThreeTen;

import org.threeten.bp.LocalDate;
import org.threeten.bp.LocalTime;
import org.threeten.bp.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class CreateMeetingActivityJava extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private static final String TAG = "TAG";
    MeetingJava meeting = new MeetingJava();
    private int SpannedLength = 0, chipLength = 4;
    private MainViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_meeting);
        retrieveXMLandLaunchMethod();
        AndroidThreeTen.init(this);
        launchTimerPickerDialog();
        launchDatePickerDialog();
        // launchRecyclerView();
    }

    public void retrieveXMLandLaunchMethod() {
        // Text Input XML and Method for subject
        TextInputEditText subjectOfMeeting = findViewById(R.id.create_meeting_tiet_subject);
        subjectOfMeeting(subjectOfMeeting);

        // Text Input XML and Method for list of participant
        TextInputEditText listOfParticipant = findViewById(R.id.create_meeting_teit_listOfParticipant);
        listOfParticipant.setBackground(null);

        // Spinner for room
        Spinner roomOfMeeting = findViewById(R.id.create_meeting_spi_room);
        roomOfMeeting(roomOfMeeting);

        // ScrollView for Chip
        HorizontalScrollView horizontalScrollView = findViewById(R.id.horizontal_scroll_view);
        horizontalScrollView.setBackground(listOfParticipant.getBackground());

        // Chip & ChipGroup
        ChipGroup chipGroup = findViewById(R.id.chipGroup);
        chipsForParticipant(listOfParticipant, chipGroup);

        // Button for valid meeting & method
        Button validMeeting = findViewById(R.id.create_meeting_bt_valid_meeting);
        onValidMeetingClick(validMeeting);
        mViewModel = new ViewModelProvider(this, ViewModelFactory.getInstance()).get(MainViewModel.class);
    }

    // Subject of meeting
    private void subjectOfMeeting(TextInputEditText subjectOfMeeting) {

        subjectOfMeeting.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                meeting.setSubject(editable.toString());
            }
        });
    }

    // TimerPickerDialog
    public void launchTimerPickerDialog() {
        final TextView chooseHour = findViewById(R.id.create_meeting_et_edit_hour);
        // TODO : change visibilité of "chooseHour" if is empty
        Button button = findViewById(R.id.create_meeting_bt_hour);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final TimePickerDialog timePickerDialog = new TimePickerDialog(CreateMeetingActivityJava.this, new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minutes) {
                        // TODO : save selected hour for when want to change hour it's showing previous choice
                        chooseHour.setText(hourOfDay + "h" + String.format("%02d", minutes));
                        String hourInString = String.valueOf(hourOfDay);
                        String minutesInString = String.valueOf(minutes);
                        String hour = hourInString + "h" + minutesInString;
                        meeting.setHour(hour);
                    }
                }, LocalTime.now().getHour(), LocalTime.now().getMinute(), true);
                timePickerDialog.show();
            }
        });
    }

    // DatePickerDialo
    public void launchDatePickerDialog() {
        final TextView chooseDate = findViewById(R.id.create_meeting_et_edit_date);
        Button button = findViewById(R.id.create_meeting_bt_date);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final DatePickerDialog datePickerDialog = new DatePickerDialog(CreateMeetingActivityJava.this, new DatePickerDialog.OnDateSetListener() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        // TODO : save selected day month year for when want to change hour it's showing previous choice

                        String dayInString = String.format("%02d", dayOfMonth);
                        chooseDate.setText(dayInString + "/" + month + "/" + year);
                        String yearInString = String.valueOf(year);
                        String monthInString = String.valueOf(month);
                        String dateString = yearInString + "-" + monthInString + "-" + dayInString;

                        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

                        LocalDate localDate = LocalDate.parse(dateString, dateTimeFormatter);

                        Log.d(TAG, "datetest = " + localDate);

                        meeting.setDate(localDate);

                    }
                }, LocalDate.now().getYear(), Calendar.getInstance().get(Calendar.MONTH), LocalDate.now().getDayOfMonth());
                datePickerDialog.show();
            }
        });
    }

    // Chip For Participant
    public void chipsForParticipant(final TextInputEditText listOfParticipant, final ChipGroup chipGroup) {

        final ArrayList<String> listOfParticipantChip = new ArrayList<>();

        listOfParticipant.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                if (charSequence.length() == SpannedLength - chipLength) {
                    SpannedLength = charSequence.length();
                }
            }

            // TODO : delete button delete chip
            @Override
            public void afterTextChanged(Editable editable) {

                // CLOSE ICON WORKS AND "," FOR WRITE NEW CHIP
                if (editable.length() > 1 && (editable.toString().endsWith(",") || editable.toString().endsWith("\n"))) {
                    final Chip chip = new Chip(CreateMeetingActivityJava.this);
                    chip.setChipDrawable(ChipDrawable.createFromResource(CreateMeetingActivityJava.this, R.xml.chip));
                    final CharSequence charSequenceParticipantMailFromChip = editable.subSequence(SpannedLength, editable.length() - 1);
                    chip.setText(charSequenceParticipantMailFromChip);

                    // String str = String.valueOf(editable.subSequence(SpannedLength, editable.length() - 1));

                    listOfParticipantChip.add(listOfParticipantChip.size(), charSequenceParticipantMailFromChip.toString());

                    chip.setOnCloseIconClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            chipGroup.removeView(chip);

                            // TODO : remove participant from listOfParticipantChip when it's delete from ChipGroup

                            /*// int i = indexOf(chip);
                            int i = Arrays.asList(listOfParticipantChip).indexOf(charSequenceParticipantMailFromChip);
                            String str = charSequenceParticipantMailFromChip.toString();
                            // int j = Arrays.asList(listOfParticipantChip).indexOf(charSequenceParticipantMailFromChip);

                            listOfParticipantChip.remove(Arrays.asList(listOfParticipantChip).indexOf(str));*/

                            // listOfParticipantChip.remove(Arrays.asList(listOfParticipantChip).indexOf(charSequenceParticipantMailFromChip));
                            Log.d(TAG, "listOfParticipant in afterTextChanged = " + listOfParticipantChip);
                        }
                    });
                    chipGroup.addView(chip);
                    editable.clear();
                    meeting.setListOfEmailOfParticipant(listOfParticipantChip);
                    Log.d(TAG, "listOfParticipant in afterTextChanged = " + listOfParticipantChip + " & meeting =" + meeting);

                } // END OF IF
            } // END OF afterTextChanged
        });
    }

    // Spinner for room of meeting
    public void roomOfMeeting(final Spinner roomOfMeeting) {

        roomOfMeeting.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                Object item = adapterView.getItemAtPosition(position);
                if (item != null && !MyPreferencesFirstLaunch.isFirst(CreateMeetingActivityJava.this)) {
                    meeting.setRoom(Integer.parseInt(item.toString()));
                    Log.d(TAG, "place = " + item.toString());
                }
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

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        String item = parent.getItemAtPosition(position).toString();

        // Showing selected spinner item
        Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {}

    // Listener on button for validate meeting
    public void onValidMeetingClick(Button validMeeting) {
        validMeeting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MeetingManager.getInstance().addMeeting(meeting);
                Log.d(TAG, " meeting in onValidMeetingClick / onClick = " + meeting);

                Toast toast = Toast.makeText(getApplicationContext(),
                        "Réunion enregistrée",
                        Toast.LENGTH_SHORT);
                toast.show();
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
    }

    // check if activity is launch for the first time for TOAST in "roomOfMeeting" method
    public static class MyPreferencesFirstLaunch {

        private static final String MY_PREFERENCES = "my_preferences";

        public static boolean isFirst(Context context) {
            final SharedPreferences reader = context.getSharedPreferences(MY_PREFERENCES, Context.MODE_PRIVATE);
            final boolean first = reader.getBoolean("is_first", true);
            if (first) {
                final SharedPreferences.Editor editor = reader.edit();
                editor.putBoolean("is_first", false);
                editor.commit();
            }
            return first;
        }
    }
}