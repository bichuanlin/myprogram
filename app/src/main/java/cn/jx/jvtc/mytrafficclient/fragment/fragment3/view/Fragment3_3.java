package cn.jx.jvtc.mytrafficclient.fragment.fragment3.view;


import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;


import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import cn.jx.jvtc.mytrafficclient.R;
import cn.jx.jvtc.mytrafficclient.activity.MainActivity;
import cn.jx.jvtc.mytrafficclient.fragment.fragment3.model.Car;
import cn.jx.jvtc.mytrafficclient.fragment.fragment3.model.CarPeccancy;

/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment3_3 extends Fragment {
    private static final String TAG = "Fragment3_3";
    public HorizontalBarChart horizontalBarChart;
    List<Car> carList;//所有车辆信息
    List<CarPeccancy> carPeccancyList;//车牌违章信息
    int peccancyCarCount, oneTwoPeccancy,threeToFivePeccancy,overFivePeccancy;
    public Fragment3_3() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        MainActivity mainActivity= (MainActivity) context;
        this.carList=mainActivity.getCarList();
        this.carPeccancyList=mainActivity.getCarPeccancyList();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_fragment3_3, container, false);
        horizontalBarChart=view.findViewById(R.id.horizontalBarChart_Fragment33);
        calcuPeccancy();
        initBarChart();
        return view;
    }
    private void initBarChart() {
        horizontalBarChart.setDescription(null);       ;
        horizontalBarChart.setTouchEnabled(false);
        //x坐标轴设置
        XAxis xAxis = horizontalBarChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setTextSize(20f);
        //Y轴，上面的坐标
        YAxis leftAxis = horizontalBarChart.getAxisLeft();
        leftAxis.setDrawGridLines(false);
        leftAxis.setEnabled(false);
       //y轴，下面的坐标
        YAxis rightAxis = horizontalBarChart.getAxisRight();
        rightAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        rightAxis.setValueFormatter(new PercentFormatter());
        rightAxis.setTextSize(20f);
        Legend legend=horizontalBarChart.getLegend();
        legend.setEnabled(false);
        horizontalBarChart.setData(getData());
        horizontalBarChart.invalidate();
    }

    public BarData getData() {
        BarDataSet barDataSet=new BarDataSet(getyValues(),"");
        List<Integer> colors=new ArrayList<>();
        colors.add(Color.rgb(170,70,68));
        colors.add(Color.rgb(69,115,167));
        colors.add(Color.rgb(101,255,83));
        barDataSet.setColors(colors);
        barDataSet.setValueFormatter(new PercentFormatter());
        BarData barData=new BarData(getxValues(),barDataSet);
        return barData;
    }

    public List<BarEntry> getyValues() {
        List<BarEntry> yValues=new ArrayList<>();
        yValues.add(new BarEntry((float)(overFivePeccancy*100.0/peccancyCarCount),5));
        yValues.add(new BarEntry((float)(threeToFivePeccancy*100.0/peccancyCarCount),3));
        yValues.add(new BarEntry((float)(oneTwoPeccancy*100.0/peccancyCarCount),1));
        return yValues;
    }

    public List<String> getxValues() {
        List<String> xValues=new ArrayList<>();
        xValues.add("");
        xValues.add("1-2条以上违章");
        xValues.add("");
        xValues.add("3-5条以上违章");
        xValues.add("");
        xValues.add("5条以上违章");
        xValues.add("");
        return xValues;
    }
    /**
     * 计算违章条数分布
     */
    void calcuPeccancy(){
        for (int i = 0; i <carList.size() ; i++) {
            if (carList.get(i).getPeccancy() >= 1) {
                peccancyCarCount++;
            }
            if(carList.get(i).getPeccancy()>=1&&carList.get(i).getPeccancy()<=2){
                oneTwoPeccancy++;
            }
            if(carList.get(i).getPeccancy()>=3&&carList.get(i).getPeccancy()<=5){
                threeToFivePeccancy++;
            }
            if(carList.get(i).getPeccancy()>5){
                overFivePeccancy++;
            }
        }
        Log.d(TAG, "calcuPeccancy: "+peccancyCarCount+":"+oneTwoPeccancy+":"+threeToFivePeccancy+":"+overFivePeccancy);
    }

}

