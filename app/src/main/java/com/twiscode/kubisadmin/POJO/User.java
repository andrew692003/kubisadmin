package com.twiscode.kubisadmin.POJO;

/**
 * Created by Crusader on 7/29/2016.
 */
public class User {

    String uid, username, name, imageUrl, description;
    Long status;

    public User() {
    }

    public User(String uid, String username, String name, String imageUrl, String description, Long status) {
        this.uid = uid;
        this.username = username;
        this.name = name;
        this.imageUrl = imageUrl;
        this.description = description;
        this.status = status;
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

    public Long getStatus() {
        return status;
    }

    public void setStatus(Long status) {
        this.status = status;
    }
}
