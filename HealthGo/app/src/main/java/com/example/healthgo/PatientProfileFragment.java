package com.example.healthgo;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class PatientProfileFragment extends Fragment {

    DatabaseReference db;
    FirebaseAuth auth;
    FirebaseUser user;
    public PatientProfileFragment() {
        // Required empty public constructor
    }

    TextView name,country,address,city,state,mobile,age,bloodgrp,email,gender;





    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_patient_profile, container, false);
        ImageView img=v.findViewById(R.id.imageView6);
        name=v.findViewById(R.id.name);
        country=v.findViewById(R.id.country);
        address=v.findViewById(R.id.address);
        city=v.findViewById(R.id.city);
        state=v.findViewById(R.id.state);
        mobile=v.findViewById(R.id.mobile);
        age=v.findViewById(R.id.age);
        bloodgrp=v.findViewById(R.id.blood_group);
        email=v.findViewById(R.id.textView10);
        gender=v.findViewById(R.id.gender);
        auth=FirebaseAuth.getInstance();
        user=auth.getCurrentUser();
        db= FirebaseDatabase.getInstance().getReference("Profile_Info");
        db.child("Profile_Details").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot snap:snapshot.getChildren()){
                    ProfileInfo profileInfo=snap.getValue(ProfileInfo.class);
                   if(profileInfo.getEmail().equals(user.getEmail())){
                       try{
                           name.setText(profileInfo.getName());
                           country.setText(profileInfo.getCountry());
                           address.setText(profileInfo.getAddress());
                           city.setText(profileInfo.getCity());
                           state.setText(profileInfo.getState());
                           mobile.setText(profileInfo.getMobile());
                           age.setText(profileInfo.getAge()+"Yrs");
                           bloodgrp.setText(profileInfo.getBloodgrp());
                           email.setText(user.getEmail());
                           gender.setText(profileInfo.getGender());
                       }
                       catch (Exception e){

                       }


                   }



                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity().getApplicationContext(),ProfileForm.class));
            }
        });
        return  v;
    }

}