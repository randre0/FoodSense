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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
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


        submit_Button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                age = Integer.valueOf(mEditAge.getText().toString());
                height = Integer.valueOf(mEditHeight.getText().toString());
                weight = Integer.valueOf(mEditWeight.getText().toString());
                email = mEditEmail.getText().toString();
                updateUserProfile(age, height, weight, "female");
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

    /********************** FOR YOU GUYS TO USE **********************************/
    // add  viewmodel to activity/fragments that you need
    private FoodRegimenViewModel vm_foodRegimen;
    private FoodJournalViewModel vm_foodJournal;
    private HeartRateViewModel vm_heartRate;
    private MyViewModel vm_user;
    private HealthStateViewModel vm_healthState;

    private User USER;
    private final String USER_EMAIL = "default@uci.edu";
    private final String NAME = "default";


    private void init(){
        vm_user = ViewModelProviders.of(this).get(MyViewModel.class);
        vm_heartRate = ViewModelProviders.of(this).get(HeartRateViewModel.class);
        vm_foodRegimen = ViewModelProviders.of(this).get(FoodRegimenViewModel.class);
        vm_foodJournal = ViewModelProviders.of(this).get(FoodJournalViewModel.class);
        vm_healthState = ViewModelProviders.of(this).get(HealthStateViewModel.class);

        login(this.USER_EMAIL, NAME, 0, 0, 0, "Female");
        updateUserProfile(16, 30, 30.5, "female");
        insertHeartRate(this.USER_EMAIL, 60, "BEFORE");
        LocalDateTime timeEaten = Utility.getCurrentDateTime();
        insertInFoodJournal(this.USER_EMAIL, "beef", timeEaten);
       // testVm_healthstate();
    }

    private void regimenObserve(){
        vm_foodRegimen.getAllFoodRegimen().observe(this, new Observer<List<FoodRegimen>>(){
            @Override
            public void onChanged(@Nullable List<FoodRegimen> fr){
                // Update RecycleView
                Toast.makeText(MainActivity.this, "onChanged", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void userObserve(){
        vm_user.getUser(this.USER_EMAIL).observe(this, new Observer<User>(){
            @Override
            public void onChanged(@Nullable User fr){
                // Update RecycleView
                Toast.makeText(MainActivity.this, "onChanged", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void heartRateObserve(){
        vm_heartRate.getHrDataByUser(this.USER_EMAIL).observe(this, new Observer<List<HeartRate>>(){
            @Override
            public void onChanged(@Nullable List<HeartRate> hrList){
                // Update RecycleView
                Toast.makeText(MainActivity.this, "onChanged", Toast.LENGTH_SHORT).show();
            }
        });
    }



    private void insertHeartRate(String userEmail, int heartRate, String tag){
        HeartRate hr = new HeartRate(userEmail, heartRate, tag);
        hr.setEmail(userEmail);
        hr.setHeartRate(heartRate);
        hr.setTag(tag);
        hr.setId(0);
        if (hr != null){
            vm_heartRate.insert(new HeartRate(userEmail, heartRate, tag));
        }
    }

    // ** Logs in user if account exists, if non exist- one is created for the user
    private void login(String email, String name, int age, int height, double weight, String gender){   // for kayvaan
        try{
            USER = new User(email, name, age, height, weight, gender);
            vm_user.insertIfNotExist(USER);

            UserConstitution uc = new UserConstitution(this.USER_EMAIL);
            vm_healthState.insertIfNotExist(uc);
            //UserConstitution uc = vm_healthState.getUserConst(userEmail);
            uc.setColHits(1);
            uc.setHepHits(3);
            vm_healthState.updateUserHitChanges(uc);
            uc.setBodyConstitution("HPE");
            vm_healthState.updateConstitution(uc);

        }
        catch (Exception e){
            Log.d("MainActivity", "Failed to login/register user");
        }
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

    private void insertInFoodJournal(String userEmail, String food, LocalDateTime timeEaten){
        FoodJournal foodEntry = new FoodJournal(userEmail, food, Converters.toDateString(timeEaten));
        vm_foodJournal.insert(foodEntry);
        foodEntry.setHrDiff(15);
        vm_foodJournal.update(foodEntry);

    }

}
