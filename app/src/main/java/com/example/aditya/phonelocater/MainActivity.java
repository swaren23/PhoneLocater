package com.example.aditya.phonelocater;

import android.Manifest;
import android.location.Location;
import android.location.LocationManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.content.Context;


import com.example.aditya.phonelocater.Database.DatabaseHandler;
import com.example.aditya.phonelocater.Database.Person;


public class MainActivity extends AppCompatActivity {

    Button buttonA;
    Person person;
    private LocationManager locationManager;
    private Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonA = (Button) findViewById(R.id.button);

        buttonA.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {

                Intent myIntent = new Intent(MainActivity.this, ContactsActivity.class);

                startActivity(myIntent);
            }
        });



        if (!DatabaseHandler.getInstance(getApplicationContext()).isEmpty()) {
            person = DatabaseHandler.getInstance(getApplicationContext()).getPerson();
        }
    }



        /*
        person has attributes name and cont for name and contact no(String) of the Contact
        */





}
