package com.example.mareu.view.activity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.mareu.R;
import com.example.mareu.view.alertDialog.RangeTimePickerDialog;
import com.example.mareu.view.helper.HintType;
import com.example.mareu.view.viewModel.ViewModelFactory;
import com.example.mareu.view.viewModel.CreateMeetingViewModel;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipDrawable;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.textfield.TextInputEditText;
import com.jakewharton.threetenabp.AndroidThreeTen;

import org.threeten.bp.LocalDate;
import org.threeten.bp.LocalDateTime;
import org.threeten.bp.LocalTime;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class CreateMeetingActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private final ArrayList<String> mListOfParticipantChip = new ArrayList<>();
    private final int mChipLength = 4;
    private int mSpannedLength = 0;
    private int mRoomSelected;
    private int mYearsSelectedInInt;
    private int mMonthSelectedInInt;
    private int mDaysSelectedInInt;
    private String mParticipantHint;
    private CreateMeetingViewModel mCreateMeetingViewModel;
    private LocalDate mChosenDate;
    private String mChosenDateString;
    private String mChosenTimeString;
    private TextInputEditText mMeetingSubjectEditText;
    private TextInputEditText mListOfParticipantEditText;
    private TextView mChosenHourTextView;
    private TextView mChosenDateTextView;

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
                if (viewAction.equals(CreateMeetingViewModel.ViewAction.OK)) {
                    finish();
                }
            }
        });

        mCreateMeetingViewModel.getHintUiModel().observe(this, new Observer<HintType>() {
            @Override
            public void onChanged(HintType hintType) {
                setHint(hintType, mMeetingSubjectEditText, mListOfParticipantEditText, mChosenDateTextView, mChosenHourTextView);
            }
        });

        mCreateMeetingViewModel.getMonthSelectedInInt().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer monthSelectedInInt) {
                mMonthSelectedInInt = monthSelectedInInt;
            }
        });

        mCreateMeetingViewModel.getYearsSelectedInInt().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer yearsSelectedInInt) {
                mYearsSelectedInInt = yearsSelectedInInt;
            }
        });

        mCreateMeetingViewModel.getDaysSelectedInInt().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer daysSelectedInInt) {
                mDaysSelectedInInt = daysSelectedInInt;
            }
        });

        mCreateMeetingViewModel.getChosenDateString().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String chosenDateString) {
                mChosenDateString = chosenDateString;
            }
        });

        mCreateMeetingViewModel.getChosenDate().observe(this, new Observer<LocalDate>() {
            @Override
            public void onChanged(LocalDate chosenDate) {
                mChosenDate = chosenDate;
            }
        });

        mCreateMeetingViewModel.getChosenTime().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String chosenTime) {
                mChosenTimeString = chosenTime;
            }
        });

        mCreateMeetingViewModel.getRoomSelected().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer roomSelected) {
                mRoomSelected = roomSelected;
            }
        });

    }

    private void setHint(HintType hintType,
                         TextInputEditText subjectOfMeeting,
                         TextInputEditText listOfParticipant,
                         TextView date,
                         TextView hour) {
        String hintSource = hintType.getSourceHint();
        String hintText = hintType.getTextHint();

        switch (hintSource) {
            case "Subject":
                subjectOfMeeting.setHint(hintText);
            case "Participant":
                listOfParticipant.setHint(hintText);
            case "Date":
                date.setHint(hintText);
            case "Hour":
                hour.setHint(hintText);
        }
    }

    private void init() {
        mMeetingSubjectEditText = findViewById(R.id.create_meeting_tiet_subject);

        mListOfParticipantEditText = findViewById(R.id.create_meeting_teit_listOfParticipant);

        Spinner meetingRoomSpinner = findViewById(R.id.create_meeting_spi_room);
        retriveRoomWithSpinner(meetingRoomSpinner);

        ChipGroup participantChipGroup = findViewById(R.id.chipGroup);
        retriveParticipantsWithChips(mListOfParticipantEditText, participantChipGroup);

        mChosenHourTextView = findViewById(R.id.create_meeting_tv_edit_hour);
        retriveTimeWithPickerDialog(mChosenHourTextView);

        mChosenDateTextView = findViewById(R.id.create_meeting_tv_edit_date);
        retriveDateWithPickerDialog(mChosenDateTextView);

        Button validateMeetingButton = findViewById(R.id.create_meeting_bt_valid_meeting);
        validateMeeting(validateMeetingButton, mMeetingSubjectEditText);
    }

    private void retriveDateWithPickerDialog(final TextView chooseDate) {
        Button chooseDateButton = findViewById(R.id.create_meeting_bt_date);
        chooseDateButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                final DatePickerDialog datePickerDialog = new DatePickerDialog(
                        CreateMeetingActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                mCreateMeetingViewModel.setDateSelectedWithPickerDialog(year, month, dayOfMonth);
                                chooseDate.setText(mChosenDateString);
                            }
                        }, LocalDate.now().getYear(), LocalDate.now().getMonthValue() - 1, LocalDate.now().getDayOfMonth());
                datePickerDialog.getDatePicker().setMinDate(new Date().getTime());
                datePickerDialog.show();
            }
        });
    }

    private void retriveTimeWithPickerDialog(final TextView chooseHour) {
        Button choseTimeButton = findViewById(R.id.create_meeting_bt_hour);
        choseTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final RangeTimePickerDialog timePickerDialog = new RangeTimePickerDialog(
                        CreateMeetingActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minutes) {
                        mCreateMeetingViewModel.setTimeSelectedWithPickerDialog(hourOfDay, minutes);
                        chooseHour.setText(mChosenTimeString);
                    }
                }, LocalTime.now().getHour(), LocalTime.now().getMinute(), true);

                if (mYearsSelectedInInt == LocalDate.now().getYear()
                        && mMonthSelectedInInt == LocalDate.now().getMonthValue()
                        && mDaysSelectedInInt == LocalDate.now().getDayOfMonth()) {
                    timePickerDialog.setMin(LocalDateTime.now().getHour(), LocalDateTime.now().getMinute());
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

                if (editable.length() > 1 && (editable.toString().endsWith(",") || editable.toString().endsWith("\n"))) {
                    final Chip chip = new Chip(CreateMeetingActivity.this);
                    chip.setChipDrawable(ChipDrawable.createFromResource(CreateMeetingActivity.this, R.xml.chip));

                    final CharSequence charSequenceParticipantMailFromChip = editable.subSequence(mSpannedLength, editable.length() - 1);
                    chip.setText(charSequenceParticipantMailFromChip);

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

                    if (mListOfParticipantChip.size() > 0) {
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
                mCreateMeetingViewModel.setRoomSelected(adapterView, position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
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
                        mChosenDate, mChosenTimeString, mRoomSelected, Objects.requireNonNull(meetingSubjectEditText.getText()).toString(),
                        mListOfParticipantChip);
            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }
}