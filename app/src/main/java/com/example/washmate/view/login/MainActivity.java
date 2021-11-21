package com.example.washmate.view.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.washmate.R;
import com.example.washmate.databinding.ContractorActivityMainBinding;
import com.example.washmate.model.role.administrator;
import com.example.washmate.model.role.contractor;
import com.example.washmate.model.role.User;
import com.example.washmate.model.role.customer;
import com.example.washmate.view.admin.adminMainActivity;
import com.example.washmate.view.contractor.contractorMainActivity;
import com.example.washmate.view.customer.CustomerMainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    //initializing firebase connection and edittexts and textviews
    private TextView register;
    private EditText mEmail, mPassword;
    private Button mloginBtn;
    private FirebaseAuth fAuth;
    private FirebaseFirestore fStore;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity_main);
        mEmail = findViewById(R.id.emaillogin);
        mPassword = findViewById(R.id.passwordlogin);
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        mloginBtn = findViewById(R.id.loginButton);



        mloginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (TextUtils.isEmpty(mEmail.getText()))
                    mEmail.setError("Email is required");
                if (TextUtils.isEmpty(mPassword.getText()))
                    mEmail.setError("Password is required");


                fAuth.signInWithEmailAndPassword(mEmail.getText().toString().trim(), mPassword.getText().toString().trim()).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult result) {
                        Toast.makeText(getBaseContext(),"Login successfully",Toast.LENGTH_SHORT).show();
                        checkRole(result.getUser().getUid());
                    }


                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getBaseContext(),e.toString(),Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        register = (TextView) findViewById(R.id.registeranaccount);
        register.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.registeranaccount:
                startActivity(new Intent(this, RegisterUser.class));
                break;
        }
    }

    public void checkRole(String Uid)
    {
        DocumentReference reference = fStore.collection("Users").document(Uid);
        reference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Log.d("TAG", "onSuccess: "+documentSnapshot.getData());

                if(documentSnapshot.getString("isAdmin") != null)
                {
                    startActivity(new Intent(getApplicationContext(), adminMainActivity.class));
                }
                if(documentSnapshot.getString("isCont") != null)
                {
                    //testing email and password contractor@cont.com | customer
                    contractor cont = new contractor(Uid,documentSnapshot.getString("FullName"),documentSnapshot.getString("UserEmail"),documentSnapshot.getString("PhoneNo"),documentSnapshot.getDouble("Balance"));
                    cont.setloginUser();
                    startActivity(new Intent(getApplicationContext(), contractorMainActivity.class));
                    finish();

                }
                if (documentSnapshot.getString("isCust") != null )
                {
                    //testing email and password customer@cust.com | customer
                    customer cust = new customer(Uid,documentSnapshot.getString("FullName"),documentSnapshot.getString("UserEmail"),documentSnapshot.getString("PhoneNo"));
                    cust.setloginUser();
                    startActivity(new Intent(getApplicationContext(),CustomerMainActivity.class));
                    finish();
                }
            }


        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getBaseContext(),e.toString(),Toast.LENGTH_SHORT).show();
            }
        });
    }

}
