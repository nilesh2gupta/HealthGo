package com.example.healthgo;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.IBinder;

import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;

public class NotificationService extends Service {
    private static final String CHANNEL_ID = "Notification channel";
    private static final int NOTIFICATION_ID = 100;
    private int x=122;
    private Drawable drawable;
    private BitmapDrawable bitmapDrawable;
    private Notification notifyVideoCall;

    @Override
    public void onCreate() {
        super.onCreate();
        drawable = ResourcesCompat.getDrawable(getResources(), R.drawable.callbtn, null);
        bitmapDrawable = (BitmapDrawable) drawable;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Intent i=new Intent(getApplicationContext(),Doctors.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent=PendingIntent.getActivity(getApplicationContext(), x, i, PendingIntent.FLAG_IMMUTABLE);
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            notifyVideoCall = new Notification.Builder(this)
                    .setContentIntent(pendingIntent).
                    setLargeIcon(bitmapDrawable.getBitmap()).
                    setSmallIcon(R.drawable.callbtn).
                    setContentText("Hey!! Doctor , patient is waiting for you...").
                    setSubText("Need of Doctor").
                    setChannelId(CHANNEL_ID)
                    .build();
            notificationManager.createNotificationChannel(new NotificationChannel(CHANNEL_ID, "My Channel", NotificationManager.IMPORTANCE_HIGH));
        }
        notificationManager.notify(NOTIFICATION_ID, notifyVideoCall);
        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}

