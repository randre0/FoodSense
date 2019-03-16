package com.cs125.foodsense;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.cs125.foodsense.data.entity.FoodRegimen;
import com.cs125.foodsense.data.entity.User;
import com.cs125.foodsense.data.view_model.FoodRegimenViewModel;
import com.cs125.foodsense.data.view_model.MyViewModel;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import static android.app.Activity.RESULT_OK;

public class InputFoodActivity extends Fragment {

    private Spinner mFoodCategorySpinner;
    private Spinner mFoodItemsSpinner;
    private Button mButton;
    private String mCategory;
    private String mFood;
    private FoodRegimenViewModel vm_food_regimen;
    private LiveData<List<FoodRegimen>> mFoodRegimen;
    private HashMap<String, ArrayList<String>> mCategoryWithFoods;
    private ArrayList<String> foodCategory;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        final View myView = inflater.inflate(R.layout.activity_input_food, container, false);
        mCategoryWithFoods = new HashMap<String, ArrayList<String>>();
        foodCategory = new ArrayList<String>();
        vm_food_regimen = ViewModelProviders.of(this).get(FoodRegimenViewModel.class);
        mFoodCategorySpinner = (Spinner) myView.findViewById(R.id.food_category_spinner);
        mFoodItemsSpinner = (Spinner) myView.findViewById(R.id.food_list_spinner);
        mButton = (Button) myView.findViewById(R.id.submit_food_button);
        //Get LiveData<User>
        mFoodRegimen = vm_food_regimen.getAllFoodRegimen();

        //Set Observer to access data
        final Observer<List<FoodRegimen>> foodRegimenObserver =  new Observer<List<FoodRegimen>>() {
            @Override
            public void onChanged(@Nullable List<FoodRegimen> foodRegimenList) {
                for(FoodRegimen fr : foodRegimenList){
                    if(mCategoryWithFoods.containsKey(fr.foodType)){
                        mCategoryWithFoods.get(fr.foodType).add(fr.foodDesc);
                    }else{
                        mCategoryWithFoods.put(fr.foodType, new ArrayList<String>());
                        mCategoryWithFoods.get(fr.foodType).add(fr.foodDesc);
                    }
                }
                foodCategory.addAll(mCategoryWithFoods.keySet());
                Collections.sort(foodCategory);
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                        android.R.layout.simple_spinner_item, foodCategory);
                adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
                mFoodCategorySpinner.setAdapter(adapter);
            }
        };
        //Attach observer to LiveData<User>
        mFoodRegimen.observe(this, foodRegimenObserver);



        mFoodCategorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                String selectedCategory = (String)parent.getItemAtPosition(pos);
                mCategory = selectedCategory;
                ArrayList<String> test2 = mCategoryWithFoods.get(mCategory);
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity().getBaseContext(),
                        android.R.layout.simple_spinner_item, test2);
                adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
                mFoodItemsSpinner.setAdapter(adapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        mFoodItemsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                String selectedFood = (String)parent.getItemAtPosition(pos);
                mFood = selectedFood;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), HeartRateActivity.class);
                intent.putExtra("category", mCategory );
                intent.putExtra("food", mFood);
                FragmentManager mFragmentManager = getFragmentManager();
                FragmentTransaction mFragmentTransaction = mFragmentManager.beginTransaction();
                getTargetFragment().onActivityResult(getTargetRequestCode(), RESULT_OK, intent);
                mFragmentManager.popBackStackImmediate();
                mFragmentTransaction.commit();
            }
        });

        return myView;
    }
}
