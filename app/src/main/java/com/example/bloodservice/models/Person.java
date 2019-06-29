package com.example.bloodservice.models;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "person")
public class Person {
    @PrimaryKey(autoGenerate = true)
    private  int id;
    @ColumnInfo(name ="name")
    private String name;
    @ColumnInfo(name ="number")
    private String number;
    @ColumnInfo(name ="notes")
    private String notes;
    @ColumnInfo(name ="bloodType")
    private String bloodType;
    @ColumnInfo(name ="lastDate")
    private String lastDate;


    public Person(int id, String name, String number, String notes, String bloodType, String lastDate) {
        this.id = id;
        this.name = name;
        this.number = number;
        this.notes = notes;
        this.bloodType = bloodType;
        this.lastDate = lastDate;
    }

    @Ignore
    public Person(String name, String number, String notes, String bloodType, String lastDate) {
        this.name = name;
        this.number = number;
        this.notes = notes;
        this.bloodType = bloodType;
        this.lastDate = lastDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getBloodType() {
        return bloodType;
    }

    public void setBloodType(String bloodType) {
        this.bloodType = bloodType;
    }

    public String getLastDate() {
        return lastDate;
    }

    public void setLastDate(String lastDate) {
        this.lastDate = lastDate;
    }
}
