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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import org.w3c.dom.Document;

import java.util.HashMap;
import java.util.Map;

public class RegisterUser extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth mAuth;

    private TextView logintitle, registerbutton;
    private EditText editTextFName, editTextEmail, editTextPass;
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
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPass.getText().toString().trim();
        String role = choicereg.getSelectedItem().toString().trim();
        if(fname.isEmpty()){
            editTextFName.setError("Full name is required");
            editTextFName.requestFocus();
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
                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                           DocumentReference df = FirebaseFirestore.getInstance().collection("Users").document(user.getUid());
                            Map<String,Object> userInfo = new HashMap<>();

                                 userInfo.put("FullName",fname);
                                 userInfo.put("UserEmail",email);
                                 userInfo.put("PhoneNo"," ");

                                 if(role.equals("Contractor"))
                                     userInfo.put("isCont","1");userInfo.put("Balance",0.00);
                                 if(role.equals("Customer"))
                                     userInfo.put("isCust","1");

                                 df.set(userInfo);
                                 progressBar.setVisibility(View.GONE);
                                 finish();
                            Toast.makeText(RegisterUser.this, "Successfully registered , Please Login", Toast.LENGTH_SHORT).show();


                        }else{
                            Toast.makeText(RegisterUser.this, "Failed to register", Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });

    }
}