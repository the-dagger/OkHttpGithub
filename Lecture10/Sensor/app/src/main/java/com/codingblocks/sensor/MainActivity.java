package com.codingblocks.sensor;

import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.List;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    public static final String TAG = "MainActivity";
    ConstraintLayout constraintLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SensorManager sm = (SensorManager) getSystemService(SENSOR_SERVICE);
        constraintLayout = findViewById(R.id.constraint);

        List<Sensor> sensorList = sm.getSensorList(Sensor.TYPE_ALL);

        Log.e(TAG, "onCreate: " + sensorList.size());
        for (int i = 0; i < sensorList.size(); i++) {
            Sensor s = sensorList.get(i);
        }
        for (Sensor s : sensorList) {
            Log.e(TAG, "onCreate: -------------------------------------");
            Log.e(TAG, "onCreate: " + s.toString());
            Log.e(TAG, "onCreate: -------------------------------------");
        }
        Sensor accelSensor = sm.getDefaultSensor(Sensor.TYPE_GRAVITY);

        sm.registerListener(this, accelSensor, 1000000);

    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        Log.e(TAG, "Acceleration in x is : " + event.values[0]);
        Log.e(TAG, "Acceleration in y is : " + event.values[1]);
        Log.e(TAG, "Acceleration in z is : " + event.values[2]);
        int r = (int) (event.values[0] * 255 / 9.8),
                g = (int) (event.values[1] * 255 / 9.8),
                b = (int) (event.values[2] * 255 / 9.8);
        int color = Color.rgb(r, g, b);
        constraintLayout.setBackgroundColor(color);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
