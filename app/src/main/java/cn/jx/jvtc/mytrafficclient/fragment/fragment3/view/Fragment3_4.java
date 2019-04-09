package cn.jx.jvtc.mytrafficclient.fragment.fragment3.view;


import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;

import java.util.ArrayList;
import java.util.List;

import cn.jx.jvtc.mytrafficclient.R;
import cn.jx.jvtc.mytrafficclient.activity.MainActivity;
import cn.jx.jvtc.mytrafficclient.fragment.fragment3.model.Car;
import cn.jx.jvtc.mytrafficclient.fragment.fragment3.model.CarPeccancy;
import cn.jx.jvtc.mytrafficclient.fragment.fragment3.model.Person;

/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment3_4 extends Fragment {
    private static final String TAG = "Fragment3_4";
    private BarChart barChart;
    private BarDataSet barDataSet;
    private List<BarEntry> yVal;
    List<Car> carList;//所有车辆信息
    List<CarPeccancy> carPeccancyList;//车牌违章信息
    List<Person> personList;
    int person90,person80,person70,person60,person50;
    int peccancy90,peccancy80,peccancy70,peccancy60,peccancy50;//各年龄端违章人数
    float f90,f80,f70,f60,f50;
    float f90_p,f80_p,f70_p,f60_p,f50_p;
    public Fragment3_4() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        MainActivity mainActivity= (MainActivity) context;
        personList=mainActivity.getPersonList();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_fragment3_4, container, false);
        barChart=v.findViewById(R.id.barChat_fragment34);
        sum_age_count();

        initBarChart();
        return v;
    }
    private void initBarChart() {
        barChart.setDescription(null);
        barChart.setTouchEnabled(false);
        Legend legend = barChart.getLegend();
        legend.setEnabled(false);
        XAxis xAxis = barChart.getXAxis();
        xAxis.setDrawGridLines(false);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        YAxis yAxisRight=barChart.getAxisRight();
        yAxisRight.setTextSize(20f);
        yAxisRight.setDrawGridLines(false);
        barChart.setData(getBarData());
        barChart.invalidate();
    }
    private BarData getBarData() {
        barDataSet = new BarDataSet(getYVal(), "");
        List<Integer> colors = new ArrayList<>();
        colors.add(Color.rgb(106, 152, 0));//绿色
        colors.add(Color.rgb(235, 114, 8));//橙色
        barDataSet.setColors(colors);
        barDataSet.setBarSpacePercent(50f);
        barDataSet.setStackLabels(new String[]{"无违章", "有违章"});
        BarData barData = new BarData(getXVal(), barDataSet);
        barDataSet.setValueFormatter(new PercentFormatter());
        return barData;
    }

    private String[] getXVal() {
        String[] xVal = new String[]{"90后", "80后", "70后", "60后", "50后"};
        return xVal;
    }

    private List<BarEntry> getYVal() {
        yVal = new ArrayList<>();
        yVal.add(new BarEntry(new float[]{f90,f90_p }, 0));
        yVal.add(new BarEntry(new float[]{f80,f80_p }, 1));
        yVal.add(new BarEntry(new float[]{f70,f70_p}, 2));
        yVal.add(new BarEntry(new float[]{f60,f60_p}, 3));
        yVal.add(new BarEntry(new float[]{f50,f50_p}, 4));
        return yVal;
    }
    /**
     * 统计个年龄段人数和违章人数,需要考虑一个人有多辆车的情况
     */
    void sum_age_count(){
        String year="";// 出生年份
        for (int i = 0; i <personList.size() ; i++) {
            if(personList.get(i).getPcardid().length()==18) {
                year = personList.get(i).getPcardid().substring(8, 9);
                if (year.equals("9")) {
                    person90++;
                    if (personList.get(i).getPeccancy() > 0) {
                        peccancy90++;
                    }
                }
                if (year.equals("8")) {
                    person80++;
                    if (personList.get(i).getPeccancy() > 0) {
                        peccancy80++;
                    }
                }
                if (year.equals("7")) {
                    person70++;
                    if (personList.get(i).getPeccancy() > 0) {
                        peccancy70++;
                    }
                }
                if (year.equals("6")) {
                    person60++;
                    if (personList.get(i).getPeccancy() > 0) {
                        peccancy60++;
                    }
                }
                if (year.equals("5")) {
                    person50++;
                    if (personList.get(i).getPeccancy() > 0) {
                        peccancy50++;
                    }
                }
            }
        }
        if(person90!=0) {
            f90 = (float) ((person90 - peccancy90) * 100.0 / person90);
            f90_p = (float) (peccancy90 * 100.0 / person90);
        }
        if(person80!=0) {
            f80 = (float) ((person80 - peccancy80) * 100.0 / person80);
            f80_p = (float) (peccancy80 * 100.0 / person80);
        }
        if(person70!=0) {
            f70 = (float) ((person70 - peccancy70) * 100.0 / person70);
            f70_p = (float) (peccancy70 * 100.0 / person70);
        }
        if(person60!=0) {
            f60 = (float) ((person60 - peccancy60) * 100.0 / person60);
            f60_p = (float) (peccancy60 * 100.0 / person60);
        }
        if(person50!=0) {
            f50 = (float) ((person50 - peccancy50) * 100.0 / person50);
            f50_p = (float) (peccancy50 * 100.0 / person50);
        }
    }
}
