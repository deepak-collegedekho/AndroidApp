package com.collegedekho.app.entities;

/**
 * @author Mayank Gautam
 *         Created: 04/07/15
 */
public class User {
    private String resource_uri;
    private String email;
    private String username;
    private String name;
    private String image;
    private String token;
    private boolean is_anony;
    private Prefs pref;

    public Prefs getPref() {
        return pref;
    }

    public void setPref(Prefs pref) {
        this.pref = pref;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public boolean getIs_anony() {
        return is_anony;
    }

    public void setIs_anony(boolean is_anony) {
        this.is_anony = is_anony;
    }

    public String getResource_uri() {
        return resource_uri;
    }

    public void setResource_uri(String resource_uri) {
        this.resource_uri = resource_uri;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public enum Prefs {
        NOT_SURE, STREAMKNOWN
    }
}
