package com.example.healthgo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;

public class ProfileForm extends AppCompatActivity {
   ImageView imgcamera;
   Bitmap bitmap;
   DatabaseReference db;
   TextView text1;
   FirebaseAuth auth;
   FirebaseUser user;
   Button apply_change;
   EditText name,address,city,state,country,mobile,age,bloodgrp,gender;
   String Name,Address,City,State,Country,Mobile,Age,Bloodgrp,Gender;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_form);
        db= FirebaseDatabase.getInstance().getReference("Profile_Info");
        auth=FirebaseAuth.getInstance();
        user=auth.getCurrentUser();
        imgcamera=findViewById(R.id.imgcamera);
        text1=findViewById(R.id.text1);
        name=findViewById(R.id.name);
        address=findViewById(R.id.address);
        city=findViewById(R.id.city);
        state=findViewById(R.id.state);
        country=findViewById(R.id.country);
        mobile=findViewById(R.id.mobile);
        age=findViewById(R.id.age);
        bloodgrp=findViewById(R.id.blood_group);
        gender=findViewById(R.id.gender);
        apply_change=findViewById(R.id.apply_change);





        apply_change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Name=name.getText().toString();
                Address=address.getText().toString();
                City=city.getText().toString();
                State=state.getText().toString();
                Country=country.getText().toString();
                Mobile=mobile.getText().toString();
                Age=age.getText().toString();
                Bloodgrp=bloodgrp.getText().toString();
                Gender=gender.getText().toString();
                ProfileInfo profileInfo=new ProfileInfo(Name,Address,Country,State,City,Mobile, user.getEmail(), Age,Bloodgrp,Gender);
                db.child("Profile_Details").push().setValue(profileInfo).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(ProfileForm.this, "Changed apply successfully!!!", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });

        text1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent,10);
            }
        });
        imgcamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent camerai=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(camerai,100);
            }
        });



    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)  {

        if(requestCode==10){
            if(data!=null){
                Uri uri=data.getData();
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(),uri);
                    imgcamera.setImageBitmap(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK){

            if(requestCode==100){
                try{
                    bitmap=(Bitmap)data.getExtras().get("data");
                    imgcamera.setImageBitmap(bitmap);}
                catch (Exception e){
                    e.printStackTrace();
                }
            }

    }
}}