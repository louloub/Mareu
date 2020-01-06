package com.example.mareu.utility;

import android.app.TimePickerDialog;
import android.content.Context;
import android.widget.TimePicker;

import java.lang.reflect.Field;
import java.text.DateFormat;
import java.util.Calendar;

public class RangeTimePickerDialog extends TimePickerDialog {

    private int minHour = 0;
    private int minMinute = 0;

    private int currentHour;
    private int currentMinute;

    private final Calendar calendar = Calendar.getInstance();
    private final DateFormat dateFormat;

    public RangeTimePickerDialog(Context context, OnTimeSetListener callBack, int hourOfDay, int minute, boolean is24HourView) {
        super(context, callBack, hourOfDay, minute, is24HourView);
        currentHour = hourOfDay;
        currentMinute = minute;
        dateFormat = DateFormat.getTimeInstance(DateFormat.SHORT);

        try {
            Class<?> superclass = getClass().getSuperclass();
            Field mTimePickerField = superclass.getDeclaredField("mTimePicker");
            mTimePickerField.setAccessible(true);
            TimePicker mTimePicker = (TimePicker) mTimePickerField.get(this);
            mTimePicker.setOnTimeChangedListener(this);
        } catch (NoSuchFieldException | IllegalArgumentException | IllegalAccessException e) {
        }
    }

    public void setMin(int hour, int minute) {
        minHour = hour;
        minMinute = minute;
    }

    @Override
    public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {

        boolean validTime = true;
        if (hourOfDay < minHour || (hourOfDay == minHour && minute < minMinute)){
            validTime = false;
        }

        int maxMinute = 25;
        int maxHour = 25;
        if (hourOfDay  > maxHour || (hourOfDay == maxHour && minute > maxMinute)){
            validTime = false;
        }

        if (validTime) {
            currentHour = hourOfDay;
            currentMinute = minute;
        }

        updateTime(currentHour, currentMinute);
        updateDialogTitle(currentHour, currentMinute);
    }

    private void updateDialogTitle(int hourOfDay, int minute) {
        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
        calendar.set(Calendar.MINUTE, minute);
        String title = dateFormat.format(calendar.getTime());
        setTitle(title);
    }
}
