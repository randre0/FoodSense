package com.cs125.foodsense.data.view_model;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import android.util.Log;

import com.cs125.foodsense.data.MyRepository;
import com.cs125.foodsense.data.entity.HeartRate;

import java.util.List;

public class HeartRateViewModel extends AndroidViewModel {
    private MyRepository repository;
    private LiveData<List<HeartRate>> hrData;

    public HeartRateViewModel(@NonNull Application application) {
        super(application);
        repository = new MyRepository(application);
    }

    /* FOOD REGIMEN */
    public void insert(@NonNull HeartRate hr){
        try {
            Log.d("HeartRateViewModel", "Inserting heart rate... " + hr);
            repository.insertHeartRate(hr);
        }
        catch (Exception e){

        }
    }

    public LiveData<List<HeartRate>> getHRData(String email){
        return repository.getAllHeartRateByUser(email);
    }

}
