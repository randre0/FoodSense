package com.cs125.foodsense.data.entity;


import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.cs125.foodsense.data.util.Converters;
import com.cs125.foodsense.data.util.Utility;

import java.sql.Date;
import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity(tableName = "dt_food_journal")
public class FoodJournal {
    // Fields
    @PrimaryKey(autoGenerate = true)    // don't need for this table but android requires primary key
    @ColumnInfo(name="id")
    public int id;

    @ColumnInfo(name = "fk_user_email")
    private String userEmail;

    @ColumnInfo(name = "food_desc")
    private String food;

    @Nullable
    @ColumnInfo(name="hr_diff")
    private double hrDiff;

    @Nullable
    @ColumnInfo(name="food_classification")
    private String foodClassification;

    @Nullable
    @TypeConverters({Converters.class})
    @ColumnInfo(name="time_eaten")
    private String timeEaten;

    @Nullable
    @TypeConverters({Converters.class})
    @ColumnInfo(name="timestamp")
    private String timestamp;

    // Constructor
    public FoodJournal(String userEmail, String food, String timeEaten) {
        this.id = 0;
        this.userEmail = userEmail;
        this.food = food;
        this.timeEaten = timeEaten;
        this.timestamp = Converters.toDateString(Utility.getCurrentDateTime());
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getFood() {
        return food;
    }

    public void setFood(String food) {
        this.food = food;
    }

    public double getHrDiff() {
        return hrDiff;
    }

    public void setHrDiff(double hrDiff) {
        this.hrDiff = hrDiff;
        if (hrDiff < 2){
            setFoodClassification("GOOD");
        }
        else if (hrDiff >= 2 && hrDiff < 4){
            setFoodClassification("NEUTRAL");
        }
        else {
            setFoodClassification("BAD");
        }
    }

    @Nullable
    public String getFoodClassification() {
        return foodClassification;
    }

    public void setFoodClassification(@Nullable String foodClassification) {
        this.foodClassification = foodClassification;
    }

    @Nullable
    public String getTimeEaten() {
        return timeEaten;
    }

    public void setTimeEaten(@Nullable String timeEaten) {
        this.timeEaten = timeEaten;
    }

    @Nullable
    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(@Nullable String timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "FoodJournal{" +
                "food='" + food + '\'' +
                ", hrDiff=" + hrDiff +
                ", foodClassification='" + foodClassification + '\'' +
                ", timeEaten='" + timeEaten + '\'' +
                '}';
    }
}
