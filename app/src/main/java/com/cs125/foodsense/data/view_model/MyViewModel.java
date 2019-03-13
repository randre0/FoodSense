package com.cs125.foodsense.data.view_model;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import android.util.Log;

import com.cs125.foodsense.data.MyRepository;
import com.cs125.foodsense.data.entity.User;

/*
- Responsible for preparing/managing data for activity/fragment
- handles communication of activity/fragment w/ rest of application
 */
public class MyViewModel extends AndroidViewModel {
    private MyRepository repository;
    private LiveData<User> user;

    public MyViewModel(@NonNull Application application) {
        super(application);
        repository = new MyRepository(application);
    }

    /* FOOD REGIMEN */
    public void insert(@NonNull User user){
        Log.d("MyViewModel (User)", "Inserting user...");
        repository.insertUser(user);
    }

    public void update(User user){
        Log.d("MyViewModel (User)", "Inserting user...");
        repository.updateUser(user);
    }

    public void initData(String email){
        user = repository.getUser(email);
    }

    public LiveData<User> getUser(String email){
        if (user == null){
            initData(email);
        }
        return user;
    }



}