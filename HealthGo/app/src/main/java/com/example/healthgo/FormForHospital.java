package com.example.healthgo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class FormForHospital extends AppCompatActivity {
    DatabaseReference db;

Button goonline,send;
EditText hosName,hosAdd,totalBed,availBed,totalDoc;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_for_hospital);
        hosName =findViewById(R.id.hosname);
        hosAdd=findViewById(R.id.hosadd);
        totalBed=findViewById(R.id.totalbed);
        availBed=findViewById(R.id.availablebed);
        totalDoc=findViewById(R.id.totaldoc);
        goonline=findViewById(R.id.goOnline);
        send=findViewById(R.id.send);
        db=FirebaseDatabase.getInstance().getReference("NotificationAlert");
        goonline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),AlarmFragment.class));


            }
        });


        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String hosname=hosName.getText().toString();
                String hosadd=hosAdd.getText().toString();
                String totalbed=totalBed.getText().toString();
                String availbed=availBed.getText().toString();
                String totaldoc=totalDoc.getText().toString();

            }
        });


       receiveMessage();


    }
    public void receiveMessage() {
        db.child("notifyAlert").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snap : snapshot.getChildren()) {
                    String alert = (String) snap.getValue();
                    if (alert != null && alert.equals("Hello")) {
                        startService(new Intent(FormForHospital.this, NotificationService.class));
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}