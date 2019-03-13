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

/*
// add  viewmodel to activity/fragments that you need
    private FoodRegimenViewModel vm_foodRegimen;
    private FoodJournalViewModel vm_foodJournal;
    private HeartRateViewModel vm_heartRate;
    private MyViewModel vm_user;

    private User USER;
    private final String USER_EMAIL = "default@uci.edu";
    private final String NAME = "default";


private void init(){
        vm_user = ViewModelProviders.of(this).get(MyViewModel.class);
        vm_heartRate = ViewModelProviders.of(this).get(HeartRateViewModel.class);
        vm_foodRegimen = ViewModelProviders.of(this).get(FoodRegimenViewModel.class);
        vm_foodJournal = ViewModelProviders.of(this).get(FoodJournalViewModel.class);

        login(this.USER_EMAIL, NAME, 0, 0, 0);
        updateUserProfile(16, 30, 30.5);
        readToDB_FoodRegimen();
        insertHeartRate(this.USER_EMAIL, 60, "BEFORE");
        insertInFoodJournal(this.USER_EMAIL, "beef");
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
        vm_heartRate.getHRData(this.USER_EMAIL).observe(this, new Observer<List<HeartRate>>(){
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
    private void login(String email, String name, int age, int height, double weight){   // for kayvaan
        LiveData<User> user;
        try{
            USER = new User(email, name, age, height, weight);
            Log.d("MainActivity", "Created user " + USER.getEmail());
            vm_user.insert(USER);
        }
        catch (Exception e){
            Log.d("MainActivity", "Failed to login user");
        }
    }

    // ** Update any changes to age, height, and weight for user
    private void updateUserProfile(int age, int height, double weight){
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
        vm_user.update(USER);
    }

    private LiveData<User> getUserProfile(String email){
        vm_user = ViewModelProviders.of(this).get(MyViewModel.class);
        vm_user.getUser(email).observe(this,new Observer<User>() {
            @Override
            public void onChanged(@Nullable User user){
                // Update Recycle View
            }
        });
        return vm_user.getUser(email);
    }

    private void insertInFoodJournal(String userEmail, String food){

        FoodJournal foodEntry = new FoodJournal(userEmail, food);
        try {
            foodEntry.setId(0);
            vm_foodJournal.insert(foodEntry);

            //Log.d("MainActivity", "Just inserted food entry to journal: " + foodEntry);
           // foodEntry.setHrDiff(15);
            //vm_foodJournal.update(foodEntry);
            //Log.d("MainActivity", "Just updated heart rate difference for food: " + foodEntry);
        }
        catch (Exception e){
            Log.d("MainActivity", "failed: " + foodEntry);
        }

    }

    private int toInt(String token){
        return Integer.parseInt(token.trim());
    }

    private void readToDB_FoodRegimen() {

        InputStream is = getResources().openRawResource(R.raw.regimen);
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(is, Charset.forName("UTF-8"))
        );
        String line;
        try {
            Log.d("MainActivity.readToDB_FoodRegimen()", "Starting to read regimen to DB...");
            while ((line = reader.readLine()) != null) {
                // Split
                String delimiter = ";";
                String[] tokens = line.split(delimiter);

                if (tokens.length > 9) {
                    // Read data
                    FoodRegimen foodReg = new FoodRegimen(tokens[0],
                                                            tokens[1],
                                                            toInt(tokens[2]),
                                                            toInt(tokens[3]),
                                                            toInt(tokens[4]),
                                                            toInt(tokens[5]),
                                                            toInt(tokens[6]),
                                                            toInt(tokens[7]),
                                                            toInt(tokens[8]),
                                                            toInt(tokens[9]));

                    foodReg.setFoodId(0);
                    vm_foodRegimen.insert(foodReg); // Insert if not exist in table
                }
            }
        } catch (IOException e) {
            Log.wtf("MainActivity", "Error reading csv data file");
            e.printStackTrace();
        }
    }

 */

//    private void init(){
//        vm_user = ViewModelProviders.of(this).get(MyViewModel.class);
//        vm_heartRate = ViewModelProviders.of(this).get(HeartRateViewModel.class);
//        vm_foodRegimen = ViewModelProviders.of(this).get(FoodRegimenViewModel.class);
//        vm_foodJournal = ViewModelProviders.of(this).get(FoodJournalViewModel.class);
//
//        login(this.USER_EMAIL, NAME, 0, 0, 0);
//        updateUserProfile(16, 30, 30.5);
//        readToDB_FoodRegimen();
//        insertHeartRate(this.USER_EMAIL, 60, "BEFORE");
//        insertInFoodJournal(this.USER_EMAIL, "beef");
//    }
//
//    private void regimenObserve(){
//        vm_foodRegimen.getAllFoodRegimen().observe(this, new Observer<List<FoodRegimen>>(){
//            @Override
//            public void onChanged(@Nullable List<FoodRegimen> fr){
//                // Update RecycleView
//                Toast.makeText(com.cs125.foodsense.ui.MainActivity.this, "onChanged", Toast.LENGTH_SHORT).show();
//            }
//        });
//    }
//
//    private void userObserve(){
//        vm_user.getUser(this.USER_EMAIL).observe(this, new Observer<User>(){
//            @Override
//            public void onChanged(@Nullable User fr){
//                // Update RecycleView
//                Toast.makeText(com.cs125.foodsense.ui.MainActivity.this, "onChanged", Toast.LENGTH_SHORT).show();
//            }
//        });
//    }
//
//    private void heartRateObserve(){
//        vm_heartRate.getHRData(this.USER_EMAIL).observe(this, new Observer<List<HeartRate>>(){
//            @Override
//            public void onChanged(@Nullable List<HeartRate> hrList){
//                // Update RecycleView
//                Toast.makeText(com.cs125.foodsense.ui.MainActivity.this, "onChanged", Toast.LENGTH_SHORT).show();
//            }
//        });
//    }
//
//    private void insertHeartRate(String userEmail, int heartRate, String tag){
//        HeartRate hr = new HeartRate(userEmail, heartRate, tag);
//        hr.setEmail(userEmail);
//        hr.setHeartRate(heartRate);
//        hr.setTag(tag);
//        hr.setId(0);
//        if (hr != null){
//            vm_heartRate.insert(new HeartRate(userEmail, heartRate, tag));
//
//        }
//    }
//
//    // ** Logs in user if account exists, if non exist- one is created for the user
//    private void login(String email, String name, int age, int height, double weight){   // for kayvaan
//        LiveData<User> user;
//        try{
//            USER = new User(email, name, age, height, weight);
//            Log.d("MainActivity", "Created user " + USER.getEmail());
//            vm_user.insert(USER);
//        }
//        catch (Exception e){
//            Log.d("MainActivity", "Failed to login user");
//        }
//    }
//
//    // ** Update any changes to age, height, and weight for user
//    private void updateUserProfile(int age, int height, double weight){
//        Log.d("Main Activity", "Starting update for user profile");
//        if (USER.getAge() != age){
//            USER.setAge(age);
//            Log.d("User", "Set age to " + age);
//        }
//        if (USER.getHeight() != height){
//            USER.setHeight(height);
//            Log.d("User", "Set height to " + height);
//        }
//        if (USER.getWeight() != weight){
//            USER.setWeight(weight);
//            Log.d("User", "Set weight to " + weight);
//        }
//        vm_user.update(USER);
//    }
//
//    private LiveData<User> getUserProfile(String email){
//        vm_user = ViewModelProviders.of(this).get(MyViewModel.class);
//        vm_user.getUser(email).observe(this,new Observer<User>() {
//            @Override
//            public void onChanged(@Nullable User user){
//                // Update Recycle View
//            }
//        });
//        return vm_user.getUser(email);
//    }
//
//    private void foodJournalVM_observer(){
//        vm_foodJournal = ViewModelProviders.of(this).get(FoodJournalViewModel.class);
//        vm_foodJournal.getMyFoodJournal(this.USER_EMAIL).observe(this,new Observer<List<FoodJournal>>() {
//            @Override
//            public void onChanged(@Nullable List<FoodJournal> fj){
//                // Update Recycle View
//            }
//        });
//    }
//    private void insertInFoodJournal(String userEmail, String food){
//
//        FoodJournal foodEntry = new FoodJournal(userEmail, food);
//        try {
//            foodEntry.setId(0);
//            vm_foodJournal.insert(foodEntry);
//
//            //Log.d("MainActivity", "Just inserted food entry to journal: " + foodEntry);
//            // foodEntry.setHrDiff(15);
//            //vm_foodJournal.update(foodEntry);
//            //Log.d("MainActivity", "Just updated heart rate difference for food: " + foodEntry);
//        }
//        catch (Exception e){
//            Log.d("MainActivity", "failed: " + foodEntry);
//        }
//
//    }
//
//    private int toInt(String token){
//        return Integer.parseInt(token.trim());
//    }
//
//    private void readToDB_FoodRegimen() {
//
//        InputStream is = getResources().openRawResource(R.raw.regimen);
//        BufferedReader reader = new BufferedReader(
//                new InputStreamReader(is, Charset.forName("UTF-8"))
//        );
//        String line;
//        try {
//            Log.d("MainActivity.readToDB_FoodRegimen()", "Starting to read regimen to DB...");
//            while ((line = reader.readLine()) != null) {
//                // Split
//                String delimiter = ";";
//                String[] tokens = line.split(delimiter);
//
//                if (tokens.length > 9) {
//                    // Read data
//                    FoodRegimen foodReg = new FoodRegimen(tokens[0],
//                            tokens[1],
//                            toInt(tokens[2]),
//                            toInt(tokens[3]),
//                            toInt(tokens[4]),
//                            toInt(tokens[5]),
//                            toInt(tokens[6]),
//                            toInt(tokens[7]),
//                            toInt(tokens[8]),
//                            toInt(tokens[9]));
//
//                    foodReg.setFoodId(0);
//                    vm_foodRegimen.insert(foodReg); // Insert if not exist in table
//                }
//            }
//        } catch (IOException e) {
//            Log.wtf("MainActivity", "Error reading csv data file");
//            e.printStackTrace();
//        }
//    }
//
//}
