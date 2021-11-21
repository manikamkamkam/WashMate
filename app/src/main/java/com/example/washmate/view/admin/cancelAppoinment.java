package com.example.washmate.view.admin;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.washmate.R;

import java.util.ArrayList;

public class cancelAppoinment extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_cancel_appointment);
        Spinner appoinmentSpinner = findViewById(R.id.appoinmentSpinner);
        String items[] = {"Choose an appoinment to cancel","This is a sample"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, items);
        appoinmentSpinner.setAdapter(adapter);
    }
}
