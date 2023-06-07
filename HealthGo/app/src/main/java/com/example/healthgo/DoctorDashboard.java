package com.example.healthgo;

import static android.app.PendingIntent.getActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DoctorDashboard extends AppCompatActivity {
DrawerLayout drawerLayout;
NavigationView navigationView;
DatabaseReference db;
PendingIntent pendingIntent;
FirebaseAuth auth;
Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_dashboard);
        drawerLayout=findViewById(R.id.drawerlayout);
        navigationView=findViewById(R.id.navigationView);
        toolbar=findViewById(R.id.toolbar);
        db=FirebaseDatabase.getInstance().getReference("NotificationAlert");
        auth=FirebaseAuth.getInstance();
        createNotificationChannel();
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle=new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.opendrawer,R.string.closedrawer);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id=item.getItemId();
                if(id==R.id.optHome){
                    loadfrag(new DoctorProfileFragment(),true);
                }
                else if(id==R.id.optLogout){
                    AlertDialog.Builder logoutDialog=new AlertDialog.Builder(DoctorDashboard.this);
                    logoutDialog.setTitle("Logout");
                    logoutDialog.setIcon(R.drawable.baseline_logout_24);
                    logoutDialog.setMessage("Are you sure want to logout?");
                    logoutDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            auth.signOut();
                            startActivity(new Intent(getApplicationContext(), LoginDoctor.class));
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
                else if(id==R.id.opteditprofile){
                    loadfrag(new DoctorProfileEditFragment(),false);
                }
                else if(id==R.id.optVideoCall){
                    startActivity(new Intent(getApplicationContext(),Doctors.class));
                }
                drawerLayout.closeDrawer(GravityCompat.START);

                return true;
            }
        });


        navigationView.getMenu().getItem(0).setChecked(true);
        navigationView.setCheckedItem(R.id.optHome);
        loadfrag(new DoctorProfileFragment(),true);

    }

    @Override
    public void onBackPressed() {
       if(drawerLayout.isDrawerOpen(GravityCompat.START)){
           drawerLayout.closeDrawer(GravityCompat.START);
       }
       else {
           super.onBackPressed();
       }
    }

    @Override
    protected void onStart() {
        super.onStart();
        receiveMessage();
    }

    public void receiveMessage() {
        db.child("notifyAlert").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snap : snapshot.getChildren()) {
                    String alert = (String) snap.getValue();
                    if (alert != null && alert.equals("Hello")) {
                        startService(new Intent(DoctorDashboard.this, NotificationService.class));
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "NotificationAlert";
            String description = "Channel for Notification Manager";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel("Notification", name, importance);
            channel.setDescription(description);

            NotificationManager notificationManager = (NotificationManager) getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }}
    public void loadfrag(Fragment fragment,boolean flag){
        FragmentManager fm=getSupportFragmentManager();
        FragmentTransaction ft=fm.beginTransaction();
        if(flag){
            ft.add(R.id.container,fragment);
        }
        else{
            ft.replace(R.id.container,fragment);
        }

        ft.commit();
    }
}