package com.example.washmate.view.customer;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.washmate.model.role.User;
import com.example.washmate.model.role.customer;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.washmate.R;
import com.example.washmate.view.login.MainActivity;
import com.google.firebase.auth.FirebaseAuth;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Profile#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Profile extends Fragment {

    private static User LoggedInUser = User.getLoggedinUser();
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Profile() {
        // Required empty public constructor

    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Profile.
     */
    // TODO: Rename and change types and number of parameters
    public static Profile newInstance(String param1, String param2) {
        Profile fragment = new Profile();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.customer_fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        EditText nameEditText = getView().findViewById(R.id.nameEditText);
        EditText emailEditText = getView().findViewById(R.id.emailAddressEditText);
        EditText phoeNoEditText = getView().findViewById(R.id.phoeNoEditText);
        ImageView profilePic = getView().findViewById(R.id.profilePic);

        nameEditText.setText(LoggedInUser.getFullName());
        emailEditText.setText(LoggedInUser.getEmail());
        phoeNoEditText.setText(LoggedInUser.getPhoneNumber());
        Button editBtn = getView().findViewById(R.id.editTextBtn);
        Button saveBtn = getView().findViewById(R.id.saveBtn);
        Button logoutBtn = getView().findViewById(R.id.logOutBtn);
        editBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                nameEditText.setEnabled(true);
                emailEditText.setEnabled(true);
                phoeNoEditText.setEnabled(true);
                saveBtn.setVisibility(View.VISIBLE);
            }

        });

        saveBtn.setOnClickListener(new View.OnClickListener(){

            public void onClick(View v)
            {
                nameEditText.setEnabled(false);
                emailEditText.setEnabled(false);
                phoeNoEditText.setEnabled(false);
                saveBtn.setVisibility(View.GONE);

            }
        });
        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog logoutDialog = new AlertDialog.Builder(getContext())
                        .setTitle("Exit comfirmation")
                        .setMessage("Logout ?")
                        .setPositiveButton("Yes",null)
                        .setNegativeButton("No",null)
                        .show();
                Button posBtn = logoutDialog.getButton(AlertDialog.BUTTON_POSITIVE);
                Button negBtn = logoutDialog.getButton(AlertDialog.BUTTON_NEGATIVE);

                posBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(getContext(),MainActivity.class));
                    }
                });
                negBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        FirebaseAuth.getInstance().signOut();
                       logoutDialog.dismiss();
                       getActivity().finish();
                    }
                });

            }
        });







    }
}