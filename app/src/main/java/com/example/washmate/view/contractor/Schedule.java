package com.example.washmate.view.contractor;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.washmate.R;
import com.example.washmate.model.appointment;
import com.example.washmate.view.customDialog.LoadingDialog;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Schedule #newInstance} factory method to
 * create an instance of this fragment.
 */
public class Schedule extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Schedule() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Appointment.
     */
    // TODO: Rename and change types and number of parameters
    public static com.example.washmate.view.customer.Appointment newInstance(String param1, String param2) {
        com.example.washmate.view.customer.Appointment fragment = new com.example.washmate.view.customer.Appointment();
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
        return inflater.inflate(R.layout.customer_fragment_appointment, container, false);
    }

    ListView appointmentlistView;
    ArrayList<appointment> appointments;
    LoadingDialog ld ;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        appointmentlistView = getView().findViewById(R.id.appointmentList);
        ld = new LoadingDialog(getActivity());
        ld.startLoadingDialog();



    }


    @Override
    public void onResume() {
        super.onResume();
        readyToUpdate();
    }

    private void readyToUpdate() {
        appointments = new appointment().getCurrentUsersOrderWithStatusIncoming(new appointment.TaskCompletedCallBack() {
            @Override
            public void isTaskCompleted(boolean isCompleted) {
                updateView();
            }
        });
    }

    private void updateView() {
        appointmentlistadapter adapter = new appointmentlistadapter(getActivity(), R.layout.appointmentlist_row, appointments);
        appointmentlistView.setAdapter(adapter);
        ld.dismissDialog();
    }

    class appointmentlistadapter extends ArrayAdapter<appointment> {
        Context mcontext;
        int mresource;

        public appointmentlistadapter(@NonNull Context context, int resource, @NonNull ArrayList<appointment> objects) {
            super(context, resource, objects);
            mcontext = context;
            mresource = resource;

        }


        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater layoutInflater = LayoutInflater.from(mcontext);
            convertView = layoutInflater.inflate(mresource, parent, false);

            TextView contratorname = convertView.findViewById(R.id.contractorName);
            TextView date = convertView.findViewById(R.id.date);
            TextView carPlateNum = convertView.findViewById(R.id.car_numberPlate);
            TextView carType = convertView.findViewById(R.id.carType);
            TextView price = convertView.findViewById(R.id.appointment_price_row);
            TextView status = convertView.findViewById(R.id.appointment_status_row);

            if(getItem(position).getInchargeContratorName()!=null)
                contratorname.setText(getItem(position).getInchargeContratorName());
            else contratorname.setText("Waiting for contractor");


            date.setText(getItem(position).getDate()+" \n"+getItem(position).getTime());
            status.setText(getItem(position).getStatus());
            price.setText("RM " + getItem(position).getPrice());
            carType.setText(getItem(position).getCarType());
            carPlateNum.setText(getItem(position).getCarPlateNo());

            return convertView;
        }
    }
}
