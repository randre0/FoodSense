package com.cs125.foodsense.data.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;


@Entity (tableName = "dt_user")
public class User {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "pk_email")
    private String email;
    @Nullable
    @ColumnInfo(name = "first_name")
    private String firstName;
    @Nullable
    @ColumnInfo(name = "age")
    private int age;
    @Nullable
    @ColumnInfo(name = "weight")
    private double weight;  // in pounds
    @ColumnInfo(name = "height")
    @Nullable
    private int height;     // in inches
    @ColumnInfo(name="gender")
    private String gender;
    @Nullable
    @ColumnInfo(name="body_constitution")
    private String constitution;

    public User(@NonNull String email){
        this.email = email;
        this.firstName = "";
        this.age = 0;
        this.weight = 0;
        this.height = 0;
        this.gender = "";
    }

//    public User(@NonNull String email, @NonNull String firstName, int age, int height, double weight, String gender) {
//        this.email = email;
//        this.firstName = firstName;
//        this.age = age;
//        this.weight = weight;
//        this.height = height;
//        this.gender = gender;
//    }

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

    public String getGender() {
        return gender;
    }

    // SETTER
    public void setEmail(@NonNull String email) {
        this.email = email;
    }

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

    public void setGender(String gender) {
        this.gender = gender;
    }

    @Nullable
    public String getConstitution() {
        return constitution;
    }

    public void setConstitution(String constitution) {
        this.constitution = constitution;
    }

    @Override
    public String toString() {
        return "User{" +
                "email='" + email + '\'' +
                ", firstName='" + firstName + '\'' +
                ", age=" + age +
                ", weight=" + weight +
                ", height=" + height +
                ", gender='" + gender + '\'' +
                ", constitution='" + constitution + '\'' +
                '}';
    }
}