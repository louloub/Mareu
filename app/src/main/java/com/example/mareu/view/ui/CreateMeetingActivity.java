package com.example.mareu.view.ui;

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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
    private LocalDate mChosenDate;
    private String mParticipantHint;
    private String mChosenDateString;
    private String mChosenTimeString;
    private TextInputEditText mMeetingSubjectEditText;
    private TextInputEditText mListOfParticipantEditText;
    private TextView mChosenHourTextView;
    private TextView mChosenDateTextView;
    private CharSequence mParticipantChip;
    private Chip mChip;

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
                setHint(hintUiModel, mMeetingSubjectEditText, mListOfParticipantEditText, mChosenDateTextView, mChosenHourTextView);
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

        mCreateMeetingViewModel.getParticipantChip().observe(this, new Observer<CharSequence>() {
            @Override
            public void onChanged(CharSequence charSequenceParticipantFromChip) {
                mParticipantChip = charSequenceParticipantFromChip;
                mChip.setText(mParticipantChip);

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

        mChip = new Chip(CreateMeetingActivity.this);
    }

    private void retriveDateWithPickerDialog(final TextView chooseDate) {
        Button chooseDateButton = findViewById(R.id.create_meeting_bt_date);
        chooseDateButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v)
            {
                final DatePickerDialog datePickerDialog = new DatePickerDialog(
                        CreateMeetingActivity.this,
                    new DatePickerDialog.OnDateSetListener()
                    {
                        @Override
                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                            mCreateMeetingViewModel.setDateSelectedWithPickerDialog(year,month,dayOfMonth);
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
                        CreateMeetingActivity.this, new TimePickerDialog.OnTimeSetListener()
                {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minutes) {
                        mCreateMeetingViewModel.setTimeSelectedWithPickerDialog(hourOfDay,minutes);
                        chooseHour.setText(mChosenTimeString);
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
                    mChip.setChipDrawable(ChipDrawable.createFromResource(CreateMeetingActivity.this, R.xml.chip));
                    mCreateMeetingViewModel.setTextOfChip(editable,mSpannedLength);

                    // TODO : don't use TO STRING but use loop for delete [] (boucle charSeque... pour récupérer 1 par 1 les strings)
                    mListOfParticipantChip.add(mListOfParticipantChip.size(), mParticipantChip.toString());

                    mChip.setOnCloseIconClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            chipGroup.removeView(mChip);
                            mListOfParticipantChip.remove(mParticipantChip.toString());
                        }
                    });

                    chipGroup.addView(mChip);
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
                        mChosenDate, mChosenTimeString, mRoom, Objects.requireNonNull(meetingSubjectEditText.getText()).toString(),
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