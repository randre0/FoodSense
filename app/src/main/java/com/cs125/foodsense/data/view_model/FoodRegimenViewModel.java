package com.cs125.foodsense.data.view_model;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.cs125.foodsense.data.MyRepository;
import com.cs125.foodsense.data.dao.FoodJournalDAO;
import com.cs125.foodsense.data.dao.FoodRegimenDAO;
import com.cs125.foodsense.data.entity.FoodRegimen;

import java.util.List;

public class FoodRegimenViewModel extends AndroidViewModel {
    private MyRepository repository;
    private FoodRegimenDAO foodRegDao;
    private LiveData<List<FoodRegimen>> allFoodRegimen;


    public FoodRegimenViewModel(@NonNull Application application) {
        super(application);
        repository = new MyRepository(application);
    }

    public void insert(FoodRegimen fr){
        repository.insertFoodRegimen(fr);
    }

    public LiveData<List<FoodRegimen>> getAllFoodRegimen(){
        return repository.getAllFoodRegimen();
    }
}
