package com.example.washmate.view.admin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.washmate.R;

public class adminMainActivity extends AppCompatActivity {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.admin_activity_main);

        ImageButton appointmentBtn = findViewById(R.id.appointmentBtn);
        ImageButton refundrequestBtn = findViewById(R.id.refundBtn);

        appointmentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getBaseContext(), cancelAppoinment.class));
            }
        });

        refundrequestBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getBaseContext(),refundrequest.class));
            }
        });
    }
}
