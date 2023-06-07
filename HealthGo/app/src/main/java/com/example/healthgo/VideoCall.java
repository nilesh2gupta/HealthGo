package com.example.healthgo;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.jitsi.meet.sdk.JitsiMeet;
import org.jitsi.meet.sdk.JitsiMeetActivity;
import org.jitsi.meet.sdk.JitsiMeetConferenceOptions;

import java.net.MalformedURLException;
import java.net.URL;

public class VideoCall extends AppCompatActivity {


FloatingActionButton callbtn;
DatabaseReference dbr;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_call);

        dbr= FirebaseDatabase.getInstance().getReference("NotificationAlert");
        callbtn=findViewById(R.id.callbtn);
        Intent fromact=getIntent();
        String codeword=fromact.getStringExtra("code");


         URL serverurl ;
        try {
            serverurl = new URL("https://meet.jit.si");
            JitsiMeetConferenceOptions defaultOption=
                    new JitsiMeetConferenceOptions.Builder()
                            .setServerURL(serverurl).build();

            JitsiMeet.setDefaultConferenceOptions(defaultOption);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        try{ callbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbr.child("notifyAlert").push().setValue("Hello");
                JitsiMeetConferenceOptions Option=
                        new JitsiMeetConferenceOptions.Builder().
                                setRoom(codeword).build();


                JitsiMeetActivity.launch(VideoCall.this,Option);
            }
        });}
        catch (Exception e){
            e.printStackTrace();
        }

    }
}