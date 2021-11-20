package com.example.washmate.view.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.washmate.R;
import com.example.washmate.model.role.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterUser extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth mAuth;

    private TextView logintitle, registerbutton;
    private EditText editTextFName, editTextAge, editTextEmail, editTextPass;
    private Spinner choicereg;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);

        //spinner
        Spinner dropdown = findViewById(R.id.rolereg);
        String[] items = new String[]{"Contractor", "Customer"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        dropdown.setAdapter(adapter);

        mAuth = FirebaseAuth.getInstance();

        logintitle = (TextView) findViewById(R.id.logintitle);
        logintitle.setOnClickListener(this);

        registerbutton = (Button)findViewById(R.id.registerbutton);
        registerbutton.setOnClickListener(this);

        editTextEmail = (EditText) findViewById(R.id.emailreg);
        editTextAge = (EditText) findViewById(R.id.agereg);
        editTextFName = (EditText) findViewById(R.id.fnamereg);
        editTextPass = (EditText) findViewById(R.id.pasreg);

        choicereg = (Spinner) findViewById(R.id.rolereg);

        progressBar = (ProgressBar)findViewById(R.id.progressbar);
    }

    @Override
    public void onClick(View v){
        switch ((v.getId())){
            case R.id.logintitle:
                startActivity(new Intent(this, MainActivity.class));
                break;
            case R.id.registerbutton:
                registerbutton();
                break;
        }
    }

    private void registerbutton(){
        String fname = editTextFName.getText().toString().trim();
        String age = editTextAge.getText().toString().trim();
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPass.getText().toString().trim();

        if(fname.isEmpty()){
            editTextFName.setError("Full name is required");
            editTextFName.requestFocus();
            return;
        }

        if(age.isEmpty()){
            editTextAge.setError("Age is required");
            editTextAge.requestFocus();
            return;
        }

        if(email.isEmpty()){
            editTextEmail.setError("Email is required");
            editTextEmail.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            editTextEmail.setError("Email must be valid");
            editTextEmail.requestFocus();
            return;
        }

        if(password.isEmpty()){
            editTextPass.setError("Password is required");
            editTextPass.requestFocus();
            return;
        }

        if(password.length()<5){
            editTextPass.setError("Password must be more than 5 characters long");
            editTextPass.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            User user = new User(fname, age, email);

                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(RegisterUser.this, "User has been registered", Toast.LENGTH_SHORT).show();
                                        progressBar.setVisibility(View.VISIBLE);
                                        
                                    }else{
                                        Toast.makeText(RegisterUser.this, "Failed to register", Toast.LENGTH_SHORT).show();
                                        progressBar.setVisibility(View.GONE);
                                    }
                                }
                            });
                        }else{
                            Toast.makeText(RegisterUser.this, "Failed to register", Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });

    }
}