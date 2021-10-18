package com.example.washmate.view.customer;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.washmate.R;
import com.example.washmate.model.appointment;
import com.example.washmate.model.car;

import java.util.ArrayList;

public class myCarsActivity extends AppCompatActivity {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customer_mycars_activity);
        ListView myCarsListView = findViewById(R.id.carListView) ;
        ImageView addBtn = findViewById(R.id.addBtn);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),addCarActiviti.class));
            }
        });
        ArrayList<car> cars = new ArrayList<>();
        cars.add(new car("AAA 123","suv"));
        cars.add(new car("bbb 456","van"));

        carslistadapter adapter = new carslistadapter(this,R.layout.mycars_row,cars);
        myCarsListView.setAdapter(adapter);

    }


}
class carslistadapter extends ArrayAdapter<car>
{
    Context mcontext;
    int mresource;

    public carslistadapter(@NonNull Context context, int resource, @NonNull ArrayList<car> objects) {
        super(context,resource,objects);
        mcontext = context;
        mresource = resource;

    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(mcontext);
        convertView = layoutInflater.inflate(mresource,parent,false);

        TextView carType = convertView.findViewById(R.id.carType);
        TextView car_numberPlate = convertView.findViewById(R.id.car_numberPlate);



        carType.setText(getItem(position).getCarType());
        car_numberPlate.setText(getItem(position).getCarplateNum());


        return convertView;
    }
}