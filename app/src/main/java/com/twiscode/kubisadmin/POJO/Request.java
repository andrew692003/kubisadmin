package com.twiscode.kubisadmin.POJO;

/**
 * Created by Crusader on 8/1/2016.
 */
public class Request {
    String userid,deskripsi;
    int status;

    Request(String userid, String deskripsi, int status)
    {
        this.userid=userid;
        this.deskripsi=deskripsi;
        this.status=status;
    }
    Request(){}

    public void setStatus(int status) {
        this.status = status;
    }

    public int getStatus() {
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
}
