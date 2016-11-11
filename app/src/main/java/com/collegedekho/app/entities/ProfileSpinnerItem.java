package com.collegedekho.app.entities;

/**
 * Created by sureshsaini on 24/5/16.
 */
public class ProfileSpinnerItem {

    private int id;
    private String name;
    private boolean isSelected;
    private int institutes_count;

    public ProfileSpinnerItem(){
        // required empty constructor
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

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }

    public int getInstitutes_count() {
        return institutes_count;
    }

    public void setInstitutes_count(int institutes_count) {
        this.institutes_count = institutes_count;
    }
}
