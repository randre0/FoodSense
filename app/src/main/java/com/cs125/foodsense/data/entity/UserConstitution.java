package com.cs125.foodsense.data.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity (tableName = "dt_user_constitution")
public class UserConstitution {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name="user_email")
    private String userEmail;

    // Body Constitution
    @ColumnInfo(name="constitution")
    private String bodyConstitution;

    @ColumnInfo(name = "hep_hits")
    public int hepHits; // Hepatomia
    @ColumnInfo(name = "cho_hits")
    public int choHits; // Cholecystonia
    @ColumnInfo(name = "pan_hits")
    public int panHits; // Pancreotonia
    @ColumnInfo(name = "gas_hits")
    public int gasHits; // Gastrotonia
    @ColumnInfo(name = "pul_hits")
    public int pulHits; // Pulmontonia
    @ColumnInfo(name = "col_hits")
    public int colHits; // Colonotonia
    @ColumnInfo(name = "ren_hits")
    public int renHits; // Renotonia
    @ColumnInfo(name = "ves_hits")
    public int vesHits; // Vescicotonia

    public UserConstitution(String userEmail) {
        this.userEmail = userEmail;
        this.bodyConstitution = "";
        this.hepHits = 0;
        this.choHits = 0;
        this.panHits = 0;
        this.gasHits = 0;
        this.pulHits = 0;
        this.colHits = 0;
        this.renHits = 0;
        this.vesHits = 0;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getBodyConstitution() {
        return bodyConstitution;
    }

    public void setBodyConstitution(String bodyConstitution) {
        this.bodyConstitution = bodyConstitution;
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
                ", bodyConstitution='" + bodyConstitution + '\'' +
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
