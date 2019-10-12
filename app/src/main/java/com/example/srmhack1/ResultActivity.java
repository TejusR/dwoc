package com.example.srmhack1;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class ResultActivity extends AppCompatActivity {
    String driver,user,from,to,date;
    double distance,time;
    TextView t1,t2,t3,t4,t5,t6,t7;
    SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.result_activity);
        t1 = (TextView)findViewById(R.id.driver_name);
        t2 = (TextView)findViewById(R.id.username);
        t3 = (TextView)findViewById(R.id.from);
        t4 = (TextView)findViewById(R.id.to);
        t5 = (TextView)findViewById(R.id.distance);
        t6 = (TextView)findViewById(R.id.esttime);
        t7 = (TextView)findViewById(R.id.date);
        Intent intent = getIntent();

        driver = intent.getStringExtra("driver");
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0);
        user = pref.getString("email","none");
        from = intent.getStringExtra("from");
        to = intent.getStringExtra("to");
        date = intent.getStringExtra("date");
        distance = intent.getDoubleExtra("distance",0);
        time = intent.getDoubleExtra("time",0);
        t1.setText("Ride Offered by (username): " + driver);
        t2.setText("Ride Shared by (username): " + user);
        t3.setText("From: " + from);
        t4.setText("To  : " + to);
        t5.setText("Distance (in km): " + distance);
        t6.setText("Estimated time: (in min): " + time);
        t7.setText("Travel Date: " + date);

        SharedPreferences.Editor editor = pref.edit();
        float co2 = pref.getFloat("co2",0);
        float dist = pref.getFloat("distance",0);
        float fare = pref.getFloat("fare",0);
        float point = pref.getFloat("point",0);

        dist+=distance;
        co2+=(2640/15.0)*distance;
        fare+= 40 + distance*10;
        point = point + Math.round(dist)*100;
        editor.putFloat("co2",co2);
        editor.putFloat("distance",dist);
        editor.putFloat("fare",fare);
        editor.putFloat("point",point);
        editor.commit();
    }
}
