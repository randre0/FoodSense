package com.cs125.foodsense.data.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.cs125.foodsense.data.entity.UserConstitution;

import java.util.List;

@Dao
public interface UserConstitutionDAO {
    @Insert
    public void insert(UserConstitution bc);

    @Query("SELECT * FROM dt_user_constitution " +
            "WHERE user_email =:userEmail")
    public List<UserConstitution> getByUser(String userEmail);
}