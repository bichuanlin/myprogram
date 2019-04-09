package cn.jx.jvtc.mytrafficclient.bean;

public class Bus {
    int id;
    int distance;//举例车站的距离，单位米
    int capacity;//容量
    int time;//到达车站的时间，单位分钟
   public  Bus(){}
    public Bus(int id, int distance, int capacity) {
        this.id = id;
        this.distance = distance;
        setDistance(distance);
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
        //计算时间
        if(distance>0){
            time=distance/((20*1000/60));//速度20公里每小时转换为米/分钟
        }
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;

    }
    public int getTime() {
        return time;
    }


}
