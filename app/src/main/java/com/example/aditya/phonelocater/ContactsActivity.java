package com.example.aditya.phonelocater;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.EditText;

import java.lang.StringBuilder;

import com.example.aditya.phonelocater.Database.DatabaseHandler;
import com.example.aditya.phonelocater.Database.Person;

import java.util.jar.Attributes;

public class ContactsActivity extends AppCompatActivity {

    Button buttonSave;
    EditText NameText, PhText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);



        buttonSave=(Button) findViewById(R.id.button2);
        NameText=(EditText)  findViewById(R.id.editText);
        PhText=(EditText)  findViewById(R.id.editText2);



        if (!DatabaseHandler.getInstance(getApplicationContext()).isEmpty()) {
            buttonSave.setText("Update");
            Person pers=DatabaseHandler.getInstance(getApplicationContext()).getPerson();
            NameText.setText(pers.name);
            PhText.setText(pers.cont);
        }
        else{
            buttonSave.setText("Save");
        }


        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Person person=new Person();



                final StringBuilder sb = new StringBuilder(50);
                sb.append(NameText.getText());
                person.name = sb.toString();


                final StringBuilder sb2 = new StringBuilder(20);
                sb2.append(PhText.getText());
                person.cont = sb2.toString();



                DatabaseHandler.getInstance(getApplicationContext()).putPerson(person);

                buttonSave.setText("Saved");


            }
        });
    }


}
