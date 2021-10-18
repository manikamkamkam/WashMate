package com.example.washmate.view.customer;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.washmate.model.appointment;
import com.example.washmate.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Appointment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Appointment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Appointment() {
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
    public static Appointment newInstance(String param1, String param2) {
        Appointment fragment = new Appointment();
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

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ListView appointmentlistView;
        appointmentlistView = getView().findViewById(R.id.appointmentList);

        ArrayList<appointment> appointments = new ArrayList<>();

        // Use for example//
        appointments.add(new appointment("10 oct 2021 , 12:00 AM ","RM 500","ContratorName","Completed"));
        appointments.add(new appointment("10 oct 2021 , 12:00 AM ","RM 500","ContratorName","Completed"));
        appointments.add(new appointment("10 oct 2021 , 12:00 AM ","RM 500","ContratorName","Completed"));
        appointments.add(new appointment("10 oct 2021 , 12:00 AM ","RM 500","ContratorName","Completed"));
        appointments.add(new appointment("10 oct 2021 , 12:00 AM ","RM 500","ContratorName","Incoming"));
        appointments.add(new appointment("10 oct 2021 , 12:00 AM ","RM 500","ContratorName","Completed"));
        appointments.add(new appointment("10 oct 2021 , 12:00 AM ","RM 500","ContratorName","Completed"));
        appointments.add(new appointment("10 oct 2021 , 12:00 AM ","RM 500","ContratorName","Completed"));
        appointments.add(new appointment("10 oct 2021 , 12:00 AM ","RM 500","ContratorName","Completed"));
        appointments.add(new appointment("10 oct 2021 , 12:00 AM ","RM 500","ContratorName","Incoming"));
        appointments.add(new appointment("10 oct 2021 , 12:00 AM ","RM 500","ContratorName","Completed"));
        appointments.add(new appointment("10 oct 2021 , 12:00 AM ","RM 500","ContratorName","Completed"));
        appointments.add(new appointment("10 oct 2021 , 12:00 AM ","RM 500","ContratorName","Completed"));
        appointments.add(new appointment("10 oct 2021 , 12:00 AM ","RM 500","ContratorName","Completed"));
        appointments.add(new appointment("10 oct 2021 , 12:00 AM ","RM 500","ContratorName","Incoming"));
        appointments.add(new appointment("10 oct 2021 , 12:00 AM ","RM 500","ContratorName","Completed"));
        appointments.add(new appointment("10 oct 2021 , 12:00 AM ","RM 500","ContratorName","Completed"));
        appointments.add(new appointment("10 oct 2021 , 12:00 AM ","RM 500","ContratorName","Completed"));
        appointments.add(new appointment("10 oct 2021 , 12:00 AM ","RM 500","ContratorName","Completed"));
        appointments.add(new appointment("10 oct 2021 , 12:00 AM ","RM 500","ContratorName","Incoming"));

// Use for example//
        appointmentlistadapter adapter = new appointmentlistadapter(getActivity(),R.layout.appointmentlist_row,appointments);
        appointmentlistView.setAdapter(adapter);
    }
}
class appointmentlistadapter extends ArrayAdapter<appointment>
{
Context mcontext;
int mresource;

    public appointmentlistadapter(@NonNull Context context, int resource, @NonNull ArrayList<appointment> objects) {
        super(context,resource,objects);
       mcontext = context;
       mresource = resource;

    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(mcontext);
        convertView = layoutInflater.inflate(mresource,parent,false);

        TextView contratorname = convertView.findViewById(R.id.car_numberPlate);
        TextView date = convertView.findViewById(R.id.carType);
        TextView price = convertView.findViewById(R.id.appointment_price_row);
        TextView status = convertView.findViewById(R.id.appointment_status_row);

        contratorname.setText(getItem(position).getInchargeContratorName());
        date.setText(getItem(position).getDate());
        status.setText(getItem(position).getStatus());
        price.setText(getItem(position).getPrice());

        return convertView;
    }
}
