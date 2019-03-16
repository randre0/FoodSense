package com.cs125.foodsense;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import com.cs125.foodsense.data.entity.FoodJournal;
import com.cs125.foodsense.data.entity.HeartRate;
import com.cs125.foodsense.data.util.Converters;
import com.cs125.foodsense.data.util.Utility;
import com.cs125.foodsense.data.view_model.FoodJournalViewModel;
import com.cs125.foodsense.data.view_model.HeartRateViewModel;

import static android.app.Activity.RESULT_OK;


public class LogFoodFragment extends Fragment {

    public static final int REQUEST_CODE = 1;
    private ImageButton mLogHRButton;
    private ImageButton mLogFoodButton;
    private int mBeforeHeartRate;
    private int mAfterHeartRate;
    private String mFood;
    private String mCategory;
    private View myView = null;
    private boolean heartRateClick;
    private boolean logfoodClick;
    private HeartRateViewModel vm_heart_rate;
    private FoodJournalViewModel vm_food_journal;
    private LogFoodFragment myFragment;
    private final String MY_FRAGMENT_TAG = "logfoodfragment";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if(myView != null){
            if((ViewGroup) myView.getParent() != null)
                ((ViewGroup) myView.getParent()).removeView(myView);
            return myView;
        }
        myView = inflater.inflate(R.layout.fragment_log_food, container, false);
        vm_heart_rate = ViewModelProviders.of(this).get(HeartRateViewModel.class);
        vm_food_journal = ViewModelProviders.of(this).get(FoodJournalViewModel.class);
        mLogHRButton = (ImageButton) myView.findViewById(R.id.log_heart_rate);
        mLogFoodButton = (ImageButton) myView.findViewById(R.id.log_food);
        mBeforeHeartRate = Integer.MIN_VALUE;
        mAfterHeartRate = Integer.MIN_VALUE;
        mFood = null;
        mCategory = null;
        heartRateClick = false;
        logfoodClick = false;

        mLogHRButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                FragmentManager mFragmentManager = getFragmentManager();
                FragmentTransaction mFragmentTransaction = mFragmentManager.beginTransaction();
                Fragment heartRateFragment = new HeartRateActivity();
                heartRateFragment.setTargetFragment(LogFoodFragment.this,REQUEST_CODE);
                mFragmentTransaction.addToBackStack("loghr");
                mFragmentTransaction.replace(R.id.container, heartRateFragment);
                mFragmentTransaction.commit();
                heartRateClick = true;
            }
        });

        mLogFoodButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                FragmentManager mFragmentManager = getFragmentManager();
                FragmentTransaction mFragmentTransaction = mFragmentManager.beginTransaction();
                Fragment inputFoodFragment = new InputFoodActivity();
                inputFoodFragment.setTargetFragment(LogFoodFragment.this,REQUEST_CODE);
                mFragmentTransaction.addToBackStack("logfood");
                mFragmentTransaction.replace(R.id.container, inputFoodFragment);
                mFragmentTransaction.commit();
                logfoodClick = true;
            }
        });

        return myView;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        try{
            super.onActivityResult(requestCode, resultCode, data);
            if(heartRateClick) {
                if (resultCode == RESULT_OK) {
                    int result = data.getIntExtra("heartrate", 0);
                    if(mBeforeHeartRate == Integer.MIN_VALUE){
                        mBeforeHeartRate = result;
                    }else if(mAfterHeartRate == Integer.MIN_VALUE &&
                            mFood != null & mCategory != null){
                            mAfterHeartRate = result;
                            //Send to database
                            HeartRate beforeHR = new HeartRate("default@uci.edu",
                                    mBeforeHeartRate, "BEFORE");
                            HeartRate afterHR = new HeartRate("default@uci.edu",
                                    mAfterHeartRate, "AFTER");
                            vm_heart_rate.insert(beforeHR);
                            vm_heart_rate.insert(afterHR);
                            FoodJournal entry = new FoodJournal("default@uci.edu",
                                    mFood, Converters.toDateString(Utility.getCurrentDateTime()));
                            entry.setHrDiff((double)(mAfterHeartRate - mBeforeHeartRate));
                            vm_food_journal.insert(entry);
                            mFood = null;
                            mCategory = null;
                        Toast.makeText(getActivity(), "HR and Food logged",
                                Toast.LENGTH_LONG).show();
                    } else if(mAfterHeartRate == Integer.MIN_VALUE){
                        //Send to database as result
                        Toast.makeText(getActivity(), "Result Logged",
                                Toast.LENGTH_LONG).show();
                        mBeforeHeartRate = result;
                        HeartRate beforeHR = new HeartRate("default@uci.edu",
                                mBeforeHeartRate, "RESULT");
                        vm_heart_rate.insert(beforeHR);
                    }
                    heartRateClick = false;
                }
            } else if(logfoodClick){
                    if (resultCode == RESULT_OK) {
                        String result = data.getStringExtra("category");
                        String result2 = data.getStringExtra("food");
                        mFood = result2;
                        mCategory = result;
                        logfoodClick = false;
                    }
            }
        }
        catch (Exception ex){
            Toast.makeText(getActivity(), ex.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    private void setHeartRate(int result) {
        Toast.makeText(getActivity(), "HeartRate Recieved:",
                Toast.LENGTH_LONG).show();
    }
}
