package com.example.healthgo;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.IBinder;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AccidentDetection extends Service implements SensorEventListener {
    DatabaseReference db= FirebaseDatabase.getInstance().getReference("Accident alert");
    private SensorManager sensorManager;
    private Sensor accelerometer;

    private static final int WINDOW_SIZE = 50;
    private static final float FALL_THRESHOLD = 9.8f * 2;

    private float[] accelerationWindow = new float[WINDOW_SIZE];
    private int windowIndex = 0;

    private static final int NOTIFICATION_ID = 1;
    private static final String CHANNEL_ID = "fall_detection_channel";

    @Override
    public void onCreate() {
        super.onCreate();

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        if (sensorManager != null) {
            accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {



        if (accelerometer != null) {
            sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        }

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if (sensorManager != null) {
            sensorManager.unregisterListener(this);
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];

            float acceleration = (float) Math.sqrt(x * x + y * y + z * z);

            accelerationWindow[windowIndex] = acceleration;
            windowIndex = (windowIndex + 1) % WINDOW_SIZE;

            if (isFallDetected()) {
                // Fall detected, take appropriate actions
                db.child("Emergency").push().setValue("Accident Detected");
                // Add your own logic here, such as triggering emergency response or notifying user.
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Not used in this example
    }

    private boolean isFallDetected() {
        // Check if the maximum acceleration in the window exceeds the fall threshold
        float maxAcceleration = 0.0f;
        for (float acceleration : accelerationWindow) {
            if (acceleration > maxAcceleration) {
                maxAcceleration = acceleration;
            }
        }

        return maxAcceleration > FALL_THRESHOLD;
    }

}

