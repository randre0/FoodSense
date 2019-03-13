package com.cs125.foodsense.data.view_model;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.cs125.foodsense.data.MyRepository;
import com.cs125.foodsense.data.entity.FoodRegimen;

import java.util.List;

public class FoodRegimenViewModel extends AndroidViewModel {
    private MyRepository repository;
    private LiveData<List<FoodRegimen>> allFoodRegimen;


    public FoodRegimenViewModel(@NonNull Application application) {
        super(application);
        repository = new MyRepository(application);
    }
    /* FOOD REGIMEN */
    public void insert(FoodRegimen fr){
        repository.insertFoodRegimen(fr);
    }

    public void initData(){
        allFoodRegimen = repository.getAllRegimen();
    }

    public LiveData<List<FoodRegimen>> getAllFoodRegimen(){
        if (allFoodRegimen == null){
            initData();
        }
        return allFoodRegimen;
    }
    /*
    public void delete(FoodRegimen fr){
        repository(fr);
    }
    */
}
