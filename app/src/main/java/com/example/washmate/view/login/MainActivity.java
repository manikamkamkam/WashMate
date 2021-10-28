package com.example.washmate.view.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.example.washmate.R;
import com.example.washmate.view.customer.CustomerMainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView register;
    private EditText mEmail,mPassword;
    private Button mloginBtn;
    private FirebaseAuth fAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity_main);
          mEmail = findViewById(R.id.emaillogin);
          mPassword = findViewById(R.id.passwordlogin);
          fAuth = FirebaseAuth.getInstance();
          mloginBtn = findViewById(R.id.loginButton);


        mloginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(TextUtils.isEmpty(mEmail.getText()))
                    mEmail.setError("Email is required");
                if(TextUtils.isEmpty(mPassword.getText()))
                    mEmail.setError("Password is required");



                fAuth.signInWithEmailAndPassword(mEmail.getText().toString().trim(),mPassword.getText().toString().trim()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful())
                            startActivity(new Intent(getApplicationContext(), CustomerMainActivity.class));
                    }
                });
            }
        });
        register = (TextView)  findViewById(R.id.registeranaccount);
        register.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.registeranaccount:
                startActivity(new Intent(this, RegisterUser.class));
                break;
        }
    }
}
