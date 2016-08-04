package com.twiscode.kubisadmin.POJO;

/**
 * Created by Crusader on 8/2/2016.
 */
public class Product {
    private String pro_title;
    private String pro_desc;
    private int pro_upvotes;
    private String pro_img;

    public Product(String pro_title, String pro_desc, int pro_upvotes, String pro_img) {
        this.pro_title = pro_title;
        this.pro_desc = pro_desc;
        this.pro_upvotes = pro_upvotes;
        this.pro_img = pro_img;
    }

    public String getPro_title() {
        return pro_title;
    }

    public void setPro_title(String pro_title) {
        this.pro_title = pro_title;
    }

    public String getPro_desc() {
        return pro_desc;
    }

    public void setPro_desc(String pro_desc) {
        this.pro_desc = pro_desc;
    }

    public int getPro_upvotes() {
        return pro_upvotes;
    }

    public void setPro_upvotes(int pro_upvotes) {
        this.pro_upvotes = pro_upvotes;
    }

    public String getPro_img() {
        return pro_img;
    }

    public void setPro_img(String pro_img) {
        this.pro_img = pro_img;
    }
}
