package com.cs125.foodsense.data.entity;


import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

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

    // Constructor
    public FoodJournal(String userEmail, String food) {
        this.id = 0;
        this.userEmail = userEmail;
        this.food = food;
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
    }

    @Override
    public String toString() {
        return "FoodJournal{" +
                "userEmail='" + userEmail + '\'' +
                ", food='" + food + '\'' +
               // ", timeEaten='" + timeEaten + '\'' +
                ", hrDiff='" + hrDiff + '\'' +
               // ", timestamp='" + timestamp + '\'' +
                '}';
    }
}
