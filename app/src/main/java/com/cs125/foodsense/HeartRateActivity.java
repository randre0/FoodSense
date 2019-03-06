package com.cs125.foodsense;

import android.Manifest;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

public class HeartRateActivity extends AppCompatActivity implements SensorEventListener {
    private static final String TAG = "HeartRateActivity";

    private TextView mInstructionsText;
    private ImageButton mHeartRateButton;
    private boolean mHREnabled;
    private ArrayList<Integer> mHeartRates;
    private SensorManager mSensorManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ActivityCompat.requestPermissions(HeartRateActivity.this,
                new String[]{Manifest.permission.BODY_SENSORS},
                1);
        super.onCreate(savedInstanceState);

        mHeartRates = new ArrayList<Integer>();
        setContentView(R.layout.activity_heart_rate);

        mInstructionsText = (TextView) findViewById(R.id.heartRateInstructions);
        mHREnabled = false;

        mHeartRateButton = findViewById(R.id.heartbutton);
        mHeartRateButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                if(!mHREnabled){
                    enableHeartRateSensor();
                    mInstructionsText.setText("Place Finger On Infared Sensor");
                }
            }
        });
    }

    @Override
    protected void onResume(){
        super.onResume();
    }

    @Override
    protected void onPause(){
        super.onPause();
    }

    @Override
    public void onSensorChanged(SensorEvent event){
        if(event.sensor.getType() == Sensor.TYPE_HEART_RATE){
            int hr = (int)event.values[0];
            if(hr != 0){
                mInstructionsText.setText("Hold Finger In Place");
                mHeartRates.add(hr);
            }

            if(mHeartRates.size() == 10){
                displayHeartRate(mHeartRates);
                mHeartRates = new ArrayList<Integer>();
                mHREnabled = false;
                resetHeartRateSensor();
            }

        }else{
            Log.d(TAG, "Unknown sensor type");
        }
    }
    @Override
    public void onAccuracyChanged(Sensor sensor, int accurate){

    }

    private void enableHeartRateSensor(){
        mSensorManager = ((SensorManager)getSystemService(SENSOR_SERVICE));
        Sensor mHeartRateSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_HEART_RATE);

        mSensorManager.registerListener(this,mHeartRateSensor,
                SensorManager.SENSOR_DELAY_FASTEST);
    }

    private void displayHeartRate(ArrayList<Integer> heartRates){
        int avgHR = 0;
        for(int hr : heartRates){
            avgHR += hr;
        }
        avgHR /= 10;
        String message = "Your BPM is " + Integer.toString(avgHR);
        mInstructionsText.setText(message);
    }

    private void resetHeartRateSensor(){
        mSensorManager.unregisterListener(this);
        mHREnabled = false;
    }


}
