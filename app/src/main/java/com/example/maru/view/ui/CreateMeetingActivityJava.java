package com.example.maru.view.ui;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
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
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.maru.R;
import com.example.maru.utility.RangeTimePickerDialog;
import com.example.maru.view.ViewModelFactory;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipDrawable;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.textfield.TextInputEditText;
import com.jakewharton.threetenabp.AndroidThreeTen;

import org.threeten.bp.LocalDate;
import org.threeten.bp.LocalDateTime;
import org.threeten.bp.LocalTime;
import org.threeten.bp.ZoneId;
import org.threeten.bp.ZonedDateTime;
import org.threeten.bp.format.DateTimeFormatter;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class CreateMeetingActivityJava extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private static final String TAG = "TAG";
    final ArrayList<String> listOfParticipantChip = new ArrayList<>();
    private int SpannedLength = 0, chipLength = 4, mRoom, yearsSelectedInInt, monthSelectedInInt, daysSelectedInInt;
    private CreateMeetingViewModel mCreateMeetingViewModel;
    private LocalDate mLocalDate;
    private String mHour, participantHint;
    Calendar calendar = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_meeting);
        retrieveXMLandLaunchMethod();
        AndroidThreeTen.init(this);
        final TextInputEditText subjectOfMeeting = findViewById(R.id.create_meeting_tiet_subject);
        final TextInputEditText listOfParticipant = findViewById(R.id.create_meeting_teit_listOfParticipant);
        final TextView hour = findViewById(R.id.create_meeting_et_edit_hour);
        final TextView date = findViewById(R.id.create_meeting_et_edit_date);

        mCreateMeetingViewModel = new ViewModelProvider(this, ViewModelFactory.getInstance()).get(CreateMeetingViewModel.class);

        mCreateMeetingViewModel.getAddMeetingUiModelLiveData().observe(this, new Observer<AddMeetingUiModel>() {
            @Override
            public void onChanged(AddMeetingUiModel addMeetingUiModel) {
                subjectOfMeeting.setHint(addMeetingUiModel.getSubjectHint());
            }
        });

        mCreateMeetingViewModel.getViewActionLiveData().observe(this, new Observer<CreateMeetingViewModel.ViewAction>() {
            @Override
            public void onChanged(CreateMeetingViewModel.ViewAction viewAction) {
                switch (viewAction) {
                    case OK:
                        finish();
                        break;
                }
            }
        });

        mCreateMeetingViewModel.getStringForSubjectHint().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String string) {
                subjectOfMeeting.setHint(string);
            }
        });

        mCreateMeetingViewModel.getStringForParticipantHint().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String string) {
                listOfParticipant.setHint(string);
            }
        });

        mCreateMeetingViewModel.getStringForHourHint().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String string) {
                hour.setHint(string);
            }
        });

        mCreateMeetingViewModel.getStringForDateHint().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String string) {
                date.setHint(string);
            }
        });
    }

    public void retrieveXMLandLaunchMethod() {
        // Text Input XML and Method for subject
        TextInputEditText subjectOfMeeting = findViewById(R.id.create_meeting_tiet_subject);
        // subjectOfMeeting(subjectOfMeeting);

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

        // Hour of meeting
        TextView chooseHour = findViewById(R.id.create_meeting_et_edit_hour);
        launchTimerPickerDialog(chooseHour);

        // Date of meeting
        TextView chooseDate = findViewById(R.id.create_meeting_et_edit_date);
        launchDatePickerDialog(chooseDate);

        // Button for valid meeting & method
        Button validMeeting = findViewById(R.id.create_meeting_bt_valid_meeting);
        onValidMeetingClick(validMeeting, listOfParticipant, subjectOfMeeting, chooseHour, chooseDate);
        // mCreateMeetingViewModel = new ViewModelProvider(this, ViewModelFactory.getInstance()).get(MainViewModel.class);
    }

    public void launchDatePickerDialog(final TextView chooseDate) {
        Button button = findViewById(R.id.create_meeting_bt_date);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                final DatePickerDialog datePickerDialog = new DatePickerDialog(
                        CreateMeetingActivityJava.this,
                    new DatePickerDialog.OnDateSetListener()
                    {
                        @RequiresApi(api = Build.VERSION_CODES.O)
                        @Override
                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth)
                        {
                            yearsSelectedInInt = year;
                            monthSelectedInInt = month;
                            daysSelectedInInt = dayOfMonth;

                            String dayInString = String.format("%02d", dayOfMonth);
                            chooseDate.setText(dayInString + "/" + month + "/" + year);
                            String yearInString = String.valueOf(year);
                            String monthInString = String.valueOf(month);
                            String dateString = dayInString + "-" + monthInString + "-" + yearInString;

                            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                            LocalDate localDate = LocalDate.parse(dateString, dateTimeFormatter);
                            mLocalDate = localDate;
                        }
                    }, LocalDate.now().getYear(), Calendar.getInstance().get(Calendar.MONTH), LocalDate.now().getDayOfMonth());
                datePickerDialog.getDatePicker().setMinDate(calendar.getTimeInMillis());
                datePickerDialog.show();
            }
        });
    }

    public void launchTimerPickerDialog(final TextView chooseHour) {
        Button button = findViewById(R.id.create_meeting_bt_hour);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final RangeTimePickerDialog timePickerDialog = new RangeTimePickerDialog(
                        CreateMeetingActivityJava.this, new TimePickerDialog.OnTimeSetListener()
                {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minutes) {
                        chooseHour.setText(hourOfDay + "h" + String.format("%02d", minutes));
                        String hourInString = String.valueOf(hourOfDay);
                        String minutesInString = String.valueOf(minutes);
                        String hour = hourInString + "h" + minutesInString;
                        mHour = hour;
                    }
                }, LocalTime.now().getHour(), LocalTime.now().getMinute(), true);

                if (yearsSelectedInInt == calendar.get(Calendar.YEAR)
                        && monthSelectedInInt == calendar.get(Calendar.MONTH)
                        && daysSelectedInInt == calendar.get(Calendar.DAY_OF_MONTH))
                {
                    timePickerDialog.setMin(calendar.get(Calendar.HOUR_OF_DAY),calendar.get(Calendar.MINUTE));
                } else {
                }
                timePickerDialog.show();
            }
        });
    }

    public void chipsForParticipant(final TextInputEditText listOfParticipant, final ChipGroup chipGroup) {

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

            @Override
            public void afterTextChanged(Editable editable) {

                // CLOSE ICON WORKS AND "," FOR WRITE NEW CHIP
                if (editable.length() > 1 && (editable.toString().endsWith(",") || editable.toString().endsWith("\n")))
                {
                    final Chip chip = new Chip(CreateMeetingActivityJava.this);
                    chip.setChipDrawable(ChipDrawable.createFromResource(CreateMeetingActivityJava.this, R.xml.chip));
                    final CharSequence charSequenceParticipantMailFromChip = editable.subSequence(SpannedLength, editable.length() - 1);
                    chip.setText(charSequenceParticipantMailFromChip);

                    listOfParticipantChip.add(listOfParticipantChip.size(), charSequenceParticipantMailFromChip.toString());

                    chip.setOnCloseIconClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            chipGroup.removeView(chip);
                            listOfParticipantChip.remove(listOfParticipantChip.indexOf(charSequenceParticipantMailFromChip.toString()));
                        }
                    });
                    chipGroup.addView(chip);
                    editable.clear();

                    if (listOfParticipantChip.size()>0) {
                        participantHint = "Participant(s)";
                        mCreateMeetingViewModel.setHintForParticipants(participantHint);
                    }
                }
            }
        });
    }

    public void roomOfMeeting(final Spinner roomOfMeeting) {

        roomOfMeeting.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                Object item = adapterView.getItemAtPosition(position);
                if (item != null && !MyPreferencesFirstLaunch.isFirst(CreateMeetingActivityJava.this)) {
                    // meeting.setRoom(Integer.parseInt(item.toString()));
                    mRoom = (Integer.parseInt(item.toString()));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

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
    public void onPointerCaptureChanged(boolean hasCapture) {
    }

    public void onValidMeetingClick(
            final Button validMeeting, final TextInputEditText listOfParticipant,
            final TextInputEditText subjectOfMeeting, final TextView chooseHour,
            final TextView chooseDate) {
        validMeeting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCreateMeetingViewModel.createMeeting(mLocalDate, mHour, mRoom, subjectOfMeeting.getText().toString(), listOfParticipantChip);
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