package com.github.noteapp;

import java.util.Date;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "note_table")
public class Note {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String tittle;
    private String maincontent;
    private String date;

    public Note(String tittle, String maincontent, String date) {
        this.tittle = tittle;
        this.maincontent = maincontent;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTittle() {
        return tittle;
    }

    public void setTittle(String tittle) {
        this.tittle = tittle;
    }

    public String getMaincontent() {
        return maincontent;
    }

    public void setMaincontent(String maincontent) {
        this.maincontent = maincontent;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
