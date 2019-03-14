package com.cs125.foodsense;

import android.content.Intent;
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

import java.util.ArrayList;
import java.util.Arrays;

import static android.app.Activity.RESULT_OK;

public class InputFoodActivity extends Fragment {

    private Spinner mFoodCategorySpinner;
    private Spinner mFoodItemsSpinner;
    private Button mButton;
    private String mCategory;
    private String mFood;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View myView = inflater.inflate(R.layout.activity_input_food, container, false);

        mFoodCategorySpinner = (Spinner) myView.findViewById(R.id.food_category_spinner);
        mFoodItemsSpinner = (Spinner) myView.findViewById(R.id.food_list_spinner);
        mButton = (Button) myView.findViewById(R.id.submit_food_button);
        String[] test = {"Food1", "Food2", "Food3"};
        ArrayList<String> test2 = new ArrayList<String>(Arrays.asList(test));
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item, test2);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        mFoodCategorySpinner.setAdapter(adapter);
        mFoodCategorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                String selectedCategory = (String)parent.getItemAtPosition(pos);
                mCategory = selectedCategory;
                String[] test;
                switch(selectedCategory){
                    case"Food1":
                        test = new String[] {"1", "1", "1"};
                        break;
                    case "Food2":
                        test = new String[] {"2", "2", "2"};
                        break;
                    default:
                        test =  new String[] {"3", "3", "3"};
                        break;
                }
                ArrayList<String> test2 = new ArrayList<String>(Arrays.asList(test));
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
