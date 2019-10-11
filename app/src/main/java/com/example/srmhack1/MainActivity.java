package com.example.srmhack1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0);
        SharedPreferences.Editor editor = pref.edit();
        String email = pref.getString("email","none");
        if(email=="none") {
            Intent intent = new Intent(this,LoginActivity.class);
            finish();
            startActivity(intent);
        }
        else {
            setContentView(R.layout.activity_main);
        }
       // editor.remove("email");
        //editor.remove("id");
        SharedPreferences prefs = getApplicationContext().getSharedPreferences("MyPref",0);
        
        setContentView(R.layout.activity_main);
    }
}
