package com.cs125.foodsense.data.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "dt_food_regimen")
public class FoodRegimen {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "pk_food_id")
    public int foodId;

    // Food Detail
    @ColumnInfo(name = "food_desc")
    public String foodDesc;
    @ColumnInfo(name = "food_type")
    public String foodType;

    // Body Constitution
    @ColumnInfo(name = "for_hep")
    public int forHep; // Hepatomia
    @ColumnInfo(name = "for_cho")
    public int forCho; // Cholecystonia
    @ColumnInfo(name = "for_pan")
    public int forPan; // Pancreotonia
    @ColumnInfo(name = "for_gas")
    public int forGas; // Gastrotonia
    @ColumnInfo(name = "for_pul")
    public int forPul; // Pulmontonia
    @ColumnInfo(name = "for_col")
    public int forCol; // Colonotonia
    @ColumnInfo(name = "for_ren")
    public int forRen; // Renotonia
    @ColumnInfo(name = "for_ves")
    public int forVes; // Vescicotonia

    public FoodRegimen(String foodDesc, String foodType, int forHep, int forCho, int forPan, int forGas, int forPul, int forCol, int forRen, int forVes) {
        this.foodDesc = foodDesc;
        this.foodType = foodType;
        this.forHep = forHep;
        this.forCho = forCho;
        this.forPan = forPan;
        this.forGas = forGas;
        this.forPul = forPul;
        this.forCol = forCol;
        this.forRen = forRen;
        this.forVes = forVes;
    }

    public int getFoodId() {
        return foodId;
    }

    public void setFoodId(int foodId) {
        this.foodId = foodId;
    }

    public String getFoodDesc() {
        return foodDesc;
    }

    public void setFoodDesc(String foodDesc) {
        this.foodDesc = foodDesc;
    }

    public String getFoodType() {
        return foodType;
    }

    public void setFoodType(String foodType) {
        this.foodType = foodType;
    }

    public void setForHep(int forHep) {
        this.forHep = forHep;
    }

    public void setForCho(int forCho) {
        this.forCho = forCho;
    }

    public void setForPan(int forPan) {
        this.forPan = forPan;
    }

    public void setForGas(int forGas) {
        this.forGas = forGas;
    }

    public void setForPul(int forPul) {
        this.forPul = forPul;
    }

    public void setForCol(int forCol) {
        this.forCol = forCol;
    }

    public void setForRen(int forRen) {
        this.forRen = forRen;
    }

    public void setForVes(int forVes) {
        this.forVes = forVes;
    }

    public int getForHep(){
        return this.forHep;
    }

    public int getForCho(){
        return this.forCho;
    }

    public int getForPan(){
        return this.forPan;
    }

    public int getForGas(){
        return this.forGas;
    }

    public int getForPul(){
        return this.forPul;
    }

    public int getForCol(){
        return this.forCol;
    }

    public int getForRen(){
        return this.forRen;
    }

    public int getForVes(){
        return this.forVes;
    }

    @Override
    public String toString(){
        return "Food Regimen {" +
                "id='" + this.foodId +'\'' +
                ", desc='" + this.foodDesc + '\'' +
                ", type='" + this.foodType + '\'' +
                ", hep='" + this.forHep + '\'' +
                ", cho='" + this.forCho + '\'' +
                ", pan='" + this.forPan + '\'' +
                ", gas='" + this.forGas + '\'' +
                ", pul='" + this.forPul + '\'' +
                ", col='" + this.forCol + '\'' +
                ", ren='" + this.forRen + '\'' +
                ", ves='" + this.forVes+ '\'' +
                '}';
    }
}
