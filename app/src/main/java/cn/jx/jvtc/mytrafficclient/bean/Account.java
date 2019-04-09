package cn.jx.jvtc.mytrafficclient.bean;

public class Account {
    String number;//编号
    int carIcon;//汽车品牌Logo
    String carNumber;//车牌
    int balance;//余额
    String pcardid;//身份证
    String name;//车主姓名
    boolean checked=false;//是否被选中
    public boolean isChecked() {
        return checked;
    }
    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPcardid() {
        return pcardid;
    }

    public void setPcardid(String pcardid) {
        this.pcardid = pcardid;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public int getCarIcon() {
        return carIcon;
    }
    public void setCarIcon(int carIcon) {
        this.carIcon = carIcon;
    }

    public String getCarNumber() {
        return carNumber;
    }
    public void setCarNumber(String carNumber) {
        this.carNumber = carNumber;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }
}
