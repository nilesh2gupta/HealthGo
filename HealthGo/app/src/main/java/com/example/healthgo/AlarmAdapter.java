package com.example.healthgo;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.icu.text.SimpleDateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Calendar;

public class AlarmAdapter extends RecyclerView.Adapter<AlarmAdapter.ViewHolder> {
    DatabaseReference db= FirebaseDatabase.getInstance().getReference("AlarmTime");
    private  final Context context;
    private  int lastposition=-1;
    private final  ArrayList<ClockModel> arrayList;


    public AlarmAdapter(Context context, ArrayList<ClockModel> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(context).inflate(R.layout.alarm_view,parent,false);
        ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder ;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
      holder.imageView.setImageResource(arrayList.get(position).img);
      holder.time.setText(arrayList.get(position).time);
      holder.date.setText(arrayList.get(position).date);
      holder.msg.setText(arrayList.get(position).msg);
      holder.alarmview.setOnLongClickListener(new View.OnLongClickListener() {
          @Override
          public boolean onLongClick(View v) {
              AlertDialog.Builder alarmBox=new AlertDialog.Builder(context);
              alarmBox.setTitle("Delete");
              alarmBox.setIcon(R.drawable.baseline_auto_delete_24);
              alarmBox.setMessage("Are you sure to delete this time?");
              alarmBox.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                  @Override
                  public void onClick(DialogInterface dialog, int which) {
                     arrayList.remove(position);
                     notifyItemRemoved(position);
                     db.child("PM").removeValue();

                  }
              });
              alarmBox.setNegativeButton("No", new DialogInterface.OnClickListener() {
                  @Override
                  public void onClick(DialogInterface dialog, int which) {

                  }
              });
              alarmBox.show();
              return true;
          }
      });
        setAnimation(holder.itemView,position);
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
          TextView time;
          LinearLayout alarmview;
          TextView date;
          ImageView imageView;
          TextView msg;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            time=itemView.findViewById(R.id.alarmtime);
            date=itemView.findViewById(R.id.alarmdate);
            imageView=itemView.findViewById(R.id.alarmimg);
            msg=itemView.findViewById(R.id.alarmmesg);
            alarmview=itemView.findViewById(R.id.alarmview);
        }
    }
    private void setAnimation(View view,int position){
        if(position>lastposition){
            Animation slideIn= AnimationUtils.loadAnimation(context,R.anim.anim);
            view.startAnimation(slideIn);
            lastposition=position;
        }

    }

}
