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

    @Query("SELECT " +
          "(EXISTS(" +
          "SELECT 1 FROM dt_user_constitution " +
          "WHERE user_email =:email)) as isInTable;")
    public int isInTable(String email);   // 0 if none, > 0 if does

    @Query("UPDATE dt_user_constitution " +
            "SET hep_hits = hep_hits+:hepHit, cho_hits =cho_hits+:choHit, pan_hits =pan_hits+:panHits, gas_hits=gas_hits+:gasHits, pul_hits=pul_hits+:pulHits, col_hits=col_hits+:colHits, ren_hits=ren_hits+:renHits, ves_hits=ves_hits+:vesHits " +
            "WHERE user_email=:userEmail; ")
    public void updateHits(String userEmail, int hepHit, int choHit, int panHits, int gasHits, int pulHits, int colHits, int renHits, int vesHits);
    // pass in (+/-) 1

    @Query("UPDATE dt_user_constitution " +
            "SET constitution=:constitution " +
            "WHERE user_email=:userEmail; ")
    public void updateConstitution(String userEmail, String constitution);


    @Query("SELECT * FROM dt_user_constitution " +
            "WHERE user_email =:userEmail " +
            "LIMIT 1;")
    public UserConstitution getByUser(String userEmail);
}