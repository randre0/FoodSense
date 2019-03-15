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

    // Insert entry
    @Insert
    public void insert(@NonNull HeartRate hr);

    // Return table of all heart rates
    @Query("SELECT * FROM dt_heart_rate;")
    public LiveData<List<HeartRate>> getAllHR();

    // Return table of all heart rates for specified user
    @Query("SELECT * FROM dt_heart_rate " +
            "WHERE email =:email " +
            "ORDER BY datetime(timestamp) DESC;")
    public LiveData<List<HeartRate>> getAllHRByUser(String email);

    // Return heart rates by user and in the time frame (order from most recent to least recent)
    @Query("SELECT * FROM dt_heart_rate " +
            "WHERE email =:email " +
            "AND datetime(timestamp, :duration) " +
            "ORDER BY datetime(timestamp) DESC;")
    public LiveData<List<HeartRate>> getAllHRByUserDuration(String email, String duration);

}
