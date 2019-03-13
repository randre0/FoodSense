package com.cs125.foodsense.data.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "dt_body_constitution")
public class BodyConstitution {
    @PrimaryKey(autoGenerate = true)
    private int id;

    private String constitution_desc;
    private String code;

    public BodyConstitution(String constitution_desc, String code) {
        this.id = 0;
        this.constitution_desc = constitution_desc;
        this.code = code;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getConstitution_desc() {
        return constitution_desc;
    }

    public void setConstitution_desc(String constitution_desc) {
        this.constitution_desc = constitution_desc;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "BodyConstitution{" +
                ", constitution_desc='" + constitution_desc + '\'' +
                ", code='" + code + '\'' +
                '}';
    }
}
