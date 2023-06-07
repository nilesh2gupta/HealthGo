package com.example.healthgo;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Choose extends AppCompatActivity {
androidx.appcompat.widget.AppCompatImageButton doctor,patient;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose);
        doctor=findViewById(R.id.hospital);
        patient=findViewById(R.id.patient);
        doctor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ci=new Intent(Choose.this,LoginDoctor.class);
                startActivity(ci);

            }
        });
        patient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ci=new Intent(Choose.this, LoginPatient.class);
                startActivity(ci);
            }
        });
        startService(new Intent(Choose.this,AccidentDetection.class));
    }
    @Override
    public void onBackPressed() {

        AlertDialog.Builder per=new AlertDialog.Builder(Choose.this);
        per.setTitle("HealthGo");
        per.setIcon(R.drawable.launchericon);
        per.setMessage("Are you sure to exit?");
        per.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Choose.super.onBackPressed();
            }
        });
        per.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        per.show();

    }
}