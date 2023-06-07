package com.example.healthgo;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Doctors extends AppCompatActivity {
androidx.appcompat.widget.AppCompatImageButton cardio,phys,dermo,gynaco,dent;
DatabaseReference databaseReference;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctors);
        cardio=findViewById(R.id.cardiologist);
        phys=findViewById(R.id.physician);
        dermo=findViewById(R.id.dermatologist);
        gynaco=findViewById(R.id.gyancologist);
        dent=findViewById(R.id.dentist);
        databaseReference= FirebaseDatabase.getInstance().getReference("Alert");
        Intent i=new Intent(Doctors.this,VideoCall.class);





        cardio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i.putExtra("code","crd123");
                startActivity(i);
            }
        });
        phys.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i.putExtra("code","phy123");
                startActivity(i);
            }
        });
        dermo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i.putExtra("code","dmt123");
                startActivity(i);
            }
        });
        dent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i.putExtra("code","den123");
                startActivity(i);
            }
        });
        gynaco.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i.putExtra("code","gyn123");
                startActivity(i);
            }
        });
    }
}