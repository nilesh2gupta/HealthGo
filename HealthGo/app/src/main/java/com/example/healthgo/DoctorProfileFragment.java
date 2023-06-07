package com.example.healthgo;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class DoctorProfileFragment extends Fragment {
    TextView name,speciality,qualifiaction,address,city,state,country,mobile,age,gender;
    DatabaseReference db;
    FirebaseAuth auth;
    FirebaseUser user;

    public DoctorProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_doctor_profile, container, false);
        name=v.findViewById(R.id.textView9);
        address=v.findViewById(R.id.address);
        city=v.findViewById(R.id.city);
        state=v.findViewById(R.id.state);
        country=v.findViewById(R.id.country);
        mobile=v.findViewById(R.id.mobile);
        age=v.findViewById(R.id.age);

        gender=v.findViewById(R.id.gender);
        speciality=v.findViewById(R.id.specialist);
        qualifiaction=v.findViewById(R.id.qualification);
        db= FirebaseDatabase.getInstance().getReference("Doctor_Profile_Info");
        auth=FirebaseAuth.getInstance();
        user=auth.getCurrentUser();
        db.child("Doctor_Profile_Details").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot snap:snapshot.getChildren()){
                    DoctorProfileInfo doctorProfileInfo=snap.getValue(DoctorProfileInfo.class);
                    Log.d("information",doctorProfileInfo.getEmail());
                    if(doctorProfileInfo.getEmail().equals(user.getEmail())){
                        name.setText(doctorProfileInfo.getName());
                        address.setText(doctorProfileInfo.getAddress());
                        city.setText(doctorProfileInfo.getCity());
                        state.setText(doctorProfileInfo.getState());
                        country.setText(doctorProfileInfo.getCountry());
                        mobile.setText(doctorProfileInfo.getMobile());
                        age.setText(doctorProfileInfo.getAge());
                        gender.setText(doctorProfileInfo.getGender());
                        speciality.setText(doctorProfileInfo.getSpeciality());
                        qualifiaction.setText(doctorProfileInfo.getQualification());
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return v;
    }
}