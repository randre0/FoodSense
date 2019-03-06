package com.cs125.foodsense;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;


public class LogFoodFragment extends Fragment {

    private ImageButton mLogHRButton;
    private ImageButton mLogFoodButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_log_food, container, false);

        mLogHRButton = (ImageButton) v.findViewById(R.id.log_heart_rate);
        mLogFoodButton = v.findViewById(R.id.log_food);

        mLogHRButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(getActivity(), HeartRateActivity.class);
                getActivity().startActivity(intent);
            }
        });
        return v;
    }

}
