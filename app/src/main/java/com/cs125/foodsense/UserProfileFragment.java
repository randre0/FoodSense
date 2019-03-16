package com.cs125.foodsense;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.cs125.foodsense.data.entity.User;
import com.cs125.foodsense.data.view_model.MyViewModel;

import java.time.LocalDateTime;
import java.util.List;

public class UserProfileFragment extends Fragment {

    Button mSubmitButton;
    EditText mNameText;
    EditText mGenderText;
    EditText mAgeText;
    EditText mHeightText;
    EditText mWeightText;
    LiveData<User> mUser;
    private MyViewModel vm_user;
    private User USER;
    private final String USER_EMAIL = "default@uci.edu";
    private final String NAME = "default";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        vm_user = ViewModelProviders.of(this).get(MyViewModel.class);
        View v = inflater.inflate(R.layout.fragment_user_profile, container, false);

        mSubmitButton = v.findViewById(R.id.user_profile_submit_button);
        mNameText = v.findViewById(R.id.user_profile_name);
        mGenderText = v.findViewById(R.id.user_profile_gender);
        mAgeText = v.findViewById(R.id.user_profile_age);
        mHeightText = v.findViewById(R.id.user_profile_height);
        mWeightText = v.findViewById(R.id.user_profile_weight);

        //Get LiveData<User>
        mUser = vm_user.getUser(USER_EMAIL);

        //Set Observer to access data
        final Observer<User> userObserver =  new Observer<User>() {
            @Override
            public void onChanged(@Nullable User user) {
                if(user != null) {
                    USER = user;
                    mNameText.setText(user.getEmail());
                    mGenderText.setText(user.getGender());
                    mAgeText.setText(String.valueOf(user.getAge()));
                    mHeightText.setText(String.valueOf(user.getHeight()));
                    mWeightText.setText(Double.toString(user.getWeight()));
                }
            }
        };
        //Attach observer to LiveData<User>
        mUser.observe(this, userObserver);




        mSubmitButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                updateUserProfile(Integer.valueOf(mAgeText.getText().toString()),
                        Integer.valueOf(mHeightText.getText().toString()),
                        Double.valueOf(mWeightText.getText().toString()),
                        mGenderText.getText().toString());
            }
        });
        return v;
    }


    // ** Update any changes to age, height, weight, and age for user
    private void updateUserProfile(int age, int height, double weight, String gender){
        Log.d("Main Activity", "Starting update for user profile");
        if (USER.getAge() != age){
            USER.setAge(age);
            Log.d("User", "Set age to " + age);
        }
        if (USER.getHeight() != height){
            USER.setHeight(height);
            Log.d("User", "Set height to " + height);
        }
        if (USER.getWeight() != weight){
            USER.setWeight(weight);
            Log.d("User", "Set weight to " + weight);
        }
        if (USER.getGender() != gender) {
            USER.setGender(gender);
            Log.d("USER", "Set gender to " + gender);
        }
        vm_user.update(USER);
    }

}