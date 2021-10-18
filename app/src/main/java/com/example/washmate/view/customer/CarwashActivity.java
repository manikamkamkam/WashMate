package com.example.washmate.view.customer;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import com.example.washmate.R;
import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.text.DateFormat;
import java.time.Month;
import java.time.Year;
import java.util.Calendar;

public class CarwashActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    private Context context;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customer_carwash_activity);
        context=this;
        Spinner dropdown = findViewById(R.id.carsSpinner);
        String[] items = new String[]{"Select Your Car","AAO 8888", "BBB 7772", "CCC 2223"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        dropdown.setAdapter(adapter);

        EditText date = findViewById(R.id.appointment_date);
        EditText time = findViewById(R.id.appointment_time);
        ImageView backButton = findViewById(R.id.backArrow);

        backButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                startActivity(new Intent(context,CustomerMainActivity.class));
            }
        });
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment datePicker = new datePickerFragment();
                datePicker.show(getSupportFragmentManager(),"date");
            }
        });

        time.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View view) {
                DialogFragment timePicker = new timePickerFragment();
                timePicker.show(getSupportFragmentManager(),"time");
            }
        });

    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
Calendar c = Calendar.getInstance();
c.set(Calendar.YEAR,year);
c.set(Calendar.MONTH, month);
c.set(Calendar.DAY_OF_MONTH,dayOfMonth);
String dateSelected = DateFormat.getDateInstance(DateFormat.FULL).format(c.getTime());
        EditText date = findViewById(R.id.appointment_date);
        date.setText(dateSelected);
    }

    @Override
    public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute, int second) {
        EditText time = findViewById(R.id.appointment_time);
        time.setText(hourOfDay+" : "+minute);
    }
}
