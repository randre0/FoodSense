package com.cs125.foodsense.data.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.cs125.foodsense.data.entity.UserConstitution;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Dao
public interface UserConstitutionDAO {
    // Insert entry
    @Insert
    public void insert(UserConstitution bc);

    // Check if entry for email/food exists
    @Query("SELECT " +
            "(EXISTS(" +
            "SELECT 1 FROM dt_user_constitution " +
            "WHERE user_email =:email AND food_desc =:food)) as isInTable;")
    public int isInTable(String email, String food);   // 0 if none, > 0 if does

    // Update entry
    @Query("UPDATE dt_user_constitution " +
            "SET hep_hits = :hepHit, cho_hits =:choHit, pan_hits =:panHits, gas_hits=:gasHits, pul_hits=:pulHits, col_hits=:colHits, ren_hits=:renHits, ves_hits=:vesHits " +
            "WHERE user_email=:userEmail " +
            "AND food_desc =:food ")
    public void updateHits(String userEmail, String food, int hepHit, int choHit, int panHits, int gasHits, int pulHits, int colHits, int renHits, int vesHits);
                                            // pass in (+/-) 1
                                            //SET hep_hits = hep_hits+:hepHit, cho_hits =cho_hits+:choHit, pan_hits =pan_hits+:panHits, gas_hits=gas_hits+:gasHits, pul_hits=pul_hits+:pulHits, col_hits=col_hits+:colHits, ren_hits=ren_hits+:renHits, ves_hits=ves_hits+:vesHits " +

    // Return user's user constitution
    @Query("SELECT * FROM dt_user_constitution " +
            "WHERE user_email =:userEmail " +
            "LIMIT 1;")
    public LiveData<UserConstitution> getByUser(String userEmail);

    // Return user's sum of hits for all the food for user
    @Query("SELECT user_email as userEmail, SUM(hep_hits) as hepHits, SUM(cho_hits) as choHits, " +
            "SUM(pan_hits) as panHits, SUM(gas_hits) as gasHits, SUM(pul_hits) as pulHits, " +
            "SUM(col_hits) as colHits, SUM(ren_hits) as renHits, SUM(ves_hits) as vesHits " +
            "FROM dt_user_constitution " +
            "WHERE user_email =:userEmail " +
            "LIMIT 1;")
    public UserOverallHits getByUserOverallHits(String userEmail);
    //"FROM (SELECT DISTINCT * FROM dt_user_constitution WHERE user_email =:userEmail ORDER BY id DESC) " +


    public class UserOverallHits {
        private String userEmail;
        private int hepHits; // Hepatomia
        private int choHits; // Cholecystonia
        private int panHits; // Pancreotonia
        private int gasHits; // Gastrotonia
        private int pulHits; // Pulmontonia
        private int colHits; // Colonotonia
        private int renHits; // Renotonia
        private int vesHits; // Vescicotonia

        public String getUserEmail() {
            return userEmail;
        }

        public void setUserEmail(String userEmail) {
            this.userEmail = userEmail;
        }

        public void setHepHits(int hepHits) {
            this.hepHits = hepHits;
        }

        public void setChoHits(int choHits) {
            this.choHits = choHits;
        }

        public void setPanHits(int panHits) {
            this.panHits = panHits;
        }

        public void setGasHits(int gasHits) {
            this.gasHits = gasHits;
        }

        public void setPulHits(int pulHits) {
            this.pulHits = pulHits;
        }

        public void setColHits(int colHits) {
            this.colHits = colHits;
        }

        public void setRenHits(int renHits) {
            this.renHits = renHits;
        }

        public void setVesHits(int vesHits) {
            this.vesHits = vesHits;
        }

        public int getHepHits() {
            return hepHits;
        }

        public int getChoHits() {
            return choHits;
        }

        public int getPanHits() {
            return panHits;
        }

        public int getGasHits() {
            return gasHits;
        }

        public int getPulHits() {
            return pulHits;
        }

        public int getColHits() {
            return colHits;
        }

        public int getRenHits() {
            return renHits;
        }

        public int getVesHits() {
            return vesHits;
        }

        public int getMaxHits(){
            List<Integer> hitList = new ArrayList<Integer>();
            hitList.add(this.hepHits);
            hitList.add(this.choHits);
            hitList.add(this.panHits);
            hitList.add(this.gasHits);
            hitList.add(this.pulHits);
            hitList.add(this.colHits);
            hitList.add(this.renHits);
            hitList.add(this.vesHits);
            int maxHit = Collections.max(hitList);
            return maxHit;
        }

        @Override
        public String toString() {
            return "UserOverallHits{" +
                    "userEmail='" + userEmail + '\'' +
                    ", hepHits=" + hepHits +
                    ", choHits=" + choHits +
                    ", panHits=" + panHits +
                    ", gasHits=" + gasHits +
                    ", pulHits=" + pulHits +
                    ", colHits=" + colHits +
                    ", renHits=" + renHits +
                    ", vesHits=" + vesHits +
                    '}';
        }
    }
}