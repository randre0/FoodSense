package com.cs125.foodsense.data.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverter;
import android.arch.persistence.room.TypeConverters;
import android.support.annotation.Nullable;

import com.cs125.foodsense.data.util.Converters;
import com.cs125.foodsense.data.util.Utility;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;


@Entity(tableName = "dt_heart_rate")
@TypeConverters({Converters.class})
public class HeartRate {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name="hr_id")
    private int id;

    @ColumnInfo(name="email")
    private String email;

    @ColumnInfo(name="heart_rate")
    private int heartRate;

    @ColumnInfo(name="tag")
    private String tag; // BEFORE, AFTER, RESTING
    @Nullable
    @TypeConverters({Converters.class})
    @ColumnInfo(name="timestamp")
    private String timestamp;

    public HeartRate(String email, int heartRate, String tag) {
        this.email = email;
        this.heartRate = heartRate;
        this.tag = tag;
       // this.timestamp = Converters.toDateString(Utility.getCurrentDateTime());
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getHeartRate() {
        return heartRate;
    }

    public void setHeartRate(int heartRate) {
        this.heartRate = heartRate;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
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
        return "HeartRate{" +
                "email='" + email + '\'' +
                ", heartRate=" + heartRate +
                ", tag='" + tag + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }
}
