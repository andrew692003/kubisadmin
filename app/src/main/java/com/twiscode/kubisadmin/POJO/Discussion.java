package com.twiscode.kubisadmin.POJO;

/**
 * Created by Crusader on 8/2/2016.
 */
public class Discussion {

    String uid, sid, comment;
    long timestamp;
    int messageType;

    public Discussion() {
    }

    public Discussion(String uid, String sid, String comment, long timestamp, int messageType) {
        this.uid = uid;
        this.sid = sid;
        this.comment = comment;
        this.timestamp = timestamp;
        this.messageType = messageType;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public int getMessageType() {
        return messageType;
    }

    public void setMessageType(int messageType) {
        this.messageType = messageType;
    }

}
