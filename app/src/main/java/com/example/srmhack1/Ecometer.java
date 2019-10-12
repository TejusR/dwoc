package com.example.srmhack1;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class Ecometer extends AppCompatActivity {
    float distance,co2,fare,point;
    TextView t1,t2,t3,t4;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ecometer_activity);

        t1 = (TextView)findViewById(R.id.distance);
        t2 = (TextView)findViewById(R.id.co2);
        t3 = (TextView)findViewById(R.id.fare);
        t4 = (TextView)findViewById(R.id.points);

        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0);
        t1.setText("Reduced grams of CO2:  " + pref.getFloat("co2",0) + " g");
        t2.setText("Amount Saved INR: " + pref.getFloat("fare",0));
        t3.setText("ECO POINTS: " + pref.getFloat("point",100));
        t4.setText("Distance Covered in Share Rides: " + pref.getFloat("distance",2) + " km");
    }
}
