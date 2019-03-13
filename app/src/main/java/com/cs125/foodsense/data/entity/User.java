package com.cs125.foodsense.data.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;


@Entity (tableName = "dt_user")
public class User {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "pk_email")
    private String email;
    @ColumnInfo(name = "first_name")
    private String firstName;
    @ColumnInfo(name = "age")
    private int age;
    @ColumnInfo(name = "weight")
    private double weight;  // in pounds
    @ColumnInfo(name = "height")
    private int height;     // in inches

    public User(@NonNull String email, @NonNull String firstName, int age, int height, double weight) {
        this.email = email;
        this.firstName = firstName;
        this.age = age;
        this.weight = weight;
        this.height = height;
    }

    // GETTER
    public String getEmail() {
        return email;
    }

    public String getFirstName() {
        return firstName;
    }

    public int getAge() {
        return age;
    }

    public double getWeight() {
        return weight;
    }

    public int getHeight() {
        return height;
    }

    // SETTER
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    @Override
    public String toString() {
        return "User{" +
                "email='" + email + '\'' +
                ", firstName='" + firstName + '\'' +
                ", age=" + age +
                ", weight=" + weight +
                ", height=" + height +
                '}';
    }
}