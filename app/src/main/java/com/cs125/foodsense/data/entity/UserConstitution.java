package com.cs125.foodsense.data.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Entity (tableName = "dt_user_constitution")
public class UserConstitution {
    // Fields
    @PrimaryKey(autoGenerate = true)    // don't need for this table but android requires primary key
    @ColumnInfo(name="id")
    public int id;

    @ColumnInfo(name="user_email")
    private String userEmail;

    @ColumnInfo(name="food_desc")
    private String food;

    @ColumnInfo(name = "hep_hits")
    private int hepHits; // Hepatomia
    @ColumnInfo(name = "cho_hits")
    private int choHits; // Cholecystonia
    @ColumnInfo(name = "pan_hits")
    private int panHits; // Pancreotonia
    @ColumnInfo(name = "gas_hits")
    private int gasHits; // Gastrotonia
    @ColumnInfo(name = "pul_hits")
    private int pulHits; // Pulmontonia
    @ColumnInfo(name = "col_hits")
    private int colHits; // Colonotonia
    @ColumnInfo(name = "ren_hits")
    private int renHits; // Renotonia
    @ColumnInfo(name = "ves_hits")
    private int vesHits; // Vescicotonia

    public UserConstitution(String userEmail, String food) {
        this.userEmail = userEmail;
        this.food = food;
        this.hepHits = 0;
        this.choHits = 0;
        this.panHits = 0;
        this.gasHits = 0;
        this.pulHits = 0;
        this.colHits = 0;
        this.renHits = 0;
        this.vesHits = 0;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFood() {
        return food;
    }

    public void setFood(String food) {
        this.food = food;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public int getHepHits() {
        return hepHits;
    }

    public void setHepHits(int hepHits) {
        this.hepHits = hepHits;
    }

    public int getChoHits() {
        return choHits;
    }

    public void setChoHits(int choHits) {
        this.choHits = choHits;
    }

    public int getPanHits() {
        return panHits;
    }

    public void setPanHits(int panHits) {
        this.panHits = panHits;
    }

    public int getGasHits() {
        return gasHits;
    }

    public void setGasHits(int gasHits) {
        this.gasHits = gasHits;
    }

    public int getPulHits() {
        return pulHits;
    }

    public void setPulHits(int pulHits) {
        this.pulHits = pulHits;
    }

    public int getColHits() {
        return colHits;
    }

    public void setColHits(int colHits) {
        this.colHits = colHits;
    }

    public int getRenHits() {
        return renHits;
    }

    public void setRenHits(int renHits) {
        this.renHits = renHits;
    }

    public int getVesHits() {
        return vesHits;
    }

    public void setVesHits(int vesHits) {
        this.vesHits = vesHits;
    }

    @Override
    public String toString() {
        return "UserConstitution{" +
                ", userEmail='" + userEmail + '\'' +
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
