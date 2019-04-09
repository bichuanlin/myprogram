package cn.jx.jvtc.mytrafficclient.fragment.fragment3.model;

import java.util.Comparator;

/**
 * 违章代码信息
 */
public class Peccancy {
    String pcode;
    int pmoney;//罚金
    int pscore;//扣分
    String premarks;//描述
    int peccancyTotal=0;//违章总次数
    public Peccancy() {
    }

    public String getPcode() {
        return pcode;
    }

    public void setPcode(String pcode) {
        this.pcode = pcode;
    }

    public int getPmoney() {
        return pmoney;
    }

    public void setPmoney(int pmoney) {
        this.pmoney = pmoney;
    }

    public int getPscore() {
        return pscore;
    }

    public void setPscore(int pscore) {
        this.pscore = pscore;
    }

    public String getPremarks() {
        return premarks;
    }

    public void setPremarks(String premarks) {
        this.premarks = premarks;
    }

    public int getPeccancyTotal() {
        return peccancyTotal;
    }

    public void setPeccancyTotal(int peccancyTotal) {
        this.peccancyTotal = peccancyTotal;
    }
    public static class OrderPeccancyCountNoDesc implements Comparator<Peccancy> {
        @Override
        public int compare(Peccancy lhs, Peccancy rhs) {//
            return rhs.getPeccancyTotal()-lhs.getPeccancyTotal();

        }
    }

}
