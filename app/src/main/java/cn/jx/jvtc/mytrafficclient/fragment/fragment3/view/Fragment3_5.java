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
public class Fragment3_5 extends Fragment {
    private static final String TAG = "Fragment3_5";
    private BarChart barChart;
    private BarDataSet barDataSet;
    private List<BarEntry> yVal;
    List<Car> carList;//所有车辆信息
    List<CarPeccancy> carPeccancyList;//车牌违章信息
    List<Person> personList;
    int man,woman,man_p,woman_p;
    float fman,fwoman,fman_p,fwoman_p;
    public Fragment3_5() {
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
        View v=inflater.inflate(R.layout.fragment_fragment3_5, container, false);
        barChart=v.findViewById(R.id.barChat_fragment35);
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
        barDataSet.setBarSpacePercent(80f);
        barDataSet.setStackLabels(new String[]{"无违章", "有违章"});
        BarData barData = new BarData(getXVal(), barDataSet);
        barDataSet.setValueFormatter(new PercentFormatter());
        return barData;
    }

    private String[] getXVal() {
        String[] xVal = new String[]{"女性", "男性"};
        return xVal;
    }

    private List<BarEntry> getYVal() {
        yVal = new ArrayList<>();
        yVal.add(new BarEntry(new float[]{fwoman, fwoman_p}, 0));
        yVal.add(new BarEntry(new float[]{fman, fman_p}, 1));
        return yVal;
    }
    /**
     * 计算男女性违章比例
     */
    void sum_age_count(){
        String sex="";// xingbie
        for (int i = 0; i <personList.size() ; i++) {
              sex = personList.get(i).getPsex();
              if(sex.equals("男")){
                  man++;
                  if(personList.get(i).getPeccancy()>0){
                      man_p++;
                  }
              }else{
                  woman++;
                  if(personList.get(i).getPeccancy()>0){
                      woman_p++;
                  }
              }
        }
        Log.d(TAG, "sum_age_count: "+man+":"+man_p+":"+woman+":"+woman_p);
        fman=(float)(100.0*(man-man_p)/man);
        fman_p=100-fman;
        fwoman=(float)(100.0*(woman-woman_p)/woman);
        fwoman_p=100-fwoman;

    }

}
