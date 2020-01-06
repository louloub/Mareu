package com.example.maru.view.ui;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.maru.R;
import com.example.maru.utility.RangeTimePickerDialog;
import com.example.maru.view.ViewModelFactory;
import com.example.maru.view.ui.model.HintUiModel;
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

public class CreateMeetingActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

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

        mCreateMeetingViewModel.getHintUiModel().observe(this, new Observer<HintUiModel>() {
            @Override
            public void onChanged(HintUiModel hintUiModel) {
                setHint(hintUiModel, subjectOfMeeting, listOfParticipant, date, hour);
            }
        });
    }

    private void setHint(HintUiModel hintUiModel,
                         TextInputEditText subjectOfMeeting,
                         TextInputEditText listOfParticipant,
                         TextView date,
                         TextView hour) {
        String hintSource = hintUiModel.getSourceHint();
        String hintText = hintUiModel.getTextHint();

        switch (hintSource) {
            case "Subject" :
                subjectOfMeeting.setHint(hintText);
            case "Participant" :
                listOfParticipant.setHint(hintText);
            case "Date" :
                date.setHint(hintText);
            case "Hour" :
                hour.setHint(hintText);
        }
    }

    public void retrieveXMLandLaunchMethod() {
        TextInputEditText subjectOfMeeting = findViewById(R.id.create_meeting_tiet_subject);
        TextInputEditText listOfParticipant = findViewById(R.id.create_meeting_teit_listOfParticipant);
        listOfParticipant.setBackground(null);
        Spinner roomOfMeeting = findViewById(R.id.create_meeting_spi_room);
        roomOfMeeting(roomOfMeeting);
        HorizontalScrollView horizontalScrollView = findViewById(R.id.horizontal_scroll_view);
        horizontalScrollView.setBackground(listOfParticipant.getBackground());
        ChipGroup chipGroup = findViewById(R.id.chipGroup);
        chipsForParticipant(listOfParticipant, chipGroup);
        TextView chooseHour = findViewById(R.id.create_meeting_et_edit_hour);
        launchTimerPickerDialog(chooseHour);
        TextView chooseDate = findViewById(R.id.create_meeting_et_edit_date);
        launchDatePickerDialog(chooseDate);
        Button validMeeting = findViewById(R.id.create_meeting_bt_valid_meeting);
        onValidMeetingClick(validMeeting, listOfParticipant, subjectOfMeeting, chooseHour, chooseDate);
    }

    public void launchDatePickerDialog(final TextView chooseDate) {
        Button button = findViewById(R.id.create_meeting_bt_date);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                final DatePickerDialog datePickerDialog = new DatePickerDialog(
                        CreateMeetingActivity.this,
                    new DatePickerDialog.OnDateSetListener()
                    {
                        @RequiresApi(api = Build.VERSION_CODES.O)
                        @Override
                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth)
                        {
                            switch (month) {
                                case 0 :
                                    month = 1;
                                    monthSelectedInInt = 1;
                                    break;
                                case 1 :
                                    month = 2;
                                    monthSelectedInInt = 2;
                                    break;
                                case 2 :
                                    month = 3;
                                    monthSelectedInInt = 3;
                                    break;
                                case 3 :
                                    month = 4;
                                    monthSelectedInInt = 4;
                                    break;
                                case 4 :
                                    month = 5;
                                    monthSelectedInInt = 5;
                                    break;
                                case 5 :
                                    month = 6;
                                    monthSelectedInInt = 6;
                                    break;
                                case 6 :
                                    month = 7;
                                    monthSelectedInInt = 7;
                                    break;
                                case 7 :
                                    month = 8;
                                    monthSelectedInInt = 8;
                                    break;
                                case 8 :
                                    month = 9;
                                    monthSelectedInInt = 9;
                                    break;
                                case 9 :
                                    month = 10;
                                    monthSelectedInInt = 10;
                                    break;
                                case 10 :
                                    month = 11;
                                    monthSelectedInInt = 11;
                                    break;
                                case 11 :
                                    month = 12;
                                    monthSelectedInInt = 12;
                                    break;
                            }

                            yearsSelectedInInt = year;
                            daysSelectedInInt = dayOfMonth;

                            String dayInStringFormat = String.format("%02d", dayOfMonth);
                            String monthInStringFormat = String.format("%02d", month);

                            chooseDate.setText(dayInStringFormat + "/" + monthInStringFormat + "/" + year);
                            String yearInString = String.valueOf(year);
                            String dateString = dayInStringFormat + "-" + monthInStringFormat + "-" + yearInString;

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
                        CreateMeetingActivity.this, new TimePickerDialog.OnTimeSetListener()
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

                if (editable.length() > 1 && (editable.toString().endsWith(",") || editable.toString().endsWith("\n")))
                {
                    final Chip chip = new Chip(CreateMeetingActivity.this);
                    chip.setChipDrawable(ChipDrawable.createFromResource(CreateMeetingActivity.this, R.xml.chip));
                    final CharSequence charSequenceParticipantMailFromChip = editable.subSequence(SpannedLength, editable.length() - 1);
                    chip.setText(charSequenceParticipantMailFromChip);

                    // TODO : don't use TO STRING but use loop for delete []
                    listOfParticipantChip.add(listOfParticipantChip.size(), charSequenceParticipantMailFromChip.toString());

                    chip.setOnCloseIconClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            chipGroup.removeView(chip);
                            listOfParticipantChip.remove(charSequenceParticipantMailFromChip.toString());
                        }
                    });

                    chipGroup.addView(chip);
                    editable.clear();

                    if (listOfParticipantChip.size()>0) {
                        participantHint = "";
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
                if (item != null && !MyPreferencesFirstLaunch.isFirst(CreateMeetingActivity.this)) {
                    mRoom = (Integer.parseInt(item.toString()));
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}
        });

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

        ArrayAdapter<Integer> dataAdapter = new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_item, categories);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        roomOfMeeting.setAdapter(dataAdapter);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String item = parent.getItemAtPosition(position).toString();
        Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {}

    public void onValidMeetingClick(
            final Button validMeeting, final TextInputEditText listOfParticipant,
            final TextInputEditText subjectOfMeeting, final TextView chooseHour,
            final TextView chooseDate) {
        validMeeting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCreateMeetingViewModel.createMeeting(
                        mLocalDate, mHour, mRoom, subjectOfMeeting.getText().toString(),
                        listOfParticipantChip);
            }
        });
    }

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