package cn.jx.jvtc.mytrafficclient.fragment.fragment3.model;

import android.util.Log;

import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 车辆违章记录
 */
public class CarPeccancy {
    private static final String TAG = "CarPeccancy";
    String carnumber;//车牌号
    String pcode;//违章代码
    String paddr;//违章地址
    Date datetime;//违章时间
    public CarPeccancy() {
    }

    public String getCarnumber() {
        return carnumber;
    }

    public void setCarnumber(String carnumber) {
        this.carnumber = carnumber;
    }

    public String getPcode() {
        return pcode;
    }

    public void setPcode(String pcode) {
        this.pcode = pcode;
    }

    public String getPaddr() {
        return paddr;
    }

    public void setPaddr(String paddr) {
        this.paddr = paddr;
    }

    public Date getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        //2016-06-06 07:19:21
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            date = format.parse(datetime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
       // Log.d(TAG, "setDatetime: "+datetime+":"+date);
        this.datetime = date;
    }
}
