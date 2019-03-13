package com.cs125.foodsense.data.view_model;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.cs125.foodsense.data.MyRepository;
import com.cs125.foodsense.data.dao.FoodJournalDAO;
import com.cs125.foodsense.data.entity.FoodJournal;
import com.cs125.foodsense.data.entity.UserConstitution;

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
        return repository.getMyFoodJournal(userEmail);
    }

    public LiveData<List<FoodJournal>> getMyFoodJournalByDuration(String userEmail, String duration){
        return repository.getMyFoodJournalByDuration(userEmail, duration);
    }

    public LiveData<List<FoodJournal>> getMyFoodJournalByNumberOfMeals(String userEmail, int meals){
        return repository.getMyFoodJournalByNumberOfMeals(userEmail, meals);
    }


}
