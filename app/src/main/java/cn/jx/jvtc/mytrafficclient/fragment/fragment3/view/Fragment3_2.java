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
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.formatter.PercentFormatter;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import cn.jx.jvtc.mytrafficclient.R;
import cn.jx.jvtc.mytrafficclient.activity.MainActivity;
import cn.jx.jvtc.mytrafficclient.fragment.fragment3.model.Car;
import cn.jx.jvtc.mytrafficclient.fragment.fragment3.model.CarPeccancy;

/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment3_2 extends Fragment {
    private static final String TAG = "Fragment3_2";
    PieChart pieChart;
    int peccancyCarCount,mutlPeccancyCarCount;//车辆违章数，重复违章车辆数
    List<Car> carList;//所有车辆信息
    List<CarPeccancy> carPeccancyList;//车牌违章信息
    public Fragment3_2() {
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
        View v=inflater.inflate(R.layout.fragment_fragment3_2, container, false);
        pieChart=v.findViewById(R.id.pieFragment32);
        sumPeccancyCars();
        initPie();
        return v;
    }
   void initPie(){
       pieChart.setUsePercentValues(true);//显示百分比
       pieChart.setDescription("");//图形的描述文字
       pieChart.setDrawHoleEnabled(false);//实心图，不是圆环
       TreeMap<String,Float> data=new TreeMap<>();//定义图形中的数据
       data.put("无重复违章",(float)((peccancyCarCount-mutlPeccancyCarCount)*1.0/peccancyCarCount));
       data.put("有重复违章",(float)(mutlPeccancyCarCount*1.0/peccancyCarCount));
       setData(data);
       pieChart.animateY(1400, Easing.EasingOption.EaseInOutBounce);
       Legend l=pieChart.getLegend();//获取图例
       l.setPosition(Legend.LegendPosition.BELOW_CHART_CENTER);//图例位置
       l.setTextSize(20f);//文字大小
       l.setFormSize(20f);//图例大小
   }
    /**
     * 设置图形的数据
     * @param data
     */
   void setData(TreeMap<String,Float> data){
       ArrayList<String> xVals=new ArrayList<>();//x轴数据
       ArrayList<Entry> yVals=new ArrayList<>();//y轴数据
       //读取数据
       int i=0;
       for (String key:data.keySet()
            ) {
           xVals.add(key);
           float value= (float)data.get(key);
           yVals.add(new Entry(value,i++));
       }
       PieDataSet dataSet=new PieDataSet(yVals,"");
       dataSet.setSliceSpace(5f);
       ArrayList<Integer> colors=new ArrayList<>();
       colors.add(Color.rgb(255,180,0));
       colors.add(Color.rgb(50,150,255));
       dataSet.setColors(colors);
       PieData pieData=new PieData(xVals,dataSet);
       pieData.setValueFormatter(new PercentFormatter());
       pieData.setValueTextSize(20f);
       pieData.setValueTextColor(Color.BLACK);
       pieChart.setData(pieData);
       pieChart.highlightValues(null);//显示y轴值
       pieChart.invalidate();//刷新
   }
    /**
     * 计算有重复违章的车辆数量
     */
    void sumPeccancyCars(){
        peccancyCarCount=0;
        mutlPeccancyCarCount=0;
        for (int i = 0; i <carList.size() ; i++) {
            if(carList.get(i).getPeccancy()>=1) {
                peccancyCarCount++;
            }
            if(carList.get(i).getPeccancy()>=2) {
                String[] pcode = carList.get(i).getPcodes().split(",");
                if (pcode.length <carList.get(i).getPeccancy()) {
                    mutlPeccancyCarCount++;
                }
            }
        }
    }

}
