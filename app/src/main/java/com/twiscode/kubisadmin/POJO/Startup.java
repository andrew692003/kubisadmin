package com.twiscode.kubisadmin.POJO;

import org.parceler.Parcel;

import java.util.ArrayList;

/**
 * Created by Crusader on 7/29/2016.
 */
@Parcel
public class Startup {
    String creatorId,type,linkUrl,name,thumbnail,description;
    Boolean status,isDisplayed;
    ArrayList<String> founder,hashtag,imageUrl,upvoters;
    Long timestamp;

    public Startup(String creatorId,String type, String description,String linkUrl,String name, ArrayList<String> founder, ArrayList<String> hashtag, ArrayList<String> imageUrl, ArrayList<String> upvoters, Boolean status, Boolean isDisplayed, Long timestamp, String thumbnail)
    {
        this.creatorId=creatorId;
        this.type=type;
        this.description=description;
        this.linkUrl=linkUrl;
        this.name=name;
        this.founder=founder;
        this.hashtag=hashtag;
        this.imageUrl=imageUrl;
        this.upvoters=upvoters;
        this.status=status;
        this.isDisplayed=isDisplayed;
        this.timestamp=timestamp;
        this.thumbnail=thumbnail;
    }
    public Startup()
    {

    }

    public void setCreatorId(String creatorId) {
        this.creatorId = creatorId;
    }

    public String getCreatorId() {
        return creatorId;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setLinkUrl(String linkUrl) {
        this.linkUrl = linkUrl;
    }

    public String getLinkUrl() {
        return linkUrl;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setFounder(ArrayList<String> founder) {
        this.founder = founder;
    }

    public ArrayList<String> getFounder() {
        return founder;
    }

    public void setHashtag(ArrayList<String> hashtag) {
        this.hashtag = hashtag;
    }

    public ArrayList<String> getHashtag() {
        return hashtag;
    }

    public void setImageUrl(ArrayList<String> imageUrl) {
        this.imageUrl = imageUrl;
    }

    public ArrayList<String> getImageUrl() {
        return imageUrl;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setUpvoters(ArrayList<String> upvoters) {
        this.upvoters = upvoters;
    }

    public ArrayList<String> getUpvoters() {
        return upvoters;
    }

    public void setDisplayed(Boolean isDisplayed) {
        this.isDisplayed = isDisplayed;
    }

    public Boolean getDisplayed() {
        return isDisplayed;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getThumbnail() {
        return thumbnail;
    }
}
