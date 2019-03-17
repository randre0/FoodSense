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

    // Insert FoodJournal entry
    @Insert
    public void insert(FoodJournal foodJournal);

    // Update FoodJournal entry
    @Update
    public void update(FoodJournal foodJournal);

    // Update FoodJournal entry with heart rate difference and classify food entry
    @Query("UPDATE dt_food_journal " +
            "SET hr_diff=:hrDiff, food_classification=:foodClassification " +
            "WHERE fk_user_email =:userEmail " +
            "AND food_desc =:food")
    public void updateHrDiff(String userEmail, String food, double hrDiff, String foodClassification);

    // Return table of FoodJournal entries for specified user
    @Query("SELECT * FROM dt_food_journal WHERE fk_user_email =:userEmail")
    public LiveData<List<FoodJournal>> getFoodJournalByUser(String userEmail);

    // Return table of FoodJournal entries for specified user in the durations specified (most recent to lease recent)
    @Query("SELECT * FROM dt_food_journal " +
            "WHERE fk_user_email =:userEmail " +
            "AND datetime(time_eaten, :duration)" +
            "ORDER BY datetime(time_eaten) DESC")
    public LiveData<List<FoodJournal>> getFoodJournalByUserAndDuration(String userEmail, String duration); // duration - "-1 min", "-24 hour", "2 day"


    // Return specified number of FoodJournal entries for specified user (most recent to lease recent)
    @Query("SELECT * FROM dt_food_journal " +
            "WHERE fk_user_email =:userEmail " +
            "ORDER BY datetime(time_eaten) DESC " +
            "LIMIT :meals")
    public LiveData<List<FoodJournal>> getFoodJournalByUserAndMeals(String userEmail, int meals); // get last ? of meals

    // USE for USER CONSTITUTION
    @Query("SELECT * FROM dt_food_journal " +
            "WHERE fk_user_email =:userEmail " +
            "ORDER BY datetime(time_eaten) DESC;")
    public LiveData<List<FoodJournal>> getFoodJournalByFood(String userEmail);

    @Query("SELECT * FROM dt_food_journal " +
            "WHERE fk_user_email =:userEmail " +
            "AND food_desc =:food " +
            "ORDER BY datetime(time_eaten) DESC " +
            "LIMIT 2;")
    public FoodJournal getMostRecentByFood(String userEmail, String food);
    // if return 1 it's new
    // if return 2 then compare most recent to past and see if there are changes

}

