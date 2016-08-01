package com.twiscode.kubisadmin.POJO;

/**
 * Created by Crusader on 8/1/2016.
 */
public class Request {
    String userid,deskripsi;
    Long status;

    Request(String userid, String deskripsi, Long status)
    {
        this.userid=userid;
        this.deskripsi=deskripsi;
        this.status=status;
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
}
