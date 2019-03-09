package com.cs125.foodsense;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    Button submit_Button;
    EditText mEditAge, mEditHeight, mEditWeight, mEditEmail;
    int age, height, weight;
    String email, gender;

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


        submit_Button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                age = Integer.valueOf(mEditAge.getText().toString());
                height = Integer.valueOf(mEditHeight.getText().toString());
                weight = Integer.valueOf(mEditWeight.getText().toString());
                email = mEditEmail.getText().toString();

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
}
