package com.cs125.foodsense.data.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.cs125.foodsense.data.entity.FoodRegimen;

import java.util.List;

@Dao
public interface FoodRegimenDAO {
    @Insert
    public void insert(FoodRegimen foodReg);

    @Update
    public void update(FoodRegimen foodReg);

    // Fetch list of FoodRegimen entities (aka table)
    @Query("SELECT * FROM dt_food_regimen")
    public LiveData<List<FoodRegimen>> getFoodReg();

    // Return 1 if food in table, else 0
    @Query("SELECT " +
            "(EXISTS(" +
            "SELECT 1 FROM dt_food_regimen " +
            "WHERE food_desc =:food_desc)) as isInTable;")
    public int isInTable(String food_desc);   // 0 if none, > 0 if does

    // Fetch list of food types
    @Query("SELECT DISTINCT food_type FROM dt_food_regimen")
    public List<String> getFoodType();

    // Fetch list of food in category food_type
    @Query("SELECT food_desc FROM dt_food_regimen WHERE food_type =:foodType")
    public List<String> getFoodByFoodType(String foodType);

    public class FoodRecommendation {
        @ColumnInfo(name="food_desc")
        private String foodDesc;
        @ColumnInfo(name="food_type")
        private String foodType;

        public String getFoodDesc() {
            return foodDesc;
        }

        public void setFoodDesc(String foodDesc) {
            this.foodDesc = foodDesc;
        }

        public String getFoodType() {
            return foodType;
        }

        public void setFoodType(String foodType) {
            this.foodType = foodType;
        }

        @Override
        public String toString() {
            return "FoodRecommendation{" +
                    "foodDesc='" + foodDesc + '\'' +
                    ", foodType='" + foodType + '\'' +
                    '}';
        }
    }

    // Fetch recommendation for body type ---need to check/test jessica night
    @Query("SELECT food_desc, food_type " +
            "FROM dt_food_regimen " +
            "WHERE :body_constitution_var > 0 ")
    public List<FoodRecommendation> getRecommendationByConstitution(String body_constitution_var); // ex. "for_hep"

    @Delete
    public void deleteFoodRegimen(FoodRegimen DAO);



}

