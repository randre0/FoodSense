package com.cs125.foodsense;

import android.app.Activity;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.cs125.foodsense.data.entity.FoodRegimen;
import com.cs125.foodsense.data.entity.User;
import com.cs125.foodsense.data.entity.UserConstitution;
import com.cs125.foodsense.data.view_model.FoodRegimenViewModel;
import com.cs125.foodsense.data.view_model.HealthStateViewModel;
import com.cs125.foodsense.data.view_model.MyViewModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class FoodRecActivity extends AppCompatActivity {

    private final String USER_EMAIL = "default@uci.edu";
    private HealthStateViewModel vm_health_state;
    private MyViewModel vm_user;
    private FoodRegimenViewModel vm_food_regimen;
    private LiveData<User> mUser;   // Note to Rymmy from Jessica: getting Constitution from User instead of UserConstitution
    private LiveData<List<FoodRegimen>> mFoodRegimen;
    private HashMap<String, ArrayList<String>> mCategoryWithFoods;
    private ArrayList<String> foodCategory;
    private String userConst;
    private Spinner mFoodCategorySpinner;
    private Spinner mFoodItemsSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_rec);
        mCategoryWithFoods = new HashMap<String, ArrayList<String>>();
        foodCategory = new ArrayList<String>();
        vm_health_state = ViewModelProviders.of(this).get(HealthStateViewModel.class);
        vm_food_regimen = ViewModelProviders.of(this).get(FoodRegimenViewModel.class);
        mUser = vm_user.getUser(USER_EMAIL);    // Note to Rymmy: Jessia changed this
        mFoodCategorySpinner = (Spinner) findViewById(R.id.food_category_spin);
        mFoodItemsSpinner = (Spinner) findViewById(R.id.food_category_spin2);

        final Observer<User> observer = new Observer<User>() {  // Note to Rymmy: Jessica changed from UserConstitution to User
            @Override
            public void onChanged(@Nullable User user) {
                TextView userConstText = findViewById(R.id.user_constitution_text);
                userConst = user.getConstitution();
                userConstText.setText("Your Body constitution is " +
                        user.getConstitution()
                        + "\nHere are your food recommendations");
            }
        };
        mUser.observe(this, observer);

        mFoodRegimen = vm_food_regimen.getAllFoodRegimen();

        //Set Observer to access data
        final Observer<List<FoodRegimen>> foodRegimenObserver =  new Observer<List<FoodRegimen>>() {
            @Override
            public void onChanged(@Nullable List<FoodRegimen> foodRegimenList) {
                for(FoodRegimen fr : foodRegimenList){
                    if(fr.getHits(userConst) >= 0) {
                        Log.d("Const","UserConst " + userConst);
                        if (mCategoryWithFoods.containsKey(fr.foodType)) {
                            mCategoryWithFoods.get(fr.foodType).add(fr.foodDesc);
                        } else {
                            mCategoryWithFoods.put(fr.foodType, new ArrayList<String>());
                            mCategoryWithFoods.get(fr.foodType).add(fr.foodDesc);
                        }
                    }
                }

                foodCategory.addAll(mCategoryWithFoods.keySet());
                Collections.sort(foodCategory);
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(FoodRecActivity.this,
                        android.R.layout.simple_spinner_item, foodCategory);
                adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
                mFoodCategorySpinner.setAdapter(adapter);
            }
        };
        mFoodRegimen.observe(this, foodRegimenObserver);


        mFoodCategorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                String selectedCategory = (String)parent.getItemAtPosition(pos);
                String mCategory = selectedCategory;
                ArrayList<String> test2 = mCategoryWithFoods.get(mCategory);
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getBaseContext(),
                        android.R.layout.simple_spinner_item, test2);
                adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
                mFoodItemsSpinner.setAdapter(adapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
}
