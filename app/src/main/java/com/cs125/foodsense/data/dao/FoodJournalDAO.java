package com.cs125.foodsense.data.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.cs125.foodsense.data.entity.FoodJournal;

import java.util.List;

@Dao
public interface FoodJournalDAO {

    @Insert
    public void insert(FoodJournal foodJournal);

    @Update
    public void update(FoodJournal foodJournal);

    @Query("UPDATE dt_food_journal " +
            "SET hr_diff=:hrDiff " +
            "WHERE fk_user_email =:userEmail " +
            "AND food_desc =:food")
    public void updateHrDiff(String userEmail, String food, double hrDiff);


    @Query("SELECT * FROM dt_food_journal WHERE fk_user_email =:userEmail")
    public LiveData<List<FoodJournal>> getFoodJournalByUser(String userEmail);

    @Query("SELECT * FROM dt_food_journal " +
            "WHERE fk_user_email =:userEmail " +
            "AND datetime(time_eaten, :duration)" +
            "ORDER BY datetime(time_eaten) ASC")
    public LiveData<List<FoodJournal>> getFoodJournalByUserAndDuration(String userEmail, String duration);
    // duration - "-1 min", "-24 hour", "2 day"

    @Query("SELECT * FROM dt_food_journal " +
            "WHERE fk_user_email =:userEmail " +
            "ORDER BY datetime(time_eaten) DESC " +
            "LIMIT :meals")
    public LiveData<List<FoodJournal>> getFoodJournalByUserAndMeals(String userEmail, int meals);
    // get last ? of meals




}
