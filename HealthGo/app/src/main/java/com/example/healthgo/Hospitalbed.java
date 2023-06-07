package com.example.healthgo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.collection.ArraySet;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Hospitalbed extends AppCompatActivity {

FirebaseFirestore db;
TextView clock;

    RecyclerAdapterBedBook adapterBedBook;
   public  ArrayList<BookBedDesign> arraylist=new ArrayList<>();
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospitalbed);
        RecyclerView recyclerViewbed;
        recyclerViewbed=findViewById(R.id.recyclerViewbed);
        db=FirebaseFirestore.getInstance();
        @SuppressLint("SimpleDateFormat") String timeStamp = new android.icu.text.SimpleDateFormat("dd-MM-yyyy HH:mm a").format(Calendar.getInstance().getTime());

        recyclerViewbed.setLayoutManager(new LinearLayoutManager(getApplicationContext(),RecyclerView.VERTICAL,true));


        db.collection("Hospitals").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                List<DocumentSnapshot> list=queryDocumentSnapshots.getDocuments();
                for(DocumentSnapshot d:list){
                    ForHospitalModel hos=d.toObject(ForHospitalModel.class);
                    arraylist.add(0,new BookBedDesign(hos.getName()+","+hos.getAddress(),Integer.toString(hos.getBeds()),Integer.toString(hos.getBeds()),timeStamp));
                }
                adapterBedBook.notifyDataSetChanged();
            }
        });

       recyclerViewbed.setHasFixedSize(true);
        adapterBedBook=new RecyclerAdapterBedBook(this,arraylist);
        recyclerViewbed.setAdapter(adapterBedBook);


       /* clock=findViewById(R.id.clock);
        Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                String currentTime = new SimpleDateFormat("HH:mm:ss").format(new Date());
                clock.setText("Time: " + currentTime);
                handler.postDelayed(this, 1000);
            }
        });*/

    }

    public void BookBed(View v){
        String url="https://healthgo.onrender.com";
        Uri u= Uri.parse(url);
        Intent i=new Intent(Intent.ACTION_VIEW,u);
        startActivity(i);

    }

}