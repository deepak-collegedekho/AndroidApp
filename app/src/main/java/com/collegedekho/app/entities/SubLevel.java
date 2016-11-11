package com.collegedekho.app.entities;

/**
 * Created by sureshsaini on 12/10/16.
 */

public class SubLevel {
    private  int id;
    private String name;
    private  int level;
    private  int institutes_count;

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

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getInstitutes_count() {
        return institutes_count;
    }

    public void setInstitutes_count(int institutes_count) {
        this.institutes_count = institutes_count;
    }
}
