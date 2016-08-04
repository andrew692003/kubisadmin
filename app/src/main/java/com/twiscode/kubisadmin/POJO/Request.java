package com.twiscode.kubisadmin.POJO;

/**
 * Created by Crusader on 8/1/2016.
 */
public class Request {
    String userid,deskripsi,nominateid;
    Long status;

    Request(String userid, String deskripsi,String nominateid, Long status)
    {
        this.userid=userid;
        this.deskripsi=deskripsi;
        this.status=status;
        this.nominateid=nominateid;
    }
    Request(){}

    public void setStatus(Long status) {
        this.status = status;
    }

    public Long getStatus() {
        return status;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getUserid() {
        return userid;
    }

    public void setNominateid(String nominateid) {
        this.nominateid = nominateid;
    }

    public String getNominateid() {
        return nominateid;
    }
}
