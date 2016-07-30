package com.twiscode.kubisadmin.POJO;

/**
 * Created by Crusader on 7/29/2016.
 */
public class User {

    String uid, username, name, imageUrl, description;
    boolean admin;

    public User() {
    }

    public User(String uid, String username, String name, String imageUrl, String description, boolean admin) {
        this.uid = uid;
        this.username = username;
        this.name = name;
        this.imageUrl = imageUrl;
        this.description = description;
        this.admin = admin;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
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

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }
}
