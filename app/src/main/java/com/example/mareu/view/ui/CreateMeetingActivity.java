package com.example.mareu.view.ui;

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

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.mareu.R;
import com.example.mareu.utility.RangeTimePickerDialog;
import com.example.mareu.view.ViewModelFactory;
import com.example.mareu.view.ui.model.HintUiModel;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipDrawable;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.textfield.TextInputEditText;
import com.jakewharton.threetenabp.AndroidThreeTen;

import org.threeten.bp.LocalDate;
import org.threeten.bp.LocalDateTime;
import org.threeten.bp.LocalTime;
import org.threeten.bp.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import static com.example.mareu.view.ui.CreateMeetingViewModel.ViewAction.OK;

public class CreateMeetingActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private final ArrayList<String> mListOfParticipantChip = new ArrayList<>();
    private int mSpannedLength = 0;
    private final int mChipLength = 4;
    private int mRoom;
    private int mYearsSelectedInInt;
    private int mMonthSelectedInInt;
    private int mDaysSelectedInInt;
    private CreateMeetingViewModel mCreateMeetingViewModel;
    private LocalDate mLocalDate;
    private String mHour;
    private String mParticipantHint;
    private TextInputEditText meetingSubjectEditText;
    private TextInputEditText listOfParticipantEditText;
    private TextView chosenHourTextView;
    private TextView chosenDateTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_meeting);
        init();
        AndroidThreeTen.init(this);

        mCreateMeetingViewModel = new ViewModelProvider(this, ViewModelFactory.getInstance()).get(CreateMeetingViewModel.class);

        mCreateMeetingViewModel.getViewActionLiveData().observe(this, new Observer<CreateMeetingViewModel.ViewAction>() {
            @Override
            public void onChanged(CreateMeetingViewModel.ViewAction viewAction) {
                if (viewAction.equals(OK)){
                    finish();
                }
            }
        });

        mCreateMeetingViewModel.getHintUiModel().observe(this, new Observer<HintUiModel>() {
            @Override
            public void onChanged(HintUiModel hintUiModel) {
                setHint(hintUiModel, meetingSubjectEditText, listOfParticipantEditText, chosenDateTextView, chosenHourTextView);
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

    private void init() {
        meetingSubjectEditText = findViewById(R.id.create_meeting_tiet_subject);

        listOfParticipantEditText = findViewById(R.id.create_meeting_teit_listOfParticipant);
        listOfParticipantEditText.setBackground(null);

        Spinner meetingRoomSpinner = findViewById(R.id.create_meeting_spi_room);
        retriveRoomWithSpinner(meetingRoomSpinner);

        HorizontalScrollView horizontalScrollView = findViewById(R.id.horizontal_scroll_view);
        horizontalScrollView.setBackground(listOfParticipantEditText.getBackground());

        ChipGroup participantChipGroup = findViewById(R.id.chipGroup);
        retriveParticipantsWithChips(listOfParticipantEditText, participantChipGroup);

        chosenHourTextView = findViewById(R.id.create_meeting_tv_edit_hour);
        retriveTimeWithPickerDialog(chosenHourTextView);

        chosenDateTextView = findViewById(R.id.create_meeting_tv_edit_date);
        retriveDateWithPickerDialog(chosenDateTextView);

        Button validateMeetingButton = findViewById(R.id.create_meeting_bt_valid_meeting);
        validateMeeting(validateMeetingButton,meetingSubjectEditText);
    }

    private void retriveDateWithPickerDialog(final TextView chooseDate) {
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
                                    mMonthSelectedInInt = 1;
                                    break;
                                case 1 :
                                    month = 2;
                                    mMonthSelectedInInt = 2;
                                    break;
                                case 2 :
                                    month = 3;
                                    mMonthSelectedInInt = 3;
                                    break;
                                case 3 :
                                    month = 4;
                                    mMonthSelectedInInt = 4;
                                    break;
                                case 4 :
                                    month = 5;
                                    mMonthSelectedInInt = 5;
                                    break;
                                case 5 :
                                    month = 6;
                                    mMonthSelectedInInt = 6;
                                    break;
                                case 6 :
                                    month = 7;
                                    mMonthSelectedInInt = 7;
                                    break;
                                case 7 :
                                    month = 8;
                                    mMonthSelectedInInt = 8;
                                    break;
                                case 8 :
                                    month = 9;
                                    mMonthSelectedInInt = 9;
                                    break;
                                case 9 :
                                    month = 10;
                                    mMonthSelectedInInt = 10;
                                    break;
                                case 10 :
                                    month = 11;
                                    mMonthSelectedInInt = 11;
                                    break;
                                case 11 :
                                    month = 12;
                                    mMonthSelectedInInt = 12;
                                    break;
                            }

                            mYearsSelectedInInt = year;
                            mDaysSelectedInInt = dayOfMonth;

                            String dayInStringFormat = String.format(Locale.FRANCE,"%02d", dayOfMonth);
                            String monthInStringFormat = String.format(Locale.FRANCE,"%02d", month);

                            String chosenDate = dayInStringFormat + "/" + monthInStringFormat + "/" + year;
                            chooseDate.setText(chosenDate);
                            String yearInString = String.valueOf(year);
                            String dateString = dayInStringFormat + "-" + monthInStringFormat + "-" + yearInString;

                            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                            mLocalDate = LocalDate.parse(dateString, dateTimeFormatter);
                        }
                    }, LocalDate.now().getYear(), Calendar.getInstance().get(Calendar.MONTH), LocalDate.now().getDayOfMonth());
                datePickerDialog.getDatePicker().setMinDate(new Date().getTime());
                datePickerDialog.show();
            }
        });
    }

    private void retriveTimeWithPickerDialog(final TextView chooseHour) {
        Button button = findViewById(R.id.create_meeting_bt_hour);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final RangeTimePickerDialog timePickerDialog = new RangeTimePickerDialog(
                        CreateMeetingActivity.this, new TimePickerDialog.OnTimeSetListener()
                {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minutes) {
                        String chosenHour = hourOfDay + "h" + String.format(Locale.FRANCE,"%02d", minutes);
                        chooseHour.setText(chosenHour);
                        String hourInString = String.valueOf(hourOfDay);
                        String minutesInString = String.valueOf(minutes);
                        mHour = hourInString + "h" + minutesInString;
                    }
                }, LocalTime.now().getHour(), LocalTime.now().getMinute(), true);

                if (mYearsSelectedInInt == LocalDate.now().getYear()
                        && mMonthSelectedInInt == LocalDate.now().getMonthValue()
                        && mDaysSelectedInInt == LocalDate.now().getDayOfMonth())
                {
                    timePickerDialog.setMin(LocalDateTime.now().getHour(),LocalDateTime.now().getMinute());
                }
                timePickerDialog.show();
            }
        });
    }

    private void retriveParticipantsWithChips(final TextInputEditText listOfParticipant, final ChipGroup chipGroup) {

        listOfParticipant.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                if (charSequence.length() == mSpannedLength - mChipLength) {
                    mSpannedLength = charSequence.length();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

                if (editable.length() > 1 && (editable.toString().endsWith(",") || editable.toString().endsWith("\n")))
                {
                    final Chip chip = new Chip(CreateMeetingActivity.this);
                    chip.setChipDrawable(ChipDrawable.createFromResource(CreateMeetingActivity.this, R.xml.chip));
                    final CharSequence charSequenceParticipantMailFromChip = editable.subSequence(mSpannedLength, editable.length() - 1);
                    chip.setText(charSequenceParticipantMailFromChip);

                    // TODO : don't use TO STRING but use loop for delete [] (boucle charSeque... pour récupérer 1 par 1 les strings)
                    mListOfParticipantChip.add(mListOfParticipantChip.size(), charSequenceParticipantMailFromChip.toString());

                    chip.setOnCloseIconClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            chipGroup.removeView(chip);
                            mListOfParticipantChip.remove(charSequenceParticipantMailFromChip.toString());
                        }
                    });

                    chipGroup.addView(chip);
                    editable.clear();

                    if (mListOfParticipantChip.size()>0) {
                        mParticipantHint = "";
                        mCreateMeetingViewModel.setHintForParticipants(mParticipantHint);
                    }
                }
            }
        });
    }

    private void retriveRoomWithSpinner(final Spinner roomOfMeeting) {
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

        List<Integer> categories = new ArrayList<>();
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

        ArrayAdapter<Integer> dataAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, categories);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        roomOfMeeting.setAdapter(dataAdapter);
    }

    private void validateMeeting(
            final Button validateMeetingButton,
            final TextInputEditText meetingSubjectEditText) {
        validateMeetingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCreateMeetingViewModel.createMeeting(
                        mLocalDate, mHour, mRoom, Objects.requireNonNull(meetingSubjectEditText.getText()).toString(),
                        mListOfParticipantChip);
            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }

    private static class MyPreferencesFirstLaunch {
        private static final String MY_PREFERENCES = "my_preferences";
        private static boolean isFirst(Context context) {
            final SharedPreferences reader = context.getSharedPreferences(MY_PREFERENCES, Context.MODE_PRIVATE);
            final boolean first = reader.getBoolean("is_first", true);
            if (first) {
                final SharedPreferences.Editor editor = reader.edit();
                editor.putBoolean("is_first", false);
                editor.apply();
            }
            return first;
        }
    }
}