package com.example.healthgo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class PatientDashboard extends AppCompatActivity {
FrameLayout frameLayout;
FirebaseAuth auth;
BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_dashboard);
        bottomNavigationView=findViewById(R.id.bottonNavigation);
        auth=FirebaseAuth.getInstance();

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id=item.getItemId();
                if(id==R.id.nav_alarm){
                   loadfrag(new AlarmFragment(),false);
                }
                else if(id==R.id.nav_location){
                    loadfrag(new MapsFragment(),false);
                }
                else if(id==R.id.nav_logout){
                    AlertDialog.Builder logoutDialog=new AlertDialog.Builder(PatientDashboard.this);
                    logoutDialog.setTitle("Logout");
                    logoutDialog.setIcon(R.drawable.baseline_logout_24);
                    logoutDialog.setMessage("Are you sure want to logout?");
                    logoutDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            auth.signOut();
                            startActivity(new Intent(getApplicationContext(), LoginPatient.class));
                            finish();
                        }
                    });
                    logoutDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    logoutDialog.show();

                }
                else if(id==R.id.nav_home){
                   loadfrag(new HomeFragment(),false);
                }
                else if(id==R.id.nav_profile){
                    loadfrag((new PatientProfileFragment()),true);
                }
                return true;
            }
        });
        bottomNavigationView.setSelectedItemId(R.id.nav_profile);


    }
    public void loadfrag(Fragment fragment,boolean flag){
        FragmentManager fm=getSupportFragmentManager();
        FragmentTransaction ft=fm.beginTransaction();
        if(flag){
            ft.add(R.id.framelayout,fragment);
        }
        else{
            ft.replace(R.id.framelayout,fragment);
        }

        ft.commit();
    }


}