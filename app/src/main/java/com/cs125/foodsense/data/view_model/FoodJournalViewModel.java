package com.cs125.foodsense.data.view_model;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.cs125.foodsense.data.MyRepository;
import com.cs125.foodsense.data.entity.FoodJournal;

import java.util.List;

public class FoodJournalViewModel extends AndroidViewModel {
    private MyRepository repository;
    private LiveData<List<FoodJournal>> myFoodJournal;

    public FoodJournalViewModel(@NonNull Application application) {
        super(application);
        repository = new MyRepository(application);
    }

    public void insert(FoodJournal entry){
        repository.insertIntoFoodJournal(entry);
    }

    public void update(FoodJournal entry){
        repository.updateHRDiff(entry);
    }

    public LiveData<List<FoodJournal>> getMyFoodJournal(String userEmail){
        if (myFoodJournal == null){
            _initData(userEmail);
        }
        return myFoodJournal;
    }

    public void _initData(String userEmail){
        myFoodJournal = repository.getMyFoodJournal(userEmail);
    }
}
