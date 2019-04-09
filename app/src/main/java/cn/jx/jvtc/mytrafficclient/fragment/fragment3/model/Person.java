package cn.jx.jvtc.mytrafficclient.fragment.fragment3.model;

import android.util.Log;

import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 车主信息
 */
public class Person {
    String pname;
    String pcardid;
    String psex;
    String username;
    String ptel;
    Date pregistdate;
    int peccancy=0;//文章次数

    public Person() {
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getPcardid() {
        return pcardid;
    }

    public void setPcardid(String pcardid) {
        this.pcardid = pcardid;
    }

    public String getPsex() {
        return psex;
    }

    public void setPsex(String psex) {
        this.psex = psex;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPtel() {
        return ptel;
    }

    public void setPtel(String ptel) {
        this.ptel = ptel;
    }

    public Date getPregistdate() {
        return pregistdate;
    }

    public void setPregistdate(String pregistdate) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            date = format.parse(pregistdate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        this.pregistdate = date;
    }

    public int getPeccancy() {
        return peccancy;
    }

    public void setPeccancy(int peccancy) {
        this.peccancy = peccancy;
    }
}
