package com.example.healthgo;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.Dialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Objects;


public class AlarmFragment extends Fragment {

    DatabaseReference db;
    MaterialTimePicker picker;
    RecyclerView recyclerView;
    AlarmManager alarmManager;
    PendingIntent pendingIntent;
    Calendar calendar;

    TextView setTime;
    String time;
    int i=0;
    FloatingActionButton selectedTime,setAlarm,cancelAlarm;
    ArrayList<ClockModel> arrayList=new ArrayList<>();
     AlarmAdapter alarmAdapter;
    @SuppressLint("SimpleDateFormat") String timeStamp = new SimpleDateFormat("dd-MM-yyyy").format(Calendar.getInstance().getTime());

    public AlarmFragment() {

        // Required empty public constructor
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ContextThemeWrapper contextThemeWrapper = new ContextThemeWrapper(getActivity(), R.style.AppTheme);
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_alarm, container, false);
        setTime=v.findViewById(R.id.selector_time);
        selectedTime=v.findViewById(R.id.selectedtime);
        setAlarm=v.findViewById(R.id.set_alarm);
        cancelAlarm=v.findViewById(R.id.cancel_alarm);
        recyclerView=v.findViewById(R.id.Recycler_view);
        db= FirebaseDatabase.getInstance().getReference("AlarmTime");

        createNotificationChannel();

        selectedTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePicker();

            }
        });
        try{
            setAlarm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setAlarm();
                    Dialog ala=new Dialog(getActivity());
                    ala.setContentView(R.layout.alarmdialogbox);
                    ala.show();
                }
            });
        }
        catch (Exception e){

        }

        cancelAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder alarm=new AlertDialog.Builder(getActivity());
                alarm.setTitle("Cancel Service");
                alarm.setIcon(R.drawable.baseline_alarm_off_24);
                alarm.setMessage("Are you sure to cancel alarm service?");
                alarm.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        cancelAlarm();
                    }
                });
                alarm.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                alarm.show();
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext(),RecyclerView.VERTICAL,true));
        db.child("PM").addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot){
                arrayList.clear();
                for(DataSnapshot snap:snapshot.getChildren()){
                    AlarmModel alarmModel=snap.getValue(AlarmModel.class);
                    arrayList.add(0,new ClockModel(R.drawable.baseline_access_alarm_24,alarmModel.getMsg(),alarmModel.getTime(),alarmModel.getDate()));
                }
                alarmAdapter.notifyDataSetChanged();
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        alarmAdapter=new AlarmAdapter(getContext(),arrayList);
        recyclerView.setAdapter(alarmAdapter);

        return  v;
    }
    private void cancelAlarm() {
        Intent intent=new Intent(getActivity().getApplicationContext(),AlarmReceiver.class);
        pendingIntent= PendingIntent.getBroadcast(getActivity().getApplicationContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        if(alarmManager==null){
            alarmManager=(AlarmManager) getContext().getSystemService(Context.ALARM_SERVICE);

        }
        alarmManager.cancel(pendingIntent);
    }

    private void setAlarm() {

        alarmManager=(AlarmManager) getContext().getSystemService(Context.ALARM_SERVICE);

        Intent intent=new Intent(getActivity().getApplicationContext(),AlarmReceiver.class);
        pendingIntent=PendingIntent.getBroadcast(getActivity().getApplicationContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),AlarmManager.INTERVAL_DAY,pendingIntent);
    }

    private void showTimePicker() {
        picker = new MaterialTimePicker.Builder()
                .setTimeFormat(TimeFormat.CLOCK_12H)
                .setHour(12)
                .setMinute(0)
                .setTitleText("Select Medicine time")
                .build();
        picker.show(requireActivity().getSupportFragmentManager(),"alarm");

        picker.addOnPositiveButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(picker.getHour()>12){
                    time=String.format("%02d", (picker.getHour()-12)) + ":" + String.format("%02d", picker.getMinute()) + " PM";
                   AlarmModel alarmModel=new AlarmModel(R.drawable.baseline_access_alarm_24,"Hurry up!! Take medicine",time,timeStamp);
                    db.child("PM").push().setValue(alarmModel);
                    setTime.setText(String.format("%02d", (picker.getHour()-12)) + ":" + String.format("%02d", picker.getMinute()) + " PM");
                    Toast.makeText(getActivity().getApplicationContext(), "Time is selected successfully...Click on Alarm icon to set Alarm", Toast.LENGTH_SHORT).show();


                } else {
                    time=picker.getHour() + ":" + picker.getMinute() + " AM";
                    AlarmModel alarmModel=new AlarmModel(R.drawable.baseline_access_alarm_24,"Hurry up!! Take medicine",time,timeStamp);
                    db.child("AM").push().setValue(alarmModel);
                    setTime.setText(picker.getHour() + ":" + picker.getMinute() + " AM");
                    Toast.makeText(getActivity().getApplicationContext(), "Time is selected successfully...Click on Alarm icon to set Alarm", Toast.LENGTH_SHORT).show();


                }

                calendar= Calendar.getInstance();
                calendar.set(Calendar.HOUR_OF_DAY,picker.getHour());
                calendar.set(Calendar.MINUTE,picker.getMinute());
                calendar.set(Calendar.SECOND,0);
                calendar.set(Calendar.MILLISECOND,0);



            }


        });

    }





    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "AlarmAlert";
            String description = "Channel for Alarm Manager";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel("alarm", name, importance);
            channel.setDescription(description);

            NotificationManager notificationManager = requireContext().getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }


}