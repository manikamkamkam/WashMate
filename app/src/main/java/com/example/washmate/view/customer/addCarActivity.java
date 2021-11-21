package com.example.washmate.view.customer;

import android.content.Context;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.washmate.R;
import com.example.washmate.model.car;
import com.example.washmate.model.role.customer;
import com.example.washmate.view.customDialog.LoadingDialog;

public class addCarActivity extends AppCompatActivity {

    customer loginUser= customer.getLoggedinUser();
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customer_addcar_activity);

        Spinner dropdown = findViewById(R.id.carTypeSpinner);
        Button saveBtn = findViewById(R.id.addcarBtn);
        EditText plateNumber = findViewById(R.id.plateNumInput);
        EditText note = findViewById(R.id.noteInput);
        LoadingDialog ld = new LoadingDialog(this);

        String[] items = new String[]{"Select Your Car Type","SUV", "COUPE", "HATCHBACK"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        dropdown.setAdapter(adapter);



         saveBtn.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
              loginUser.addCars(plateNumber.getText().toString(),dropdown.getSelectedItem().toString(),note.getText().toString(), new car.carCallBack() {
                  @Override
                  public void isTaskCompleted(boolean isCompleted) {
                   ld.dismissDialog();
                   finish();
                  }
              });
              ld.startLoadingDialog();
             }

         });




    }
}
