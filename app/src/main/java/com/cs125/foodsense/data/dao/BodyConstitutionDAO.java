package com.cs125.foodsense.data.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.cs125.foodsense.data.entity.BodyConstitution;

import java.util.List;

@Dao
public interface BodyConstitutionDAO {
    @Insert
    public void insert(BodyConstitution bc);

    @Query("SELECT * FROM dt_body_constitution")
    public List<BodyConstitution> getAll();
}
