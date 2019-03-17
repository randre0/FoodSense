package com.cs125.foodsense.data.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.cs125.foodsense.data.entity.User;

@Dao
public interface UserDAO {
    @Insert
    public void insert(User user);

    @Query("UPDATE dt_user " +
            "SET first_name =:name, age =:age, height =:height, weight =:weight, gender =:gender " +
            "WHERE pk_email =:email")
    public void updateUser(String email, String name, int age, int height, double weight, String gender);

    @Query("UPDATE dt_user " +
            "SET body_constitution =:constitution " +
            "WHERE pk_email =:email;")
    public void updateConstitution(String email, String constitution);

    @Query("SELECT " +
            "(EXISTS(" +
            "SELECT 1 FROM dt_user " +
            "WHERE pk_email =:email)) as isInTable;")
    public int isInTable(String email);   // 0 if none, > 0 if does

    @Query("SELECT * FROM dt_user " +
            "WHERE pk_email = :email ")
    public LiveData<User> getUserInfo(String email);

    @Query("SELECT * FROM dt_user " +
            "WHERE pk_email = :email ")
    public User getUserInfoStatic(String email);
}
