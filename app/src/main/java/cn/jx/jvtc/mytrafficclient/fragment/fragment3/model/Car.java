package cn.jx.jvtc.mytrafficclient.fragment.fragment3.model;

/**
 * 车辆信息
 */
public class Car {
    String carNumber;//小车车牌号
    String number;//小车编号
    String pcardid;//车主用户的身份证号
    String buydata;//购买日期
    String carbrand;//车辆品牌
    int peccancy=0;//违章次数，默认值为0
    String pcodes="";//违章代码，多个代码之间用”，“隔开
    public Car() {
    }

    public String getCarNumber() {
        return carNumber;
    }

    public void setCarNumber(String carNumber) {
        this.carNumber = carNumber;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getPcardid() {
        return pcardid;
    }

    public void setPcardid(String pcardid) {
        this.pcardid = pcardid;
    }

    public String getBuydata() {
        return buydata;
    }

    public void setBuydata(String buydata) {
        this.buydata = buydata;
    }

    public String getCarbrand() {
        return carbrand;
    }

    public void setCarbrand(String carbrand) {
        this.carbrand = carbrand;
    }

    public int getPeccancy() {
        return peccancy;
    }

    public void setPeccancy(int peccancy) {
        this.peccancy = peccancy;
    }

    public String getPcodes() {
        return pcodes;
    }

    public void setPcodes(String pcodes) {
        this.pcodes = pcodes;
    }
}
