package cn.jx.jvtc.mytrafficclient.bean;

public class RecordBean {
    String carNum,reMoney,balance,person;
    String date,week,reTime;
    public RecordBean() {
    }

    public RecordBean(String carNum, String reMoney, String balance, String person, String date, String week, String reTime) {
        this.carNum = carNum;
        this.reMoney = reMoney;
        this.balance = balance;
        this.person = person;
        this.date = date;
        this.week = week;
        this.reTime = reTime;
    }

    public String getCarNum() {
        return carNum;
    }

    public void setCarNum(String carNum) {
        this.carNum = carNum;
    }

    public String getReMoney() {
        return reMoney;
    }

    public void setReMoney(String reMoney) {
        this.reMoney = reMoney;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getPerson() {
        return person;
    }

    public void setPerson(String person) {
        this.person = person;
    }


    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
    }

    public String getReTime() {
        return reTime;
    }

    public void setReTime(String reTime) {
        this.reTime = reTime;
    }
}
