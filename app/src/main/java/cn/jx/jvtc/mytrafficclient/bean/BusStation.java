package cn.jx.jvtc.mytrafficclient.bean;

import java.util.List;

/**
 * 公交车站信息
 */
public class BusStation {
    String name;//名称
    int id;//编号
    List<Bus> busList;//站台公交信息
   public  BusStation(){}
    public BusStation(int id,String name, List<Bus> busList) {
        this.name = name;
        this.id = id;
        this.busList = busList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Bus> getBusList() {
        return busList;
    }

    public void setBusList(List<Bus> busList) {
        this.busList = busList;
    }
}
