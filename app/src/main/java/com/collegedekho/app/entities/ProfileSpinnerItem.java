package com.collegedekho.app.entities;

/**
 * Created by sureshsaini on 24/5/16.
 */
public class ProfileSpinnerItem {

    private int id;
    private String name;
    private boolean isSelected;

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
}
