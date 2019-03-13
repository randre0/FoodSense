package com.cs125.foodsense.data.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.support.annotation.NonNull;

import com.cs125.foodsense.data.entity.HeartRate;

import java.util.Date;
import java.util.List;

@Dao
public interface HeartRateDAO {

    @Insert
    public void insert(@NonNull HeartRate hr);

    @Query("SELECT * FROM dt_heart_rate;")
    public LiveData<List<HeartRate>> getAllHR();

    @Query("SELECT * FROM dt_heart_rate " +
            "WHERE email =:email " +
            "ORDER BY timestamp DESC")
    public LiveData<List<HeartRate>> getAllHRByUser(String email);

    @Query("SELECT * FROM dt_heart_rate " +
            "WHERE email =:email " +
            "AND datetime(timestamp, :duration)" +
            "ORDER BY timestamp DESC")
    public LiveData<List<HeartRate>> getAllHRByUser(String email, String duration);

}
