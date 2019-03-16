package com.cs125.foodsense;

import android.Manifest;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.SENSOR_SERVICE;

public class HeartRateActivity extends Fragment implements SensorEventListener {
    private static final String TAG = "HeartRateActivity";

    private TextView mInstructionsText;
    private ImageButton mHeartRateButton;
    private boolean mHREnabled;
    private ArrayList<Integer> mHeartRates;
    private SensorManager mSensorManager;
    private Button mButton;
    private Integer mHeartRate;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                Bundle savedInstanceState) {
        ActivityCompat.requestPermissions(getActivity(),
                new String[]{Manifest.permission.BODY_SENSORS},
                1);
        super.onCreateView(inflater, container, savedInstanceState);
        View myView = inflater.inflate(R.layout.activity_heart_rate, container, false);

        mHeartRate = null;
        mHeartRates = new ArrayList<Integer>();
        mInstructionsText = (TextView) myView.findViewById(R.id.heartRateInstructions);
        mButton = (Button) myView.findViewById(R.id.heart_rate_submit_button);
        mHREnabled = false;

        mHeartRateButton = myView.findViewById(R.id.heartbutton);
        mHeartRateButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                if(!mHREnabled){
                    enableHeartRateSensor();
                    mInstructionsText.setText("Place Finger On Infared Sensor");
                }
            }
        });

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mHeartRate != null) {
                    Intent intent = new Intent(getContext(), HeartRateActivity.class);
                    intent.putExtra("heartrate", mHeartRate);
                    FragmentManager mFragmentManager = getFragmentManager();
                    FragmentTransaction mFragmentTransaction = mFragmentManager.beginTransaction();
                    getTargetFragment().onActivityResult(getTargetRequestCode(), RESULT_OK, intent);
                    mFragmentManager.popBackStackImmediate();
                    mFragmentTransaction.commit();
                }
            }
        });

        return myView;
    }


    @Override
    public void onSensorChanged(SensorEvent event){
        if(event.sensor.getType() == Sensor.TYPE_HEART_RATE){
            int hr = (int)event.values[0];
            if(hr != 0){
                mInstructionsText.setText("Hold Finger In Place");
                mHeartRates.add(hr);
            }

            if(mHeartRates.size() == 1){
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
        mSensorManager = ((SensorManager)getActivity().getSystemService(SENSOR_SERVICE));
        Sensor mHeartRateSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_HEART_RATE);

        mSensorManager.registerListener(this,mHeartRateSensor,
                SensorManager.SENSOR_DELAY_FASTEST);
    }

    private void displayHeartRate(ArrayList<Integer> heartRates){
        String message = "Your BPM is " + Integer.toString(heartRates.get(0));
        mHeartRate = heartRates.get(0);
        mInstructionsText.setText(message);
    }

    private void resetHeartRateSensor(){
        mSensorManager.unregisterListener(this);
        mHREnabled = false;
    }


}
