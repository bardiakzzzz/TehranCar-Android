package com.myapps.tc_android.view.activities;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.fourmob.datetimepicker.date.DatePickerDialog;
import com.fourmob.datetimepicker.date.DatePickerDialog.OnDateSetListener;
import com.myapps.tc_android.R;
import com.sleepbot.datetimepicker.time.RadialPickerLayout;
import com.sleepbot.datetimepicker.time.TimePickerDialog;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RentCarActivity extends AppCompatActivity implements OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    public static final String START_DATEPICKER_TAG = "datepicker";
    public static final String START_TIMEPICKER_TAG = "timepicker";
    public static final String END_DATEPICKER_TAG = "datepicker";
    public static final String END_TIMEPICKER_TAG = "timepicker";
    @BindView(R.id.date_time_textview)
    TextView dateTimeTextview;
    @BindView(R.id.start_date_picker)
    ImageView startDatePicker;
    @BindView(R.id.start_date_text)
    TextView startDateText;
    @BindView(R.id.start_time_picker)
    ImageView startTimePicker;
    @BindView(R.id.start_time_text)
    TextView startTimeText;
    @BindView(R.id.end_date_time_textview)
    TextView endDateTimeTextview;
    @BindView(R.id.end_date_picker)
    ImageView endDatePicker;
    @BindView(R.id.end_date_text)
    TextView endDateText;
    @BindView(R.id.end_time_picker)
    ImageView endTimePicker;
    @BindView(R.id.end_time_text)
    TextView endTimeText;
    final Calendar startCalendar = Calendar.getInstance();
    private long today = startCalendar.getTime().getTime();
    final Calendar endCalendar = Calendar.getInstance();
    @BindView(R.id.next_page)
    Button nextPage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rent_car);
        ButterKnife.bind(this);


        final DatePickerDialog startDatePicker = DatePickerDialog.newInstance(this, startCalendar.get(Calendar.YEAR), startCalendar.get(Calendar.MONTH), startCalendar.get(Calendar.DAY_OF_MONTH), false);
        final TimePickerDialog startTimePicker = TimePickerDialog.newInstance(this, startCalendar.get(Calendar.HOUR_OF_DAY), startCalendar.get(Calendar.MINUTE), false, false);
        final DatePickerDialog endDatePicker = DatePickerDialog.newInstance(new OnDateSetListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDateSet(DatePickerDialog datePickerDialog, int year, int month, int day) {
                endDateText.setText(year + "/" + month + "/" + day);
                endCalendar.set(year, month, day);
                Log.e("TAG", "onDateSet: " + endCalendar.getTime());
            }
        }, endCalendar.get(Calendar.YEAR), endCalendar.get(Calendar.MONTH), endCalendar.get(Calendar.DAY_OF_MONTH), false);
        final TimePickerDialog endTimePicker = TimePickerDialog.newInstance(new TimePickerDialog.OnTimeSetListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute) {
                endTimeText.setText(hourOfDay + ":" + minute);
                endCalendar.set(endCalendar.get(Calendar.YEAR), endCalendar.get(Calendar.MONTH), endCalendar.get(Calendar.DATE), hourOfDay, minute);
                Log.e("TAG", "onDateSet: " + endCalendar.getTime());
            }
        }, endCalendar.get(Calendar.HOUR_OF_DAY), endCalendar.get(Calendar.MINUTE), false, false);

        findViewById(R.id.start_date_picker).setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                startDatePicker.setYearRange(2018, 2020);
                startDatePicker.show(getSupportFragmentManager(), START_DATEPICKER_TAG);
                startDatePicker.getSelectedDay();
            }
        });

        findViewById(R.id.start_time_picker).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                startTimePicker.show(getSupportFragmentManager(), START_TIMEPICKER_TAG);
            }
        });
        findViewById(R.id.end_date_picker).setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                endDatePicker.setYearRange(2018, 2020);
                endDatePicker.show(getSupportFragmentManager(), END_DATEPICKER_TAG);
            }
        });

        findViewById(R.id.end_time_picker).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                endTimePicker.show(getSupportFragmentManager(), END_TIMEPICKER_TAG);
            }
        });

        if (savedInstanceState != null) {
            DatePickerDialog dpd = (DatePickerDialog) getSupportFragmentManager().findFragmentByTag(START_DATEPICKER_TAG);
            if (dpd != null) {
                dpd.setOnDateSetListener(this);
            }
            DatePickerDialog dpd2 = (DatePickerDialog) getSupportFragmentManager().findFragmentByTag(END_DATEPICKER_TAG);
            if (dpd2 != null) {
                dpd2.setOnDateSetListener(this);
            }

            TimePickerDialog tpd = (TimePickerDialog) getSupportFragmentManager().findFragmentByTag(START_TIMEPICKER_TAG);
            if (tpd != null) {
                tpd.setOnTimeSetListener(this);
            }
            TimePickerDialog tpd2 = (TimePickerDialog) getSupportFragmentManager().findFragmentByTag(END_TIMEPICKER_TAG);
            if (tpd2 != null) {
                tpd2.setOnTimeSetListener(this);
            }
        }
    }


    @SuppressLint("SetTextI18n")
    @Override
    public void onDateSet(DatePickerDialog datePickerDialog, int year, int month, int day) {
        startDateText.setText(year + "/" + month + "/" + day);
        startCalendar.set(year, month, day);
        Log.e("TAG", "onDateSet: " + startCalendar.getTime());

    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute) {
        startTimeText.setText(hourOfDay + ":" + minute);
        startCalendar.set(startCalendar.get(Calendar.YEAR), startCalendar.get(Calendar.MONTH), startCalendar.get(Calendar.DATE), hourOfDay, minute);
        Log.e("TAG", "onDateSet: " + startCalendar.getTime());
    }

    @OnClick(R.id.next_page)
    public void onViewClicked() {
        if (invalidate()) {
            if (isDifferenceOk()) {
                //TODO
                Log.e("HI", "onViewClicked: " + ((int) (endCalendar.getTime().getTime() / (24 * 60 * 60 * 1000)) - (int) (startCalendar.getTime().getTime() / (24 * 60 * 60 * 1000))));
            }
        }
    }

    private boolean isDifferenceOk() {
        if ((int) (endCalendar.getTime().getTime() / (24 * 60 * 60 * 1000)) - (int) (today / (24 * 60 * 60 * 1000)) <= 0) {
            Toast.makeText(RentCarActivity.this, "Your end date is back of calender ", Toast.LENGTH_LONG).show();
            return false;
        }
        if ((int) (startCalendar.getTime().getTime() / (24 * 60 * 60 * 1000)) - (int) (today / (24 * 60 * 60 * 1000)) <= 0) {
            Toast.makeText(RentCarActivity.this, "Your start date is back of calender ", Toast.LENGTH_LONG).show();
            return false;
        }
        if ((int) (endCalendar.getTime().getTime() / (24 * 60 * 60 * 1000)) - (int) (startCalendar.getTime().getTime() / (24 * 60 * 60 * 1000)) <= 0) {
            Toast.makeText(RentCarActivity.this, "Your Dates are not match ", Toast.LENGTH_LONG).show();
            return false;
        }
        if ((int) (endCalendar.getTime().getTime() / (24 * 60 * 60 * 1000)) - (int) (startCalendar.getTime().getTime() / (24 * 60 * 60 * 1000)) > 30) {
            Toast.makeText(RentCarActivity.this, "Maximum day is 30", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    private boolean invalidate() {
        boolean valid = true;
        String startDate = startDateText.getText().toString();
        String startTime = startTimeText.getText().toString();
        String endDate = endDateText.getText().toString();
        String endTime = endTimeText.getText().toString();
        if (startDate.isEmpty()) {
            startDateText.setError("enter start date");
            requestFocus(startDateText);
            valid = false;
        } else {
            startDateText.setError(null);
        }
        if (startTime.isEmpty()) {
            startTimeText.setError("enter start time");
            requestFocus(startTimeText);
            valid = false;
        } else {
            startTimeText.setError(null);
        }
        if (endDate.isEmpty()) {
            endDateText.setError("enter end date");
            requestFocus(endDateText);
            valid = false;
        } else {
            endDateText.setError(null);
        }
        if (endTime.isEmpty()) {
            endTimeText.setError("enter end time");
            requestFocus(endTimeText);
            valid = false;
        } else {
            endTimeText.setError(null);
        }
        return valid;
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }
}