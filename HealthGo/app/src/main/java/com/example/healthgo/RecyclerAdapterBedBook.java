package com.example.healthgo;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.icu.text.SimpleDateFormat;
import android.icu.text.Transliterator;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class RecyclerAdapterBedBook extends RecyclerView.Adapter<RecyclerAdapterBedBook.ViewHolder> {
  DatabaseReference db= FirebaseDatabase.getInstance().getReference("BedBookingDetails");
  DatabaseReference dbr=FirebaseDatabase.getInstance().getReference("Patient_Info");
  FirebaseAuth auth=FirebaseAuth.getInstance();
  FirebaseUser user=auth.getCurrentUser();
  FirebaseFirestore ff=FirebaseFirestore.getInstance();
  private final  Context context;
  private final ArrayList<BookBedDesign> arraylist;
    @SuppressLint("SimpleDateFormat") String timeStamp = new android.icu.text.SimpleDateFormat("dd-MM-yyyy HH:mm a").format(Calendar.getInstance().getTime());

    private  int lastposition=-1;

    public RecyclerAdapterBedBook(Context context, ArrayList<BookBedDesign> arraylist){
        this.context=context;
        this.arraylist=arraylist;
    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(context).inflate(R.layout.book_bed_design,parent,false);
        ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder ;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.hospname.setText(arraylist.get(position).hospitalname);
        holder.totalbed.setText(arraylist.get(position).totalbed);
        holder.availbed.setText(arraylist.get(position).availablebed);
        holder.status.setText(arraylist.get(position).status);
        holder.linearBed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder bedBookBox=new AlertDialog.Builder(context);
                bedBookBox.setTitle("Confirm BedBooking");
                bedBookBox.setIcon(R.drawable.baseline_bedroom_parent_24);
                bedBookBox.setMessage("Are you sure to book bed?");

                bedBookBox.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String totalbed=arraylist.get(position).availablebed;
                        int x=Integer.parseInt(totalbed);
                        int y=x-1;
                        String remaining=Integer.toString(y);
                        arraylist.set(position,new BookBedDesign(arraylist.get(position).hospitalname,arraylist.get(position).totalbed,remaining,timeStamp));
                        try{
                            Random random=new Random();
                            int bedid=random.nextInt(1000000);
                            String bedbookingid=Integer.toString(bedid);
                            SmsManager sms=SmsManager.getDefault();
                            sms.sendTextMessage("+919113172681","+919113172681","Your bed has been booked successfully" +
                                    "Booking id:" +
                                    bedbookingid +
                                    "\nNo of bed booked=1",null,null);

                            storePatientInfo(Integer.toString(bedid),hospitalEmail(arraylist.get(position).hospitalname));

                        }
                        catch (Exception e){
                            Log.e("smserror","Hii Nilesh");
                        }

                        notifyItemChanged(position);
                        Log.d("totalbed",totalbed);
                        Dialog dial=new Dialog(context);
                        dial.setContentView(R.layout.custom_dialog_box);
                        dial.show();
                    }

                });
                bedBookBox.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                bedBookBox.show();

            }
        });
        setAnimation(holder.itemView,position);

    }

    @Override
    public int getItemCount() {
        return arraylist.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder{
        androidx.appcompat.widget.AppCompatTextView hospname;
        androidx.appcompat.widget.AppCompatTextView totalbed;
        androidx.appcompat.widget.AppCompatTextView availbed;
        androidx.appcompat.widget.AppCompatTextView status;
        LinearLayout linearBed;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            hospname=itemView.findViewById(R.id.hospitalname);
            totalbed=itemView.findViewById(R.id.totalbed);
            availbed=itemView.findViewById(R.id.availablebed);
            status=itemView.findViewById(R.id.status);
            linearBed=itemView.findViewById(R.id.recyclerbed);

        }
    }
    private void setAnimation(View view,int position){
        if(position>lastposition){
            Animation slideIn= AnimationUtils.loadAnimation(context,R.anim.anim);
            view.startAnimation(slideIn);
            lastposition=position;
        }

    }
    public void storePatientInfo(String bookingId,String hosemail){
        dbr.child("Profile_Details").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot snap: snapshot.getChildren()){
                    ProfileInfo profileInfo=snap.getValue(ProfileInfo.class);
                    if(profileInfo.getEmail().equals(user.getEmail())){
                        String Address=profileInfo.getAddress();
                        String Age=profileInfo.getAge();
                        String Bookin_ID=bookingId;
                        String Email=profileInfo.getEmail();
                        String Feedback="No";
                        String Gender=profileInfo.getGender();
                        String Mobile_Number=profileInfo.getMobile();
                        String Name=profileInfo.getName();
                        String Need_Ambulance="YES";
                        String Problem="Heart Disease";

                       PatientBedBookingModel ppt=new PatientBedBookingModel(Address,Age,Bookin_ID,Email,Feedback,Gender,Mobile_Number,Name,Need_Ambulance,Problem);
                       Log.d("hosemail",hosemail);

                        ff.collection("Hospitals").document(hosemail).collection("Bookings").document(user.getEmail()).set(ppt).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {

                            }
                        });
                    }


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public String hospitalEmail(String hosname){
        final String[] hosEmail = new String[1];
        ff.collection("Hospitals").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                List<DocumentSnapshot> list=queryDocumentSnapshots.getDocuments();
                for(DocumentSnapshot d:list){
                    ForHospitalModel hos=d.toObject(ForHospitalModel.class);
                      if((hos.getName()+","+hos.getAddress()).equals(hosname)){
                          hosEmail[0] =hos.getEmail();
                      }
                   }

            }
        });
        return hosEmail[0];

    }
    public String phone_number(){
        final String[] phone_number = new String[1];
        dbr.child("Profile_Details").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot snap:snapshot.getChildren()){
                    ProfileInfo profileInfo=snap.getValue(ProfileInfo.class);
                    if(profileInfo.getEmail().equals(user.getEmail())){
                        phone_number[0] ="+91".concat(profileInfo.getMobile());
                        Log.d("mobile_number", phone_number[0]);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return phone_number[0];
    }

}
