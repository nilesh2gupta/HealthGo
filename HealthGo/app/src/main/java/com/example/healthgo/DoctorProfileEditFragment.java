package com.example.healthgo;

import static android.app.Activity.RESULT_OK;

import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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


public class DoctorProfileEditFragment extends Fragment {

    ImageView imgcamera;
    Bitmap bitmap;
    DatabaseReference db;
    TextView text1;
    FirebaseAuth auth;
    FirebaseUser user;
    Button apply_change;
    EditText name,speciality,qualifiaction,address,city,state,country,mobile,age,bloodgrp,gender;
    String Name,Speciality,Qualification,Address,City,State,Country,Mobile,Age,Bloodgrp,Gender;
    public DoctorProfileEditFragment() {
        // Required empty public constructor
    }






    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_doctor_profile_edit, container, false);
        db= FirebaseDatabase.getInstance().getReference("Doctor_Profile_Info");
        auth=FirebaseAuth.getInstance();
        user=auth.getCurrentUser();
        imgcamera=v.findViewById(R.id.imgcamera);
        text1=v.findViewById(R.id.text1);
        name=v.findViewById(R.id.name);
        address=v.findViewById(R.id.address);
        city=v.findViewById(R.id.city);
        state=v.findViewById(R.id.state);
        country=v.findViewById(R.id.country);
        mobile=v.findViewById(R.id.mobile);
        age=v.findViewById(R.id.age);
        bloodgrp=v.findViewById(R.id.blood_group);
        gender=v.findViewById(R.id.gender);
        speciality=v.findViewById(R.id.specialist);
        qualifiaction=v.findViewById(R.id.qualification);
        apply_change=v.findViewById(R.id.apply_change);
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
                Qualification=qualifiaction.getText().toString();
                Speciality=speciality.getText().toString();
                DoctorProfileInfo profileInfo=new DoctorProfileInfo(Name,Speciality,Qualification,Address,Country,State,City,Mobile, user.getEmail(), Age,Bloodgrp,Gender);
                db.child("Doctor_Profile_Details").push().setValue(profileInfo).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(getActivity().getApplicationContext(), "Changed apply successfully!!!", Toast.LENGTH_SHORT).show();
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


        return  v;
    }
    @Override
    public  void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)  {

        if(requestCode==10){
            if(data!=null){
                Uri uri=data.getData();
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(getActivity().getApplicationContext().getContentResolver(),uri);
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
    }


}