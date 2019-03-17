package com.cs125.foodsense;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.cs125.foodsense.data.entity.BodyConstitution;
import com.cs125.foodsense.data.entity.FoodJournal;
import com.cs125.foodsense.data.entity.FoodRegimen;
import com.cs125.foodsense.data.entity.HeartRate;
import com.cs125.foodsense.data.entity.User;
import com.cs125.foodsense.data.entity.UserConstitution;
import com.cs125.foodsense.data.view_model.FoodJournalViewModel;
import com.cs125.foodsense.data.view_model.FoodRegimenViewModel;
import com.cs125.foodsense.data.view_model.HealthStateViewModel;
import com.cs125.foodsense.data.view_model.HeartRateViewModel;
import com.cs125.foodsense.data.view_model.MyViewModel;
import com.cs125.foodsense.data.util.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.time.LocalDateTime;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    Button submit_Button;
    EditText mEditAge, mEditHeight, mEditWeight, mEditEmail;
    int age, height, weight;
    String email, gender;

    private User USER;
    private final String USER_EMAIL = "default@uci.edu";

    // add  viewmodel to activity/fragments that you need
    private FoodJournalViewModel vm_foodJournal;
    private HeartRateViewModel vm_heartRate;
    private MyViewModel vm_user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        submit_Button = (Button)findViewById(R.id.button1);
        mEditAge = (EditText) findViewById(R.id.edittext);
        mEditHeight   = (EditText)findViewById(R.id.edittext2);
        mEditWeight   = (EditText)findViewById(R.id.edittext3);
        mEditEmail = (EditText)findViewById(R.id.edittext);

        Spinner spinner = findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                                    R.array.genders, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        // Fetches User if if registered before
        vm_user = ViewModelProviders.of(this).get(MyViewModel.class);
        LiveData<User> u = vm_user.getUser(USER_EMAIL);
        final Observer<User> observer = new Observer<User>() {
            @Override
            public void onChanged(@Nullable User user) {
                USER = user;
            }
        };
        u.observe(this, observer);

        submit_Button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                age = Integer.valueOf(mEditAge.getText().toString());
                height = Integer.valueOf(mEditHeight.getText().toString());
                weight = Integer.valueOf(mEditWeight.getText().toString());
                email = mEditEmail.getText().toString();

                login(USER_EMAIL);  // login/register
                if (USER != null){  // If account exists, update profile
                    updateUserProfile(USER, age, height, weight);
                }
                goToHome(view);
            }
        });
            }

    public void goToHome(View view) {
        Intent homeActivity = new Intent(MainActivity.this, MenuActivity.class);
        startActivity(homeActivity);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        gender = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    /* ------------------------ HELPER FUNCTIONS  --------------------------------------*/
    // ** Logs in user if account exists, if non exist- one is created for the user
    private void login(String email){   // for kayvaan
        try{
            USER = new User(email);
            vm_user.insertIfNotExist(USER);
            Log.d("MainActivity", "Login/register user");
        }
        catch (Exception e){
            Log.d("MainActivity", "Failed to login/register user");
        }
    }

    // ** Update any changes to age, height, weight, and age for user
    private void updateUserProfile(User user, int age, int height, double weight){
        Log.d("Main Activity", "Starting update for user profile");
        if (user.getAge() != age){
            user.setAge(age);
            Log.d("User", "Set age to " + age);
        }
        if (user.getHeight() != height){
            user.setHeight(height);
            Log.d("User", "Set height to " + height);
        }
        if (user.getWeight() != weight){
            user.setWeight(weight);
            Log.d("User", "Set weight to " + weight);
        }
        if (user.getGender() != gender) {
            user.setGender(gender);
            Log.d("USER", "Set gender to " + gender);
        }
        vm_user.update(user);
    }

    /********************** FOR YOU GUYS TO USE **********************************/

    public void test(){
        vm_heartRate = ViewModelProviders.of(this).get(HeartRateViewModel.class);
        vm_foodJournal = ViewModelProviders.of(this).get(FoodJournalViewModel.class);

        _testInsertHeartRate(this.USER_EMAIL, 60, "BEFORE");
        _testInsertHeartRate(this.USER_EMAIL, 65, "AFTER");
        LocalDateTime timeEaten = Utility.getCurrentDateTime();
        _testInsertInFoodJournal(this.USER_EMAIL, "Butter", timeEaten);
        Log.d("MainActivity", "inserted into food journla");
    }

    private void _testInsertHeartRate(String userEmail, int heartRate, String tag){
        HeartRate hr = new HeartRate(userEmail, heartRate, tag);
        hr.setEmail(userEmail);
        hr.setHeartRate(heartRate);
        hr.setTag(tag);
        hr.setId(0);
        if (hr != null){
            vm_heartRate.insert(new HeartRate(userEmail, heartRate, tag));
        }
    }

    private void _testInsertInFoodJournal(String userEmail, String food, LocalDateTime timeEaten){
        FoodJournal foodEntry = new FoodJournal(userEmail, food, Converters.toDateString(timeEaten));
        vm_foodJournal.insert(foodEntry);
        foodEntry.setHrDiff(0);
        vm_foodJournal.update(foodEntry);
    }
}
